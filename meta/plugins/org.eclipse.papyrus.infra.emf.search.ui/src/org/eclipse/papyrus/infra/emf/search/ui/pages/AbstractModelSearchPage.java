/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchPage.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - layout fixing
 *		Lucas Bigeardel - Replace handling
 *		Lucas Bigeardel - participant tabs selection handling
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaElementHandler;
import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaElementProvider;
import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaElementSelectionListener;
import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaElementSelectionProvider;
import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaModelHandler;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.IModelSearchReplaceHandler;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.ITransformation;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.ITransformationProvider;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.TextualModelSearchReplaceRefactoring;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.scope.ModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchEngineDescriptor;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchScopeDescriptor;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchScopeExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchCompositeArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchQueryArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchParticipantArea;
import org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional.TextualModelSearchRefactoringOperation;
import org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional.TextualModelSearchReplaceWizard;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.services.EngineMappingDescriptor;
import org.eclipse.papyrus.infra.emf.search.ui.services.ModelSearchEngineMappingExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ui.services.ModelSearchParticipantTabExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ui.services.ModelSearchQueryTabExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ui.services.ParticipantTabDescriptor;
import org.eclipse.papyrus.infra.emf.search.ui.services.QueryTabDescriptor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.search.ui.IReplacePage;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkingSet;

