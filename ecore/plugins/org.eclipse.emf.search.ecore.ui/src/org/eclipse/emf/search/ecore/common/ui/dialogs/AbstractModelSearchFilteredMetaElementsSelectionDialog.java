/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchFilteredMetaElementsSelectionDialog.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - Fix getText() typo
 ******************************************************************************/

package org.eclipse.emf.search.ecore.common.ui.dialogs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.emf.search.core.services.ModelSearchEngineDescriptor;
import org.eclipse.emf.search.ecore.common.ui.utils.ModelSearchEditorUtils;
import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.ecore.ui.l10n.Messages;
import org.eclipse.emf.search.ui.handlers.IModelElementEditorSelectionHandler;
import org.eclipse.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.SearchPattern;
import org.osgi.framework.Bundle;

/**
 * This class has responsability to present the list of all reachable EClassifier for 
 * current workspace. Extending {@link FilteredItemsSelectionDialog}
 * allows to take advantage of the regex based query filtering system to discriminate EClass
 * based elements on their (non fully qualified) names. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas Bigeardel</a>
 *
 */
public abstract class AbstractModelSearchFilteredMetaElementsSelectionDialog extends
		FilteredItemsSelectionDialog {
	
	// Dialog Settings
	//private final static String META_ELEMENTS_PARTICIPANTS_FILTERED_SECTION_DIALOG_SETTINGS_ID = "MetaElementsPartcipantsFilteredDialogSection"; //$NON-NLS-1$

	// Model Search Engine
	private ModelExtensibleSearchEngineExtensionManager searchEngineExtensionManager;
	
	// controls
	//private IDialogSettings settings;
	
	// Results
	private IModelSearchResult result;
	
	// Default minimal set of Ecore related ILabelProvider
	protected ComposeableAdapterFactory listMetaElementParticipantAdapterFactory;
	protected ILabelProvider listMetaElementParticipantLabelProvider;
	
	protected ComposeableAdapterFactory detailsMetaElementParticipantAdapterFactory;
	protected ILabelProvider detailsMetaElementParticipantLabelProvider;
	
	protected ModelEditorOpenEnum openEditorMode;
	
    // Dialog Settings
	private final static String META_ELEMENT_SELECTION_DIALOG_ID = "metaElementSelectionDialogID"; //$NON-NLS-1$
	private final static String OPEN_DIAGRAM_TOGGLE_SELECTION_ID = "openDiagramToggleSelectionID"; //$NON-NLS-1$

	protected abstract List<EClassifier> getMetaElementParticipants();
	
	protected abstract boolean isAMetaElementToConsider(Object item);

	private class MetaElementsFilter extends ItemsFilter {
		@Override
		public boolean isConsistentItem(Object item) {
			return isAMetaElementToConsider(item);
		}
		@Override
		public boolean matchItem(Object item) {
			if (isConsistentItem(item)) {
				return matches(getElementName(item));
			}
			return false;
		}
		@Override
		public boolean isSubFilter(ItemsFilter filter) {
			return false;
		}
		@Override
		public boolean equalsFilter(ItemsFilter filter) {
			return false;
		}
		@Override
		public int getMatchRule() {
			return SearchPattern.RULE_CAMELCASE_MATCH;
		}
	}

	public AbstractModelSearchFilteredMetaElementsSelectionDialog(Shell shell, boolean multi) {
		super(shell, multi);
		
		searchEngineExtensionManager = ModelExtensibleSearchEngineExtensionManager.getInstance();
		
		listMetaElementParticipantAdapterFactory = new ComposedAdapterFactory(getMetaElementComposeableAdapterFactories());
		listMetaElementParticipantLabelProvider = new AdapterFactoryLabelProvider(listMetaElementParticipantAdapterFactory){
			@Override
			public String getText(Object object) {
				if (object instanceof IModelResultEntry) {
					IModelResultEntry modelResultEntry = (IModelResultEntry) object;
					return getElementName(object) + (modelResultEntry.getParent() instanceof IModelResultEntry ?  " - "  + getModelResultFullyQualifiedName(modelResultEntry.getParent()) : ""); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return super.getText(object);
			}
			@Override
			public Image getImage(Object object) {
				if (object instanceof IModelResultEntry) {
					return super.getImage(((IModelResultEntry) object).getSource());
				}
				return super.getImage(object);
			}
		};

		setListLabelProvider(listMetaElementParticipantLabelProvider);

		detailsMetaElementParticipantAdapterFactory = new ComposedAdapterFactory(getMetaElementComposeableAdapterFactories());
		detailsMetaElementParticipantLabelProvider = new AdapterFactoryLabelProvider(listMetaElementParticipantAdapterFactory){
			@Override
			public String getText(Object object) {
				if (object == null) {
					return ""; //$NON-NLS-1$
				}
				if (object instanceof IModelResultEntry) {
					String txt = getModelResultFullyQualifiedName(((IModelResultEntry) object).getParent());
					Object o = ((IModelResultEntry) object).getTarget();
					return txt + " [" + ((Resource)o).getURI() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				return super.getText(object);
			}
			@Override
			public Image getImage(Object object) {
				if (object instanceof IModelResultEntry && ((IModelResultEntry) object).getParent() != null) {
					return super.getImage(((IModelResultEntry) object).getParent().getSource());
				}
				return super.getImage(object);
			}
		};

		setDetailsLabelProvider(detailsMetaElementParticipantLabelProvider);
		
		setTitle(Messages.getString("ModelSearchFilteredMetaElementsSelectionDialog.Title")); //$NON-NLS-1$
		
		final IModelSearchQuery query = newQuery(createFilter().getPattern());
		setDefaultImage(getModelSearchResultImage(query));

		Job job = new Job(UUID.randomUUID().toString()){
			protected IStatus run(IProgressMonitor monitor) {
				query.run(monitor);
				return Status.OK_STATUS;
			}
		};
		
		job.addJobChangeListener(new IJobChangeListener() {
			public void sleeping(IJobChangeEvent event) {
			}
			public void scheduled(IJobChangeEvent event) {
			}
			public void running(IJobChangeEvent event) {
			}
			public void done(IJobChangeEvent event) {
				result = query.getModelSearchResult();
			}
			public void awake(IJobChangeEvent event) {
			}
			public void aboutToRun(IJobChangeEvent event) {
			}
		});

		job.schedule();
	}

	@Override
	public void create() {
		super.create();
		if (getPatternControl() instanceof Text) {
			((Text)getPatternControl()).setText("?"); //$NON-NLS-1$
		}
	}

	/**
     * Retrieves user contributed query image from the distant bundle.
     */
    private Image getModelSearchResultImage(IModelSearchQuery query) {
	    Bundle bundle = Platform.getBundle(query.getBundleSymbolicName());
	    URL url = null;
	    if (bundle != null){
	        url = FileLocator.find(bundle, new Path(query.getResultImagePath()), null);
	        return ImageDescriptor.createFromURL(url).createImage();
	    }
	    return ImageDescriptor.getMissingImageDescriptor().createImage();
    }

	
	public abstract String getModelResultFullyQualifiedName(IModelResultEntry entry);
	
	/**
	 * Implement few Ecore basic composeable Adapter Factories.
	 * This will be overidden by user. This would allow to avoid void lists ... ^^
	 */
	protected abstract List<AdapterFactory> getMetaElementComposeableAdapterFactories();

	protected abstract IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getModelQueryEvaluator();
	
	protected abstract String getModelSearchEngineID();
	
	protected abstract IModelElementEditorSelectionHandler getModelEditorSelectionHandler();
	
	/**
	 * Creates a new Model Search Query from a given filter.
	 * 
	 * This query will apply on any Ecore resource from the current workspace.
	 * 
	 * @param filter A String describing a non regex and non case sensitive matching pattern expression
	 * @return Corresponding Model Search result populated with pattern matches if any.
	 */
    private IModelSearchQuery newQuery(String filter) {
        ModelSearchEngineDescriptor searchEngine = searchEngineExtensionManager.find(getModelSearchEngineID()); //$NON-NLS-1$
    	
        IModelSearchQueryParameters parameters = searchEngine.getModelSearchParametersFactory().createSearchQueryParameters();
        
        IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(getModelSearchEngineID());

        parameters.setScope(scope);
        parameters.setEvaluator(getModelQueryEvaluator());
        parameters.setData(EcoreModelSearchQuery.CASE_SENSITIVE_SEARCH, Boolean.FALSE);
        parameters.setData(EcoreModelSearchQuery.REGEX_SEARCH, Boolean.FALSE);
        
        List<EClassifier> participantElements = new ArrayList<EClassifier>();
        participantElements.addAll(getMetaElementParticipants());
        parameters.setParticipantElements(participantElements);
        
        return searchEngine.getModelSearchQueryFactory().createModelSearchQuery(filter, parameters);
    }
    
	@Override
	protected ItemsFilter createFilter() {
		return new MetaElementsFilter();
	}
	
	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		for (IModelResultEntry e : result.getResultsFlatenned()) {
			if (isAMetaElementToConsider(e)) {
				contentProvider.add(e, itemsFilter);
			}
		}
	}
	
	@Override
	public abstract String getElementName(Object item);
	
	@Override
	protected Comparator<IModelResultEntry> getItemsComparator() {
		return new Comparator<IModelResultEntry>() {
			public int compare(IModelResultEntry arg0, IModelResultEntry arg1) {
				String k1 = "", k2 = ""; //$NON-NLS-1$ //$NON-NLS-2$
				if (arg0.getSource() instanceof EObject) {
					k1 = ((EObject)arg0.getSource()).eClass().getInstanceClass().getCanonicalName();
				}
				if (arg1.getSource() instanceof EObject) {
					k2 = ((EObject)arg1.getSource()).eClass().getInstanceClass().getCanonicalName();
				}
				return k1.compareTo(k2);
			}
		};
	}

	@Override
	protected IStatus validateItem(Object item) {
		return item instanceof IModelResultEntry?Status.OK_STATUS:Status.CANCEL_STATUS;
	}
	
	@Override
	protected Control createExtendedContentArea(Composite parent) {
		ViewForm viewForm = new ViewForm(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		viewForm.setLayoutData(gd);
		final Button editorModeCheckBox = new Button(viewForm, SWT.CHECK);
		editorModeCheckBox.setLayoutData(gd);
		editorModeCheckBox.setText(Messages.getString("AbstractModelSearchFilteredMetaElementsSelectionDialog.OpenEditorMode")); //$NON-NLS-1$
		editorModeCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				openEditorMode = editorModeCheckBox.getSelection()?ModelEditorOpenEnum.DIAGRAM:ModelEditorOpenEnum.TREE;
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		
		});
		viewForm.setContent(editorModeCheckBox);
		
		IDialogSettings openDiagramDialogSettings;
		boolean openEditorButtonToggleMode = false;
	    if ((openDiagramDialogSettings = getDialogSettings().getSection(META_ELEMENT_SELECTION_DIALOG_ID))!=null) {
			openEditorButtonToggleMode = openDiagramDialogSettings.getBoolean(OPEN_DIAGRAM_TOGGLE_SELECTION_ID);
	    }
	    openEditorMode = openEditorButtonToggleMode?ModelEditorOpenEnum.DIAGRAM:ModelEditorOpenEnum.TREE;
	    editorModeCheckBox.setSelection(ModelEditorOpenEnum.DIAGRAM.equals(openEditorMode));

		return viewForm;
	}
	@Override
	protected void storeDialog(IDialogSettings settings) {
        IDialogSettings openDiagramDialogSettings;
        if ((openDiagramDialogSettings = settings.getSection(META_ELEMENT_SELECTION_DIALOG_ID))==null) {
        	openDiagramDialogSettings = settings.addNewSection(META_ELEMENT_SELECTION_DIALOG_ID);
        }
        
        boolean openEditorButtonToggleMode = ModelEditorOpenEnum.DIAGRAM.equals(openEditorMode);
        openDiagramDialogSettings.put(OPEN_DIAGRAM_TOGGLE_SELECTION_ID, openEditorButtonToggleMode);
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();		
		if (getResult() != null && getResult().length>0 && getResult()[0] instanceof IModelResultEntry) {
			ModelSearchEditorUtils.openEditorWithSelection(getModelEditorSelectionHandler(), (EObject)((IModelResultEntry) getResult()[0]).getSource(), openEditorMode);
		}
	}
}
