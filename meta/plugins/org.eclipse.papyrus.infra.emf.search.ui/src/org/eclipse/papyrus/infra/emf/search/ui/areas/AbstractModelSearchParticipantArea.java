/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchParticipantArea.java
 * 
 * Contributors: 
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - add target model image support
 *		Lucas Bigeardel - add participants filtering support
 *		Lucas Bigeardel - add target meta elements listening support
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.areas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.papyrus.infra.emf.search.core.engine.DelegatingPackageRegistry;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetMetaElementSelectionEvent;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetSelectionEnum;
import org.eclipse.papyrus.infra.emf.search.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.ui.sorters.AbstractMetaModelParticipantsViewerSorter;
import org.eclipse.papyrus.infra.emf.search.ui.utils.CheckboxFilteredTree;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * <p>
 * Provide a list of all Ecore meta elements particpating to the search.
 * </p>
 * <p>
 * Allows user to select into which meta-element the search query will look
 * into.
 * </p>
 * 
 * @author <a
 *         href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public abstract class AbstractModelSearchParticipantArea extends AbstractModelSearchCompositeArea implements IModelSearchParticipantArea {

    protected CheckboxFilteredTree metaElementParticipantFilterCheckedTreeViewer;
    protected AbstractMetaModelParticipantsItemProvider metaElementsPartictipantsItemProvider;

    private Button metaElementParticipantSelectAllButton, metaElementParticipantDeselectAllButton;

    // Dialog settings
    private static final String META_ELEMENTS_PARTICIPANT_LIST_SETTINGS_ID = "metaElementsParticipantList"; //$NON-NLS-1$
    private static String SETTINGS_EXT = ".settings"; //$NON-NLS-1$

    private int participantsCheckStrategy = SWT.MULTI;
    private Group qGrp;

    public AbstractModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style) {
        qGrp = new Group(parent, SWT.NONE);
        qGrp.setLayout(new GridLayout(2, false));

        GridData gdGrp = new GridData(GridData.FILL_HORIZONTAL);
        gdGrp.heightHint = 100;

        qGrp.setLayoutData(gdGrp);
        qGrp.setText(Messages.getString("AbstractModelSearchPage.textQueryGroupTitle")); //$NON-NLS-1$
    }

    public int getParticipantsChekStrategy() {
        return participantsCheckStrategy;
    }

    public void setParticipantsChekStrategy(int participantsChekStrategy) {
        this.participantsCheckStrategy = participantsChekStrategy;
    }

    protected Collection<EPackage> getTargetModelPackages() {
        ArrayList<EPackage> ePackageList = new ArrayList<EPackage>();
        for (String nsURI : getTargetMetaModelIDs()) {
            ePackageList.add(DelegatingPackageRegistry.INSTANCE.getEPackage(nsURI));
        }
        return ePackageList;
    }

    @SuppressWarnings("deprecation")
    protected void createContent() {
        metaElementsPartictipantsItemProvider = getMetaModelParticipantsItemProvider();

        metaElementParticipantFilterCheckedTreeViewer = new CheckboxFilteredTree(qGrp, SWT.BORDER, new PatternFilter());
        metaElementParticipantFilterCheckedTreeViewer.getViewer().setContentProvider(metaElementsPartictipantsItemProvider);
        metaElementParticipantFilterCheckedTreeViewer.getViewer().setLabelProvider(new AdapterFactoryLabelProvider(getMetaElementComposeableAdapterFactory()) {
            @Override
            public Image getImage(Object object) {
                Image img = getTargetModelElementImage(object);
                return img != null ? img : super.getImage(object);
            }

            @Override
            public String getText(Object object) {
                String txt = getTargetModelElementText(object);
                return txt != null ? txt : super.getText(object);
            }

            @Override
            public String getColumnText(Object object, int columnIndex) {
                return super.getText(object);
            }
        });

        ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).addCheckStateListener(new ICheckStateListener() {
            public void checkStateChanged(CheckStateChangedEvent event) {
                if (participantsCheckStrategy == SWT.MULTI) {
                    return;
                } else {
                    ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).setCheckedElements(new Object[] { event.getCheckable() });
                }
            }
        });
        metaElementParticipantFilterCheckedTreeViewer.getViewer().setInput(""); //$NON-NLS-1$
        
        IStructuredContentProvider contentProvider = (IStructuredContentProvider)metaElementParticipantFilterCheckedTreeViewer.getViewer().getContentProvider();
    	((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).expandAll();
        for (Object o : contentProvider.getElements("")) {
        	((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).setSubtreeChecked(o, SWT.MULTI == participantsCheckStrategy);
        }
        metaElementParticipantFilterCheckedTreeViewer.getViewer().setSorter(new AbstractMetaModelParticipantsViewerSorter());

        metaElementParticipantFilterCheckedTreeViewer.getViewer().getTree().setLayout(new GridLayout());

        GridData chechboxTreeViewerGridData = new GridData(GridData.FILL_BOTH);
        chechboxTreeViewerGridData.heightHint = 140;

        metaElementParticipantFilterCheckedTreeViewer.getViewer().getTree().setLayoutData(chechboxTreeViewerGridData);

        Composite rightPaneContainer = new Composite(qGrp, SWT.NONE);
        rightPaneContainer.setLayout(new GridLayout());

        GridData rightPaneContainerGridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
        rightPaneContainerGridData.heightHint = 140;
        rightPaneContainer.setLayoutData(rightPaneContainerGridData);

        // Select/Deselect All butons
        metaElementParticipantSelectAllButton = new Button(rightPaneContainer, SWT.PUSH);
        metaElementParticipantSelectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        metaElementParticipantSelectAllButton.setText("Select All"); //$NON-NLS-1$
        metaElementParticipantSelectAllButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).setAllChecked(true);
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        metaElementParticipantSelectAllButton.setEnabled(SWT.MULTI == participantsCheckStrategy);

        metaElementParticipantDeselectAllButton = new Button(rightPaneContainer, SWT.PUSH);
        metaElementParticipantDeselectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        metaElementParticipantDeselectAllButton.setText("Deselect All"); //$NON-NLS-1$
        metaElementParticipantDeselectAllButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).setAllChecked(false);
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        metaElementParticipantDeselectAllButton.setEnabled(SWT.MULTI == participantsCheckStrategy);

        createAdditionalPartcipantControls(rightPaneContainer);

        setParticipantControlsEnabling(getDefaultParticpantsControlEnabling());
    }

    protected boolean getDefaultParticpantsControlEnabling() {
        return false;
    }

    private void setParticipantControlsEnabling(boolean status) {
        metaElementParticipantFilterCheckedTreeViewer.setEnabled(status);
        metaElementParticipantDeselectAllButton.setEnabled(status);
        metaElementParticipantSelectAllButton.setEnabled(status);
    }

    /**
     * {@inheritDoc}
     */
    public void handleTargetMetaElementParticipantSelection(EObject element) {
        if (element instanceof ENamedElement) {
            TreeViewer v = getMetaElementParticipantFilterCheckedTreeViewer().getViewer();
            if (v instanceof CheckboxTreeViewer) {
                CheckboxTreeViewer containerCheckboxFilteredTree = (CheckboxTreeViewer) v;
                containerCheckboxFilteredTree.setCheckedElements(new EObject[] { element });
                getMetaElementParticipantFilterCheckedTreeViewer().getViewer().reveal(element);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void handle(TargetMetaElementSelectionEvent event) {
        for (String nsURI : getTargetMetaModelIDs()) {
            if (nsURI != null && nsURI.equals(event.getTargetModelNsURI())) {
                if (event.getSelection() instanceof IStructuredSelection) {
                    Object o = ((IStructuredSelection) event.getSelection()).getFirstElement();
                    if (o instanceof EObject) {
                        handleTargetMetaElementParticipantSelection((EObject) o);
                    }
                }
                setParticipantControlsEnabling(event.isParticipantSelectionBlocking() == TargetSelectionEnum.NONE);
            }
        }
    }

    /**
     * Users wanting to add additional sub-area on bottom of participant
     * (add|remove)all buttons must override this method.
     * 
     * @param parent
     *            the parent Composite
     */
    protected void createAdditionalPartcipantControls(Composite parent) {
        // just in case (additional OCL expressions import/export buttons, etc
        // ...)
    }

    /**
     * Users wanting to display custom image for the corresponding meta element
     * must override this method.
     * 
     * @param object
     *            the current meta element EClassifier reference
     * @return The corresponding image, MissingImage image otherwise.
     */
    abstract protected Image getTargetModelElementImage(Object object);

    /**
     * Users wanting to display custom text for the corresponding meta element
     * must override this method.
     * 
     * @param object
     *            the current meta element EClassifier reference
     * @return The corresponding text, void String otherwise.
     */
    abstract protected String getTargetModelElementText(Object object);

    /**
     * Implement at least Ecore basic composeable Adapter Factories. This will
     * be overwritten by user. This would allow to avoid void lists ... ^^
     */
    public List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
        List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

        composeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());
        composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
        composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());

        return composeableAdapterFactories;
    }

    /**
     * Implement at least Ecore basic composeable Adapter Factories. This will
     * be overwritten by user. This would allow to avoid void lists ... ^^
     */
    public List<AdapterFactory> getMetaElementComposeableAdapterFactories(String nsURI) {
        return getMetaElementComposeableAdapterFactories();
    }

    /**
     * Returns current meta-elements participants list.
     */
    public final List<EClassifier> getSelectedParticipants() {
        List<EClassifier> eClassifierList = new ArrayList<EClassifier>();
        for (Object o : ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).getCheckedElements()) {
            if (o instanceof EClass) {
                eClassifierList.add((EClass) o);
            } else if (o instanceof EStructuralFeature) {
                EStructuralFeature f = (EStructuralFeature) o;
                if (!eClassifierList.contains(f)) {
                    eClassifierList.add((EClassifier) f.eContainer());
                }
            }
        }
        return eClassifierList;
    }

    /**
     * Returns current meta-elements participants list.
     */
    public final List<EStructuralFeature> getSelectedFeatures() {
        List<EStructuralFeature> eStructuralFeatureList = new ArrayList<EStructuralFeature>();
        for (Object o : ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).getCheckedElements()) {
            if (o instanceof EClass) {
                eStructuralFeatureList.add(((EClass) o).getEIDAttribute());
            } else if (o instanceof EStructuralFeature) {
                eStructuralFeatureList.add((EStructuralFeature)o);
            }
        }
        return eStructuralFeatureList;
    }

    /**
     * User wanting to propose a meta-element participant list must implement
     * this method.
     */
    public abstract AbstractMetaModelParticipantsItemProvider getMetaModelParticipantsItemProvider();

    /**
     * Composeable Adapter Factory containing a list of pertinent other Ecore
     * Adapter Factories according to currently introspected model.
     * 
     * @return a Composeable Adapter Factory containing a list of pertinent
     *         other Ecore Adapter Factories
     * 
     */
    public final ComposeableAdapterFactory getMetaElementComposeableAdapterFactory() {
        List<AdapterFactory> adapterFactoryList = new ArrayList<AdapterFactory>();
        adapterFactoryList.add(new EcoreItemProviderAdapterFactory());
        for (String nsURI : getTargetMetaModelIDs()) {
            adapterFactoryList.addAll(getMetaElementComposeableAdapterFactories(nsURI));
        }
        return new ComposedAdapterFactory(adapterFactoryList);
    }

    /**
     * Composeable Adapter Factory containing a list of pertinent other Ecore
     * Adapter Factories according to currently introspected model.
     * 
     * @return a Composeable Adapter Factory containing a list of pertinent
     *         other Ecore Adapter Factories
     * 
     */
    public final ComposeableAdapterFactory getMetaElementComposeableAdapterFactory(String nsURI) {
        return new ComposedAdapterFactory(getMetaElementComposeableAdapterFactories());
    }

    /**
     * Current area control.
     */
    public Control getControl() {
        return qGrp;
    }

    /**
     * {@inheritDoc}
     */
    // dialog settings
    public void loadDialogSettings() {
        // Dialog settings for Query Tab
        IDialogSettings settings = Activator.getDefault().getDialogSettings();

        //
        // BEGIN: https://bugs.eclipse.org/bugs/show_bug.cgi?id=231988
        //
        String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString(); //$NON-NLS-1$ //$NON-NLS-2$
        File settingsFile = new File(settingsPath);
        if (settingsFile.exists()) {
            try {
                settings.load(settingsPath);
            } catch (IOException e) {
                Activator.getDefault().getLog().log(new Status(IStatus.INFO, Activator.PLUGIN_ID, 0, Messages.getString("AbstractModelSearchParticipantArea.loadDialogSettingsError"), e)); //$NON-NLS-1$
            }
        }
        //
        // END
        //

        IDialogSettings ecoreTextQuerySectionDialogSettings;

        if ((ecoreTextQuerySectionDialogSettings = settings.getSection(getClass().getCanonicalName())) == null) {
            ecoreTextQuerySectionDialogSettings = settings.addNewSection(getClass().getCanonicalName());
        }

        String[] previousSelection = ecoreTextQuerySectionDialogSettings.getArray(META_ELEMENTS_PARTICIPANT_LIST_SETTINGS_ID);
        if (previousSelection != null) {
            List<String> lastMetaElementsSelectionList = Arrays.asList(previousSelection);
            for (Object o : metaElementsPartictipantsItemProvider.getElements("$$__ALL_ELEMENTS_$$")) { //$NON-NLS-1$ 
                if (o instanceof EClassifier) {
                    ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).setChecked(o, lastMetaElementsSelectionList.contains(((EClassifier) o).getClassifierID() + "")); //$NON-NLS-1$
                }
            }
        }

        Object o = ((IStructuredSelection) metaElementParticipantFilterCheckedTreeViewer.getViewer().getSelection()).getFirstElement();
        if (o instanceof EObject) {
            handleTargetMetaElementParticipantSelection((EObject) o);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void storeDialogSettings() {
        // Dialog settings for Participant Tab
        IDialogSettings settings = Activator.getDefault().getDialogSettings();
        IDialogSettings participantListSectionDialogSettings;

        if ((participantListSectionDialogSettings = settings.getSection(getClass().getCanonicalName())) == null) {
            participantListSectionDialogSettings = settings.addNewSection(getClass().getCanonicalName());
        }

        List<String> metaElementSelectionCanonicalNamesList = new ArrayList<String>();
        for (Object o : ((CheckboxTreeViewer) metaElementParticipantFilterCheckedTreeViewer.getViewer()).getCheckedElements()) {
            if (o instanceof EClassifier) {
                metaElementSelectionCanonicalNamesList.add(((EClassifier) o).getClassifierID() + ""); //$NON-NLS-1$
            }
        }

        participantListSectionDialogSettings.put(META_ELEMENTS_PARTICIPANT_LIST_SETTINGS_ID, metaElementSelectionCanonicalNamesList.toArray(new String[0]));

        String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString(); //$NON-NLS-1$ //$NON-NLS-2$
        File settingsFile = new File(settingsPath);
        if (!settingsFile.exists() || settingsFile.canWrite()) {
            try {
                settings.save(settingsPath);
            } catch (IOException e) {
                Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("AbstractModelSearchParticipantArea.saveDialogSettingsError"), e)); //$NON-NLS-1$
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void prepare() {
    }

    /**
     * {@inheritDoc}
     */
    public boolean validateStatus() {
        return true;
    }

    public CheckboxFilteredTree getMetaElementParticipantFilterCheckedTreeViewer() {
        return metaElementParticipantFilterCheckedTreeViewer;
    }
}