/**
 * Defines a page contributed to the org.eclipse.search.searchPages extension point.
 * 
 * <p>
 * This pages exposes three different areas :
 * <blockquote>
 * <li>query area
 * <li>participant area
 * <li>scope area
 * </blockquote>
 * </p>
 * <br>
 * <b>Query Area</b>
 * <p>
 * The query area displays a TabFolder containing a tab for every query contributed to modelSearchQueryTab the extension point. 
 * </p>
 * <br>
 * <b>Participant Area</b>
 * <p>
 * The participant area displays a TabFolder containing a tab for every query contributed to modelSearchParticipantTab the extension point. 
 * </p>
 * <br>
 * <b>Scope Area</b>
 * <p>
 * The Scope area displays a group containing possibles workspace search scopes. 
 * </p>
 * <br>
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractModelSearchPage extends DialogPage implements 
																	ISearchPage, 
																	IReplacePage, 
																	IModelSearchReplaceHandler, 
																	ITransformationProvider<EObject, IModelSearchQuery, String, String> {
	// Dialog Settings
	private final static String QUERY_SECTION_DIALOG_SETTINGS_ID = "QuerySection"; //$NON-NLS-1$
	private final static String SELECTED_QUERY_ID = "SelectedQueryTabID"; //$NON-NLS-1$
	
	// controls
	private String queryTabID = "", currentParticipantDescID = ""; //$NON-NLS-1$ //$NON-NLS-2$
	private TabItem queryTabItemToSelect;//, participantTabItemToSelect;

	/** Extension point managers */
	private ModelSearchQueryTabExtensionManager queryAreaExtensionManager;
	private ModelSearchParticipantTabExtensionManager participantAreaExtensionManager;
	private ModelExtensibleSearchEngineExtensionManager searchEngineExtensionManager;
	
	/** tab folders which items are respectively contributed by modelSearchQueryTab & modelSearchParticipantTab */
	private TabFolder searchQueryTabFolder; //, searchParticipantTabFolder;
	
	/** map searchEngineID <-> ModelSearchEngineDescriptor */
	private Map<String, ModelSearchEngineDescriptor> searchEngineDescriptorsMap;
	private List<EngineMappingDescriptor> engineMappingDescriptorList;
	
	/** ISearchPageContainer allows to get resource selection */
	private ISearchPageContainer searchPageContainer;
	
	/** Dialog settings for Model Search main page */
	private IDialogSettings settings;
	
	/** Allows to discriminate & identify different search page from their contribution IDs */
	protected abstract String getModelSearchPageID();
	
	public String getID() {
		return getModelSearchPageID();
	}

	public AbstractModelSearchPage() {
		queryAreaExtensionManager = ModelSearchQueryTabExtensionManager.getInstance();
		participantAreaExtensionManager = ModelSearchParticipantTabExtensionManager.getInstance();
		searchEngineExtensionManager = ModelExtensibleSearchEngineExtensionManager.getInstance();
		searchEngineDescriptorsMap = new HashMap<String, ModelSearchEngineDescriptor>();
		engineMappingDescriptorList = new ArrayList<EngineMappingDescriptor>();
		settings = Activator.getDefault().getDialogSettings();
	}
	
	/* 
	 * Creates the top query configuration area 
	 */ 
	protected void createSearchQueryArea(Composite parent) {
		searchQueryTabFolder = new TabFolder(parent, SWT.TOP);
		
		GridLayout gl1 = new GridLayout();
		gl1.marginWidth = gl1.marginHeight = 0;
		
		searchQueryTabFolder.setLayout(gl1);
		
		GridData gd1 = new GridData(GridData.FILL_BOTH);
		gd1.heightHint = 200;
		
		searchQueryTabFolder.setLayoutData(gd1);
		
		assert queryAreaExtensionManager.getModelSearchQueryAreas() != null : Messages.getString("ModelExtensibleSearchPage.assertMessage1"); //$NON-NLS-1$
		assert queryAreaExtensionManager.getModelSearchQueryAreas()[0] != null : Messages.getString("ModelExtensibleSearchPage.assertMessage2"); //$NON-NLS-1$

    	// Create Query Tabs
		for (QueryTabDescriptor queryAreaDescriptor : queryAreaExtensionManager.getModelSearchQueryAreas()) {
			if (getModelSearchPageID().equals(queryAreaDescriptor.getTargetSearchPageID())) {
				IModelSearchArea area = queryAreaDescriptor.createArea(searchQueryTabFolder, this);
	
				TabItem queryTabItem = new TabItem(searchQueryTabFolder, SWT.NONE);
				queryTabItem.setText(queryAreaDescriptor.getLabel());
				queryTabItem.setToolTipText(queryAreaDescriptor.getTooltip());
				queryTabItem.setControl(area.getControl());
				queryTabItem.setData(queryAreaDescriptor);
				queryTabItem.setImage(queryAreaDescriptor.getImage());
				
				GridData gd2 = new GridData(GridData.FILL_BOTH);
				gd2.heightHint = 100;
				
				queryTabItem.getControl().setLayoutData(gd2);
				
				GridData gd3 = new GridData(GridData.FILL_BOTH);
				gd3.heightHint = 100;

				area.getControl().setLayoutData(gd3);
				
				//
				// BEGIN: https://bugs.eclipse.org/bugs/show_bug.cgi?id=231988
				//
				Object data = queryAreaDescriptor.queryModelSearchArea;
				if (data instanceof AbstractModelSearchCompositeArea) {
					Map<String, Object> map = ((AbstractModelSearchCompositeArea)data).getDataMap();
					if (map!= null) {
						if (map.get("SETTINGS_PREFIX") == null) { //$NON-NLS-1$
							map.put("SETTINGS_PREFIX", getID()); //$NON-NLS-1$
						}
					}
				}
				//
				// END
				//
				area.loadDialogSettings();
			}
		}
		
		searchQueryTabFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleTargetMetaModelEnvironmentConfiguration();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private IModelSearchScope<?, Resource> computeModelSearchWorkspaceScope(IModelSearchQueryParameters parameters){
		// Using model search engine outside platform Search infrastructure can lead to case 
		// where no container is available (For example FilteredItemSelectionDialog usage).
		// In these cases we assume that default behavior is to apply the query on the entire 
		// workspace.
		
		ModelSearchScopeDescriptor desc = ModelSearchScopeExtensionManager.getInstance().find("org.eclipse.papyrus.infra.emf.search.ModelScopeFactory");
		if (getContainer() == null) {
			if (parameters.getScope() == null) {
				return desc.getModelSearchScopeFactory().createModelSearchWorkspaceScope(parameters.getModelSearchEngineID());
			}else {
				return parameters.getScope();
			}	
		} else {
			switch (getContainer().getSelectedScope()) {
				case ISearchPageContainer.WORKSPACE_SCOPE:
				{
					return desc.getModelSearchScopeFactory().createModelSearchWorkspaceScope(parameters.getModelSearchEngineID());
				}
				case ISearchPageContainer.SELECTION_SCOPE:
				{	
					return desc.getModelSearchScopeFactory().createModelSearchScope(parameters.getModelSearchEngineID(), getContainer().getSelection());
				}
				case ISearchPageContainer.SELECTED_PROJECTS_SCOPE: 
				{
					String[] projectNames= getContainer().getSelectedProjectNames();
					return desc.getModelSearchScopeFactory().createModelSearchProjectScope(parameters.getModelSearchEngineID(), projectNames);
				}
				case ISearchPageContainer.WORKING_SET_SCOPE: 
				{				
					IWorkingSet[] workingSets = getContainer().getSelectedWorkingSets();
					// should not happen - just to be sure
					if (workingSets == null || workingSets.length < 1)
						return null;
					return desc.getModelSearchScopeFactory().createModelSearchScope(parameters.getModelSearchEngineID(), workingSets);
				}
				default:
				{
					break;
				}
			}
		}
		// Obviously, this is a void scope ^^
		return new ModelSearchScope<Object, Resource>("%%%% VOID MODEL SEARCH SCOPE %%%%"); //$NON-NLS-1$
	}

	/* 
	 * Creates the middle model search participant configuration area 
	 */ 
	protected void createParticipantTabsArea(Composite parent) {
		TabFolder searchParticipantTabFolder = new TabFolder(parent, SWT.TOP);
		
		GridLayout gl0 = new GridLayout();
		gl0.marginWidth = gl0.marginHeight = 0;
		searchParticipantTabFolder.setLayout(gl0);
		
		GridData gd0 = new GridData(GridData.FILL_BOTH);
		gd0.heightHint = 240;
		searchParticipantTabFolder.setLayoutData(gd0);
		
		assert participantAreaExtensionManager.getModelSearchParticipantDescriptors() != null : Messages.getString("ModelExtensibleSearchPage.assertMessage3"); //$NON-NLS-1$
		assert participantAreaExtensionManager.getModelSearchParticipantDescriptors()[0] != null : Messages.getString("ModelExtensibleSearchPage.assertMessage4"); //$NON-NLS-1$
    	
    	// create participant tabs
		for (ParticipantTabDescriptor participantAreaDescriptor : participantAreaExtensionManager.getModelSearchParticipantDescriptors()) {
			if (getModelSearchPageID().equals(participantAreaDescriptor.getTargetSearchPageID())) {
				IModelSearchArea pArea = participantAreaDescriptor.createArea(searchParticipantTabFolder, this);
				
				TabItem participantTabItem = new TabItem(searchParticipantTabFolder, SWT.NONE);
				participantTabItem.setText(participantAreaDescriptor.getLabel());
				participantTabItem.setToolTipText(participantAreaDescriptor.getTooltip());
				participantTabItem.setControl(pArea.getControl());
				participantTabItem.setImage(participantAreaDescriptor.getImage());
				participantTabItem.setData(participantAreaDescriptor);
				
				GridData gd1 = new GridData(GridData.FILL_BOTH);
				gd1.heightHint = 180;
				participantTabItem.getControl().setLayoutData(gd1);
				
				GridData gd2 = new GridData(GridData.FILL_BOTH);
				gd2.heightHint = 180;
				
				pArea.getControl().setLayoutData(gd2);
				
				//
				// BEGIN: https://bugs.eclipse.org/bugs/show_bug.cgi?id=231988
				//
				Object data = participantAreaDescriptor.participantModelSearchArea;
				if (data instanceof AbstractModelSearchCompositeArea) {
					Map<String, Object> map = ((AbstractModelSearchCompositeArea)data).getDataMap();
					if (map!= null) {
						if (map.get("SETTINGS_PREFIX") == null) { //$NON-NLS-1$
							map.put("SETTINGS_PREFIX", getModelSearchPageID()); //$NON-NLS-1$
						}
					}
				}
				//
				// END
				//
				
				pArea.loadDialogSettings();
				
				if (pArea instanceof IModelSearchParticipantArea) { 
					for (QueryTabDescriptor queryAreaDescriptor : queryAreaExtensionManager.getModelSearchQueryAreas()) {
						IModelSearchArea qArea = queryAreaDescriptor.getQueryModelSearchArea();
						if (qArea instanceof ITargetMetaElementSelectionProvider) {
							((ITargetMetaElementSelectionProvider)qArea).addTargetMetaElementSelectionListener((ITargetMetaElementSelectionListener)pArea);
						}
					}
				}
			}
		}
		
		searchParticipantTabFolder.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.item instanceof TabItem) {
					Object data = ((TabItem)event.item).getData();
					if (data instanceof ParticipantTabDescriptor) {
						ParticipantTabDescriptor participantTabDesc = (ParticipantTabDescriptor) data;
						currentParticipantDescID = participantTabDesc.ID;
					}
				}
			}
		} );

		if (searchParticipantTabFolder.getItems().length > 0) {
			currentParticipantDescID = ((ParticipantTabDescriptor)searchParticipantTabFolder.getItem(0).getData()).getID();
		}
	}
	
	// Associate an instance of model search engine to its ID according to extension point declaration
	private void initModelSearchEngineMap() {
		for (ModelSearchEngineDescriptor modelSearchEngineDescriptor : searchEngineExtensionManager.getModelSearchEngines()) {
			searchEngineDescriptorsMap.put(modelSearchEngineDescriptor.getID(), modelSearchEngineDescriptor);
		}
	}
	// Associate an instance of model search engine to its ID according to extension point declaration
	private void initEngineMappingsList() {
		for (EngineMappingDescriptor engineMappingDescriptor : ModelSearchEngineMappingExtensionManager.getInstance().getModelSearchEngineMappingDescriptors()) {
			engineMappingDescriptorList.add(engineMappingDescriptor);
		}
	}
	
	public void createControl(Composite parent) {
		GridLayout glP = new GridLayout();
		glP.marginHeight = glP.marginWidth = 0;
	
		parent.setLayout(glP);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		getContainer().setPerformActionEnabled(false);
		
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);

		GridLayout gl0 = new GridLayout();
		gl0.marginHeight = gl0.marginWidth = 0;
		
		sashForm.setLayout(gl0);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 375;
		data.widthHint = 225;

		sashForm.setLayoutData(data);
	
		createSearchQueryArea(sashForm);
		
		Composite participantTabsContainer = new Composite(sashForm, SWT.NONE);
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		gd1.heightHint = 250;
		participantTabsContainer.setLayout(new GridLayout());
		participantTabsContainer.setLayoutData(gd1);
		
		createParticipantTabsArea(participantTabsContainer);
		
		initModelSearchEngineMap();
		
		initEngineMappingsList();
		
		loadDialogSettings();
		
		sashForm.setWeights(new int[]{40, 60});
		
		setControl(sashForm);
		
		hookListeners();
		
		handleTargetMetaModelEnvironmentConfiguration();
		
		validateInput();
		
		parent.pack();
	}
	
	/* 
	 * Called whenever user clicks on the search button 
	 */
    private IModelSearchQuery newQuery() {
    	String expression = ""; //$NON-NLS-1$
    	ParticipantTabDescriptor participant =  getCurrentlySelectedParticipantDescriptor();
    	QueryTabDescriptor queryTabDescriptor = getCurrentlySelectedQueryDescriptor();
    	ModelSearchEngineDescriptor searchEngine = getSearchEngineDescriptorFromPageSelection();
    	
    	if (!(searchEngine instanceof ModelSearchEngineDescriptor)) return null;
    	
        IModelSearchQueryParameters parameters = searchEngine.getModelSearchParametersFactory().createSearchQueryParameters();
        
        IModelSearchArea qArea = queryTabDescriptor.getQueryModelSearchArea();
        if (qArea instanceof AbstractModelSearchQueryArea) {
        	AbstractModelSearchQueryArea queryArea = (AbstractModelSearchQueryArea) qArea;
        	
	        parameters.setScope(computeModelSearchWorkspaceScope(parameters));
	        
	        Map<String, Object> dataMap = queryArea.getDataMap();
	        for (String key : dataMap.keySet()) {
	        	parameters.setData(key, dataMap.get(key));
	        }
	        
	        expression = queryArea.getQueryExpression();
        }
        
        IModelSearchArea pArea = participant.getParticipantModelSearchArea();
        if (pArea instanceof IModelSearchParticipantArea) {
        	parameters.setParticipantElements(((IModelSearchParticipantArea) pArea).getSelectedParticipants());
        	parameters.setEStructuralFeatures(((IModelSearchParticipantArea) pArea).getSelectedFeatures());
        }
        
        return searchEngine.getModelSearchQueryFactory().createModelSearchQuery(expression, parameters);
    }
    
    // dialog settings storage
	private void storeQueryTabsDialogSettings(IDialogSettings settings) {
        IDialogSettings querySectionDialogSettings;
        if ((querySectionDialogSettings = settings.getSection(QUERY_SECTION_DIALOG_SETTINGS_ID))==null) {
        	querySectionDialogSettings = settings.addNewSection(QUERY_SECTION_DIALOG_SETTINGS_ID);
        }
    	String selectedQueryTabID = getCurrentlySelectedQueryDescriptor().getID();
        querySectionDialogSettings.put(SELECTED_QUERY_ID, selectedQueryTabID);
        
        // store dialog settings for all query tabs
		for (TabItem tabItem : searchQueryTabFolder.getItems()) {
			QueryTabDescriptor queryAreaDescriptor = (QueryTabDescriptor) tabItem.getData();
			queryAreaDescriptor.getQueryModelSearchArea().storeDialogSettings();
		}
	}
	
	private void storeParticipantTabsDialogSettings(IDialogSettings settings) {
	}
	
    private void saveDialogSettings() {
        storeQueryTabsDialogSettings(settings);
        storeParticipantTabsDialogSettings(settings);
    }
    
    //dialog settings loading
    private void loadQueryTabsDialogSettings() {
        IDialogSettings querySectionDialogSettings;
        if ((querySectionDialogSettings = settings.getSection(QUERY_SECTION_DIALOG_SETTINGS_ID))==null) {
        	querySectionDialogSettings = settings.addNewSection(QUERY_SECTION_DIALOG_SETTINGS_ID);
        }

    	queryTabID = querySectionDialogSettings.get(SELECTED_QUERY_ID);
    	queryTabItemToSelect = null;

    	// create participant tabs
		for (TabItem tabItem : searchQueryTabFolder.getItems()) {
			QueryTabDescriptor queryAreaDescriptor = (QueryTabDescriptor) tabItem.getData();
			if (queryTabID!=null && queryTabID.equals(queryAreaDescriptor.ID)) {
				queryTabItemToSelect = tabItem;
			}
		}

		if (queryTabItemToSelect != null) {
			searchQueryTabFolder.setSelection(queryTabItemToSelect);
		} else {
			if (searchQueryTabFolder.getItemCount()>0) {
				searchQueryTabFolder.setSelection(0);
			}
		}
    }
    
    private void loadParticipantTabsDialogSettings() {}
    
    private void loadDialogSettings() {
    	loadQueryTabsDialogSettings();
    	loadParticipantTabsDialogSettings();
    }
    
    private void prepareQueries() {
    	// prepare queries tabs
		for (TabItem tabItem : searchQueryTabFolder.getItems()) {
			QueryTabDescriptor queryAreaDescriptor = (QueryTabDescriptor) tabItem.getData();
			queryAreaDescriptor.getQueryModelSearchArea().prepare();
		}
    }
    
    private void prepareParticipants() {
     	// do nothing
    }
    
    private void prepareQuery() {
    	prepareQueries();
    	prepareParticipants();
    }
    
    private boolean validateQueryTabs() {
    	// validate queries tabs
		for (TabItem tabItem : searchQueryTabFolder.getItems()) {
			QueryTabDescriptor queryAreaDescriptor = (QueryTabDescriptor) tabItem.getData();
			if (!queryAreaDescriptor.getQueryModelSearchArea().validateStatus()) {
				return false;
			}
		}
		return true;
    }
    
    private boolean vaidateParticipantTabs() {
    	return true;
    }
    
    private boolean validateInput() {
    	boolean status = validateQueryTabs() && vaidateParticipantTabs();
    	getContainer().setPerformActionEnabled(status);
    	return status;
    }
    
	
