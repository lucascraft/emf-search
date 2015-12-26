/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreOCLModelSearchQueryArea.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - UI, TargetModel & Target Meta Elements listenrs
 ******************************************************************************/

package org.eclipse.emf.search.ecore.ocl.ui.areas;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.search.core.engine.DelegatingPackageRegistry;
import org.eclipse.emf.search.core.engine.TargetMetaElementSelectionEvent;
import org.eclipse.emf.search.core.engine.TargetSelectionEnum;
import org.eclipse.emf.search.ecore.common.factories.EcoreCommonUtils;
import org.eclipse.emf.search.ecore.ocl.ui.Activator;
import org.eclipse.emf.search.ecore.ocl.ui.l10n.Messages;
import org.eclipse.emf.search.ocl.engine.ModelingLevel;
import org.eclipse.emf.search.ocl.engine.OCLModelSearchQueryEnum;
import org.eclipse.emf.search.ocl.engine.TargetMetamodel;
import org.eclipse.emf.search.ocl.services.TargetMetaModelDescriptor;
import org.eclipse.emf.search.ocl.services.TargetMetaModelExtensionManager;
import org.eclipse.emf.search.ocl.ui.widget.OCLExpressionWidget;
import org.eclipse.emf.search.ui.areas.AbstractModelSearchQueryArea;
import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.uml.UMLEnvironmentFactory;
import org.eclipse.ocl.uml.UMLPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Defines UI for Ecore models <b>OCL</b> based queries.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 */
public final class EcoreOCLModelSearchQueryArea extends AbstractModelSearchQueryArea {
	// Dialog settings
	private static final String OCL_MODEL_SEARCH_AREA_DIALOG_SECTION_ID = "EcoreOCLModelSearchQueryArea"; //$NON-NLS-1$

	private static final String OCL_TARGET_META_MODEL_NS_URI_DIALOG_SETTINGS_ID = "targetMetaModelNsURI";
	private static final String ECORE_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID = "ecoreCtxSelectionsIndex"; //$NON-NLS-1$
	private static final String ECORE_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID = "ecoreQueryExpressionsList"; //$NON-NLS-1$

	private static final String UML2_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID = "uml2CtxSelectionsIndex"; //$NON-NLS-1$
	private static final String UML2_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID = "uml2QueryExpressionsList"; //$NON-NLS-1$

	private static String SETTINGS_EXT = ".settings"; //$NON-NLS-1$

	private String[] lastEcoreOCLExpressions, lastUML2OCLExpressions;
	private Composite container;
	private Label contextLabel;

	private final TargetMetaModelExtensionManager targetMetaModelExtensionManager;

	private ComboViewer metaElementsClassifiersComboViewer;
	private OCLExpressionWidget oclExpressionWidget;

	private String currentTargetMetaModelID;
	private int lastIndex = 0;
	
	public EcoreOCLModelSearchQueryArea(Composite parent, AbstractModelSearchPage page, int style) {
		
		targetMetaModelExtensionManager = TargetMetaModelExtensionManager.getInstance();
		
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		createContextLabel(container);

		createComboViewer(container);

		createOCLExpressionWidget(container);
	}

	protected void createContextLabel(Composite parent) {
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);