//	/** 
//	 * Returns current participant configuration coming from participant area controls settings 
//	 */
	public ParticipantTabDescriptor getCurrentlySelectedParticipantDescriptor() {
		assert participantAreaExtensionManager.getModelSearchParticipantDescriptors() != null : Messages.getString("ModelExtensibleSearchPage.assertMessage5"); //$NON-NLS-1$
		assert participantAreaExtensionManager.getModelSearchParticipantDescriptors()[0] != null : Messages.getString("ModelExtensibleSearchPage.assertMessage7"); //$NON-NLS-1$

		return participantAreaExtensionManager.find(currentParticipantDescID);
	}
	
	/** 
	 * Returns current participant configuration coming from query area controls settings 
	 */
	public QueryTabDescriptor getCurrentlySelectedQueryDescriptor() {
		assert searchQueryTabFolder.getSelection() != null : Messages.getString("ModelExtensibleSearchPage.assertMessage8"); //$NON-NLS-1$
		assert searchQueryTabFolder.getSelection().length == 1 : Messages.getString("ModelExtensibleSearchPage.assertMessage9"); //$NON-NLS-1$
		assert searchQueryTabFolder.getSelection()[0] != null : Messages.getString("ModelExtensibleSearchPage.assertMessage10"); //$NON-NLS-1$
		
		return (QueryTabDescriptor)searchQueryTabFolder.getSelection()[0].getData();
	}
	
	/** 
	 * Returns current participant tab item coming from query area controls settings 
	 */
	public TabItem getCurrentlySelectedQueryTabItem() {
		if (searchQueryTabFolder == null ||
			searchQueryTabFolder.getSelection() == null ||
			searchQueryTabFolder.getSelection().length < 1 ||
			searchQueryTabFolder.getSelection()[0] == null) return null;
			
		return searchQueryTabFolder.getSelection()[0];
	}
	
	/**
	 * Returns search engine associated with given (query, participant) tabs selection. 
	 */
	public ModelSearchEngineDescriptor getSearchEngineDescriptorFromPageSelection()  {
		return searchEngineDescriptorsMap.get(getApplicableModelSearchEngineIDIfAny());
	}

	private String getApplicableModelSearchEngineIDIfAny() {
		QueryTabDescriptor qDesc = getCurrentlySelectedQueryDescriptor();
		for (EngineMappingDescriptor engineMappingDescriptor : engineMappingDescriptorList) {
			if (engineMappingDescriptor.getQueryTabID().equals(qDesc.getID())) {
				return engineMappingDescriptor.getSearchEngineID();
			}
		}
		return ""; //$NON-NLS-1$
	}
	
	private void hookListeners() {
	}
	
	private void handleTargetMetaModelEnvironmentConfiguration() {
		QueryTabDescriptor qDesc = getCurrentlySelectedQueryDescriptor();
		ParticipantTabDescriptor pDesc = getCurrentlySelectedParticipantDescriptor();
		
		IModelSearchArea modelSearchArea = pDesc.getParticipantModelSearchArea();
		if (modelSearchArea instanceof IModelSearchParticipantArea) {
			for (String targetMetaModelID : ((IModelSearchParticipantArea)modelSearchArea).getTargetMetaModelIDs()) {
				if (qDesc.getQueryModelSearchArea() instanceof ITargetMetaModelHandler) {
					((ITargetMetaModelHandler)qDesc.getQueryModelSearchArea()).handleTargetMetaModel(targetMetaModelID);
				}
				if (qDesc.getQueryModelSearchArea() instanceof ITargetMetaElementProvider) {
					EObject element = ((ITargetMetaElementProvider)qDesc.getQueryModelSearchArea()).getTargetMetaElement();
					if (modelSearchArea instanceof ITargetMetaElementHandler) {
						((ITargetMetaElementHandler)modelSearchArea).handleTargetMetaElementParticipantSelection(element);
					}
				}
			}
		}
	}
	
	/**
	 * Sets the search page's container.
	 * @param searchPageContainer the container to set
	 */
	public void setContainer(ISearchPageContainer c) {
		searchPageContainer = c;
		searchPageContainer.setPerformActionEnabled(false);
	}
	
	/**
	 * Return Page Container for the current model search page.
	 * @return The current model search page PageContainer, null otherwise.
	 */
	public ISearchPageContainer getContainer() {
		return searchPageContainer;
	}
	/** 
	 * Called whenever user clicks on the search button 
	 */
	public boolean performAction() {
        try {
        	// Prepare data and settings before firing a new Search Query
        	prepareQuery();
        
            // Process the search operation in background
            NewSearchUI.runQueryInBackground(newQuery());

            // Store Dialog Settings between two queries
        	saveDialogSettings();
        } catch (Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("ModelExtensibleSearchPage.addExtensionErrorMessage"), e)); //$NON-NLS-1$
            return false;
        }
        return true;
	}
	

	// 
	// Model Search Replace implementations 
	//
	public boolean performReplace() {
        try {
        	// Prepare data and settings before firing a new Search Query
        	prepareQuery();
        	
        	handleReplaceAction(newQuery());
            
            // Store Dialog Settings between two queries
    		saveDialogSettings();
        } catch (Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("ModelExtensibleSearchPage.addExtensionErrorMessage"), e)); //$NON-NLS-1$
            return false;
        }
        return true;
	}
	
	abstract public ITransformation<EObject, IModelSearchQuery, String, String> getTransformation(EObject element, IModelSearchQuery query, String value);

	public void handleReplace(Object element, IModelSearchQuery query, Object value) {
		if (element instanceof EObject && value instanceof String) {
			ITransformation<?, ?, ?, ?> transformation = getTransformation((EObject)element, query, (String)value);
			if (transformation.isValid()) {
				transformation.perform();
				handleTransformationResult(transformation.getResult());
			}
		}
	}
	
	protected void handleTransformationResult(Object result) {
		// user to override and implementing specific behavior
	}
	
	private void handleReplaceAction(IModelSearchQuery query) {
		try {
    		TextualModelSearchReplaceRefactoring refactoring = new TextualModelSearchReplaceRefactoring(query, this);
   			TextualModelSearchReplaceWizard refactoringWizard = new TextualModelSearchReplaceWizard(refactoring, RefactoringWizard.NONE);
   			TextualModelSearchRefactoringOperation op = new TextualModelSearchRefactoringOperation(refactoringWizard);
    		op.run(getShell(), Messages.getString("AbstractModelSearchPage.ReplacePageTitle")); //$NON-NLS-1$
    	} catch (InterruptedException e) {
    		// refactoring got cancelled
    	}
	}
}