		contextLabel = new Label(parent, SWT.NONE);
		contextLabel.setText(Messages.getString("EcoreOCLModelSearchQueryArea.ContextLabel")); //$NON-NLS-1$
		contextLabel.setLayoutData(gridData);
	}

	private class MetaElementsClassifiersComboViewerSelectionChangedListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource().equals(metaElementsClassifiersComboViewer)) {
				int index = metaElementsClassifiersComboViewer.getCombo().getSelectionIndex();

				String lastExpression = ""; //$NON-NLS-1$
				ModelingLevel level = ModelingLevel.M2;

				TargetMetamodel target = getTargetMetamodelFromNsURI(currentTargetMetaModelID == null ? EcorePackage.eINSTANCE.getNsURI()
						: currentTargetMetaModelID);

				if (target.equals(TargetMetamodel.Ecore)) {
					lastExpression = lastEcoreOCLExpressions[index];
					currentTargetMetaModelID = EcorePackage.eINSTANCE.getNsURI();
					level = ModelingLevel.M2;
				} else if (target.equals(TargetMetamodel.UML)) {
					lastExpression = lastUML2OCLExpressions[index];
					currentTargetMetaModelID = UMLPackage.eINSTANCE.getNsURI();
					level = ModelingLevel.M1;
				}
				
				Object element = ((IStructuredSelection) metaElementsClassifiersComboViewer.getSelection()).getFirstElement();
				
				oclExpressionWidget.setExpression(lastExpression);
				oclExpressionWidget.setContext((EObject) element);
				oclExpressionWidget.setModelingLevel(level);
				
				oclExpressionWidget.getViewer().getControl().setFocus();
				
				notifyListeners(
						new TargetMetaElementSelectionEvent(
								metaElementsClassifiersComboViewer,
								metaElementsClassifiersComboViewer.getSelection(),
								currentTargetMetaModelID,
								TargetSelectionEnum.BLOCKING)
						);
			}
		}
	}
	
	protected void createComboViewer(Composite parent) {
		ILabelProvider metaElementsClassifiersLabelProvider = new AdapterFactoryLabelProvider(
				new ComposedAdapterFactory(EcoreCommonUtils.getMetaElementComposeableAdapterFactories())){
			@Override
			public String getText(Object object) {
				if (object instanceof ENamedElement) {
					return ((ENamedElement)object).getName();
				}
				return super.getText(object);
			}
		};

		metaElementsClassifiersComboViewer = new ComboViewer(new Combo(parent, SWT.READ_ONLY));
		metaElementsClassifiersComboViewer.setContentProvider(new ArrayContentProvider());
		metaElementsClassifiersComboViewer.setLabelProvider(metaElementsClassifiersLabelProvider);
		metaElementsClassifiersComboViewer.setInput(new Object[0]);
		metaElementsClassifiersComboViewer.setComparator(new ViewerComparator(){
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof ENamedElement && e2 instanceof ENamedElement) {
					String txt1 = ((ENamedElement)e1).getName();
					String txt2 = ((ENamedElement)e2).getName();
					
					return txt1.compareTo(txt2);
				}
				return super.compare(viewer, e1, e2);
			}
		});

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		metaElementsClassifiersComboViewer.getControl().setLayoutData(gridData);
	}

	protected void createOCLExpressionWidget(Composite parent) {
		oclExpressionWidget = new OCLExpressionWidget(parent);
		oclExpressionWidget.setLayout(new GridLayout());

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		oclExpressionWidget.getViewer().getControl().setLayoutData(gridData);
	}

	public Control getControl() {
		return container;
	}

	public String getQueryExpression() {
		return oclExpressionWidget.getExpression();
	}

	//
	// General dialog settings loading
	//
	public void loadDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		//
		// BEGIN: https://bugs.eclipse.org/bugs/show_bug.cgi?id=231988
		//
		String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString();
		File settingsFile = new File(settingsPath);
		if (settingsFile.exists()) {
			try {
				settings.load(settingsPath);
	        } catch (IOException e) {
				Activator.getDefault().getLog().log(
						new Status(
								IStatus.INFO,
								Activator.PLUGIN_ID,
								0,
								Messages
										.getString("EcoreTextModelSearchQueryArea.dialogSettingsLoadErrorMessage"), e)); //$NON-NLS-1$
	        }
		}
		//
		// END
		//
		
		IDialogSettings oclQuerySectionDialogSettings;

		if ((oclQuerySectionDialogSettings = settings.getSection(OCL_MODEL_SEARCH_AREA_DIALOG_SECTION_ID)) == null) {
			oclQuerySectionDialogSettings = settings.addNewSection(OCL_MODEL_SEARCH_AREA_DIALOG_SECTION_ID);
		}

		String targetMetaModelNsURI = oclQuerySectionDialogSettings.get(OCL_TARGET_META_MODEL_NS_URI_DIALOG_SETTINGS_ID);

		targetMetaModelNsURI = targetMetaModelNsURI == null ? (currentTargetMetaModelID == null ? EcorePackage.eINSTANCE
				.getNsURI()
				: currentTargetMetaModelID)
				: targetMetaModelNsURI;

		TargetMetamodel target = getTargetMetamodelFromNsURI(targetMetaModelNsURI);

		oclExpressionWidget.setTargetMetamodel(target);

		metaElementsClassifiersComboViewer.setInput(getContextClassifierListFromNsURI(targetMetaModelNsURI));

		// Init Ecore expressions array
		lastEcoreOCLExpressions = oclQuerySectionDialogSettings.getArray(ECORE_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID);
		lastEcoreOCLExpressions = lastEcoreOCLExpressions == null ? new String[5000]
				: lastEcoreOCLExpressions;

		// Init UML2 expressions array
		lastUML2OCLExpressions = oclQuerySectionDialogSettings.getArray(UML2_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID);
		lastUML2OCLExpressions = lastUML2OCLExpressions == null ? new String[5000]
				: lastUML2OCLExpressions;

		if (target.equals(TargetMetamodel.Ecore)) {
			// Target MetaModel : Ecore
			String lastEcoreOCLCtxIndex = oclQuerySectionDialogSettings.get(ECORE_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID);
			lastEcoreOCLCtxIndex = (lastEcoreOCLCtxIndex == null) ? "0" : lastEcoreOCLCtxIndex;

			lastIndex = Integer.parseInt(lastEcoreOCLCtxIndex);
			lastIndex = lastIndex >= 0 ? lastIndex : 0;

			if (lastEcoreOCLExpressions.length >= lastIndex) {
				oclExpressionWidget.setExpression(lastEcoreOCLExpressions[lastIndex]);
			}
		} else if (target.equals(TargetMetamodel.UML)) {
			// Target MetaModel : UML2
			String lastUML2OCLCtxIndex = oclQuerySectionDialogSettings.get(UML2_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID);
			lastUML2OCLCtxIndex = (lastUML2OCLCtxIndex == null) ? "0" : lastUML2OCLCtxIndex;

			lastIndex = Integer.parseInt(lastUML2OCLCtxIndex);
			lastIndex = lastIndex >= 0 ? lastIndex : 0;

			if (lastUML2OCLExpressions.length >= lastIndex) {
				oclExpressionWidget.setExpression(lastUML2OCLExpressions[lastIndex]);
			}
		}
		
		metaElementsClassifiersComboViewer.addSelectionChangedListener(new MetaElementsClassifiersComboViewerSelectionChangedListener());

		if (metaElementsClassifiersComboViewer.getCombo().getItems().length >= lastIndex) {
			Object object = metaElementsClassifiersComboViewer.getElementAt(lastIndex);
			metaElementsClassifiersComboViewer.setSelection(new StructuredSelection(object));
		} else {
			int itemCount = metaElementsClassifiersComboViewer.getCombo().getItemCount();
			if (itemCount > 0) {
				Object element = metaElementsClassifiersComboViewer.getElementAt(lastIndex);
				//metaElementsClassifiersComboViewer.getCombo().indexOf(((ENamedElement)element).getName());
				metaElementsClassifiersComboViewer.setSelection(new StructuredSelection(element == null ? new Object[0] : element));
			}
		}
	}

	//
	// General dialog settings storage
	//
	public void storeDialogSettings() {
		// Dialog settings for Query Tab
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings oclQuerySectionDialogSettings = settings.getSection(OCL_MODEL_SEARCH_AREA_DIALOG_SECTION_ID);

		int comboSelectionIndex = metaElementsClassifiersComboViewer.getCombo().getSelectionIndex();

		String oclExpression = oclExpressionWidget.getExpression();

		if (oclExpressionWidget.getTargetMetamodel().equals(TargetMetamodel.Ecore)) {
			// Target MetaModel : Ecore
			lastEcoreOCLExpressions = lastEcoreOCLExpressions == null ? new String[metaElementsClassifiersComboViewer
					.getCombo().getItemCount()]
					: lastEcoreOCLExpressions;
			if (comboSelectionIndex >= 0 && lastEcoreOCLExpressions.length >= comboSelectionIndex) {
				lastEcoreOCLExpressions[comboSelectionIndex] = oclExpression;
			}
			oclQuerySectionDialogSettings.put(ECORE_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID, "" + comboSelectionIndex);
			oclQuerySectionDialogSettings.put(ECORE_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID, lastEcoreOCLExpressions);
		} else if (oclExpressionWidget.getTargetMetamodel().equals(
				TargetMetamodel.UML)) {
			// Target MetaModel : UML2
			lastUML2OCLExpressions = lastUML2OCLExpressions == null ? new String[metaElementsClassifiersComboViewer
					.getCombo().getItemCount()]
					: lastUML2OCLExpressions;
			if (comboSelectionIndex >= 0 && lastUML2OCLExpressions.length >= comboSelectionIndex) {
				lastUML2OCLExpressions[comboSelectionIndex] = oclExpression;
			}
			oclQuerySectionDialogSettings.put(UML2_CONTEXT_SELECTION_INDEX_DIALOG_SETTINGS_ID, "" + comboSelectionIndex);
			oclQuerySectionDialogSettings.put(UML2_QUERY_EXPRESSION_LIST_DIALOG_SETTINGS_ID, lastUML2OCLExpressions);
		}

		currentTargetMetaModelID = currentTargetMetaModelID == null ? EcorePackage.eINSTANCE
				.getNsURI()
				: currentTargetMetaModelID;
		oclQuerySectionDialogSettings.put(OCL_TARGET_META_MODEL_NS_URI_DIALOG_SETTINGS_ID, currentTargetMetaModelID);

		String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString();
		File settingsFile = new File(settingsPath);
		if (!settingsFile.exists()||settingsFile.canWrite()) {
			try {
				settings.save(settingsPath);
			} catch (IOException e) {
	           	Activator.getDefault().getLog().log(
	           			new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
      					Messages.getString("EcoreTextModelSearchQueryArea.dialogSettingsSaveErrorMessage"), e)); //$NON-NLS-1$
			}
	    }
	}
	
	//
	// Query preparation, called before triggering the search
	//
	public void prepare() {
		ISelection selection = metaElementsClassifiersComboViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			getDataMap().put(OCLModelSearchQueryEnum.OCL_MODEL_SEARCH_CONTEXT.name(), ((StructuredSelection) selection).getFirstElement());
		}
	}

	public boolean validateStatus() {
		boolean status = true;
		try {
			status = oclExpressionWidget.evaluate();
		} catch (Throwable t) {
			// ugly, I know
		}
		return status;
	}

	public void handleTargetMetaModel(String targetMetaModelID) {
		// OCL query expressions edition is target meta model dependent
		// Eg: we need to know which OCL context to set between Ecore or UML
		handleTargetMetaModelFromContributions(targetMetaModelID);
	}

	private TargetMetamodel getTargetMetamodelFromNsURI(String getTargetMetamodelNsURI) {
		TargetMetaModelDescriptor targetMetaModelDescriptor = targetMetaModelExtensionManager.find(getTargetMetamodelNsURI);
		if (TargetMetamodel.Ecore.name().equals(targetMetaModelDescriptor.getTarget())) {
			return TargetMetamodel.Ecore;
		} else if (TargetMetamodel.UML.name().equals(targetMetaModelDescriptor.getTarget())) {
			return TargetMetamodel.UML;
		}
		return TargetMetamodel.Ecore; //FIXME: LB to introduce extensibility for arbitrary contributed models
	}

	private TargetMetamodel getTargetMetamodel(String targetMetamodelLabel) {
		if (TargetMetamodel.Ecore.name().equals(targetMetamodelLabel)) {
			return TargetMetamodel.Ecore;
		} else if (TargetMetamodel.UML.name().equals(targetMetamodelLabel)) {
			return TargetMetamodel.UML;
		}
		return TargetMetamodel.Ecore; //FIXME: LB to introduce extensibility for arbitrary contributed models
	}

	private ModelingLevel getModelingLevel(String modelingLevelLabel) {
		if (ModelingLevel.M1.name().equals(modelingLevelLabel)) {
			return ModelingLevel.M1;
		} else if (ModelingLevel.M2.name().equals(modelingLevelLabel)) {
			return ModelingLevel.M2;
		}
		return ModelingLevel.M2; //FIXME: LB to introduce extensibility for arbitrary contributed levels
	}

	private void setTargetMetaModelFromDescriptor(TargetMetaModelDescriptor targetMetaModelDescriptor) {
		switch (getTargetMetamodel(targetMetaModelDescriptor.getTarget())) {
			case Ecore:
			default:
				oclExpressionWidget.setTargetMetamodel(TargetMetamodel.Ecore);
				oclExpressionWidget.setEnvironmentFactory(new EcoreEnvironmentFactory());
				break;
			case UML:
				oclExpressionWidget.setTargetMetamodel(TargetMetamodel.UML);
				oclExpressionWidget.setEnvironmentFactory(new UMLEnvironmentFactory());
				break;
		}
	}

	private void setModelingLevelFromDescriptor(
			TargetMetaModelDescriptor targetMetaModelDescriptor) {
		switch (getModelingLevel(targetMetaModelDescriptor.getLevel())) {
			case M1:
				oclExpressionWidget.setModelingLevel(ModelingLevel.M1);
				break;
			case M2:
			default:
				oclExpressionWidget.setModelingLevel(ModelingLevel.M2);
				break;
		}
	}
	
	public EObject getTargetMetaElement() {
		if (metaElementsClassifiersComboViewer != null) {
			ISelection sel = metaElementsClassifiersComboViewer.getSelection();
			if (sel instanceof IStructuredSelection) {
				return (EObject)((IStructuredSelection)sel).getFirstElement();
			}
		}
		return null;
	}
	
	public Collection<EObject> getTargetMetaElements() {
		return Arrays.asList(new EObject[]{ getTargetMetaElement() });
	}

	private void setContextFromDescriptor(TargetMetaModelDescriptor targetMetaModelDescriptor) {
		EPackage delegatedPackage = DelegatingPackageRegistry.INSTANCE.getEPackage(targetMetaModelDescriptor.getNsURI());
		metaElementsClassifiersComboViewer.setInput(delegatedPackage.getEClassifiers());
		notifyListeners(new TargetMetaElementSelectionEvent(
				metaElementsClassifiersComboViewer, 
				new StructuredSelection(new Object[0]), 
				targetMetaModelDescriptor.getNsURI(), 
				TargetSelectionEnum.BLOCKING)
		);
	}

	private List<EClassifier> getContextClassifierListFromNsURI(String targetMetaModelID) {
		TargetMetaModelDescriptor targetMetaModelDescriptor = targetMetaModelExtensionManager.find(targetMetaModelID);
		EPackage delegatedPackage = DelegatingPackageRegistry.INSTANCE.getEPackage(targetMetaModelDescriptor.getNsURI());
		return delegatedPackage.getEClassifiers();
	}

	private void handleTargetMetaModelFromContributions(String targetMetaModelID) {
		TargetMetaModelDescriptor targetMetaModelDescriptor = targetMetaModelExtensionManager.find(targetMetaModelID);
		setTargetMetaModelFromDescriptor(targetMetaModelDescriptor);
		setModelingLevelFromDescriptor(targetMetaModelDescriptor);
		setContextFromDescriptor(targetMetaModelDescriptor);
		currentTargetMetaModelID = targetMetaModelID;
	}
}
