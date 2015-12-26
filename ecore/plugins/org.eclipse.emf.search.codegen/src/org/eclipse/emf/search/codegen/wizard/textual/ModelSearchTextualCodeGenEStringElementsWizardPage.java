/*******************************************************************************
 * Copyright (c) 2008, 2009 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchTextualCodeGenEStringElementsWizardPage.java
 * 
 * Contributors: 
 * 			Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.codegen.wizard.textual;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelEditPlugin;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelItemProviderAdapterFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.search.codegen.Activator;
import org.eclipse.emf.search.codegen.l10n.Messages;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.results.ModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.genmodel.engine.GenModelSearchQuery;
import org.eclipse.emf.search.genmodel.engine.GenModelTextualEngine;
import org.eclipse.emf.search.genmodel.helper.builder.GenModelTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ui.providers.DecoratingModelSearchResultLabelProvider;
import org.eclipse.emf.search.ui.utils.ContainerCheckboxFilteredTree;
import org.eclipse.emf.search.ui.utils.ModelSearchResultItemProvider;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.progress.UIJob;

public final class ModelSearchTextualCodeGenEStringElementsWizardPage extends
		WizardPage {

	private ContainerCheckboxFilteredTree eStringMetaElementContainerCheckboxFilteredTree;
	private GenModel genModel;
	
	private Button genCoreSearchCheckbox;
	private Button genUISearchCheckbox;
	
	private TextualGenEnum generationKind;

	public TextualGenEnum getGenerationKind() {
		return generationKind;
	}

	public void setGenerationKind(TextualGenEnum generationKind) {
		this.generationKind = generationKind;
	}

	/**
	 * Constructor
	 * 
	 * @param gMdl GenModel to get EPackage data from
	 */
	public ModelSearchTextualCodeGenEStringElementsWizardPage(GenModel gMdl) {
		super(
		        "ModelSearchTextualCodeGenEStringElementsWizardPage", //$NON-NLS-1$
		        Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.PageText"), //$NON-NLS-1$
		        ModelSearchImagesUtil.getImageDescriptor(
		                Activator.getDefault().getBundle(),
		                "/icons/wizban/gensearch_wiz.gif" //$NON-NLS-1$
		        )
		);
		genModel = gMdl;
		setGenerationKind(TextualGenEnum.CORE_AND_UI);
	}
	
	@Override
	public String getDescription() {
        return Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.PageDesc"); //$NON-NLS-1$
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		parent.getShell().setImage(
                  ModelSearchImagesUtil.getImage(
                          Activator.getDefault().getBundle(),
                          "/icons/obj16/esearch.gif" //$NON-NLS-1$
                  )
          );

		setEStringMetaElementContainerCheckboxFilteredTree(new ContainerCheckboxFilteredTree(
			parent,
			SWT.BORDER,
			new PatternFilter()
		));
		
		getEStringMetaElementContainerCheckboxFilteredTree().getViewer().getControl().setLayoutData(
			new GridData(GridData.FILL_BOTH)
		);
		
		initAdapters();
		
		setControl(getEStringMetaElementContainerCheckboxFilteredTree());
		
		TreeViewer v = getEStringMetaElementContainerCheckboxFilteredTree().getViewer();
		
		if (v instanceof CheckboxTreeViewer) {
			((CheckboxTreeViewer)v).addCheckStateListener(
				new ICheckStateListener() {
					public void checkStateChanged(CheckStateChangedEvent event) {
						getContainer().updateButtons();
					}
				}
			);
		}
		
		genCoreSearchCheckbox = new Button(parent, SWT.CHECK);
		genCoreSearchCheckbox.setText(Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.GenerateCoreEngineCheckboxLabel")); //$NON-NLS-1$
		genCoreSearchCheckbox.setSelection(true);
		genCoreSearchCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateGenerationKind();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		genUISearchCheckbox = new Button(parent, SWT.CHECK);
		genUISearchCheckbox.setText(Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.GenerateSearchPageCheckboxLabel")); //$NON-NLS-1$
		genUISearchCheckbox.setSelection(true);
		genUISearchCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateGenerationKind();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private void updateGenerationKind() {
		if (genCoreSearchCheckbox.getSelection() && genUISearchCheckbox.getSelection()) {
			setGenerationKind(TextualGenEnum.CORE_AND_UI);
		} else if (!genCoreSearchCheckbox.getSelection() && !genUISearchCheckbox.getSelection()) {
			setGenerationKind(TextualGenEnum.NONE);
		} else if (!genCoreSearchCheckbox.getSelection() && genUISearchCheckbox.getSelection()) {
			setGenerationKind(TextualGenEnum.UI);
		} else if (genCoreSearchCheckbox.getSelection() && !genUISearchCheckbox.getSelection()) {
			setGenerationKind(TextualGenEnum.CORE);
		}
		getContainer().updateButtons();
	}
	/**
	 * Setter for {@link ContainerCheckboxFilteredTree} attribute reference
	 * 
	 * @param {@link ContainerCheckboxFilteredTree} reference to set attribute with
	 */
	public void setEStringMetaElementContainerCheckboxFilteredTree(
			ContainerCheckboxFilteredTree eStringMetaElementContainerCheckboxFilteredTree) {
		this.eStringMetaElementContainerCheckboxFilteredTree = eStringMetaElementContainerCheckboxFilteredTree;
	}
	
	/**
	 * Getter for {@link ContainerCheckboxFilteredTree} attribute reference
	 * 
	 * @return {@link ContainerCheckboxFilteredTree} attribute
	 */
	public ContainerCheckboxFilteredTree getEStringMetaElementContainerCheckboxFilteredTree() {
		return eStringMetaElementContainerCheckboxFilteredTree;
	}
	
	private List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

		composeableAdapterFactories.add(new GenModelItemProviderAdapterFactory());
		composeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());

		return composeableAdapterFactories;
	}

	/**
	 * Initializes adapters for FilteredContainerCheckedTreeViewer. 
	 * 
	 * It allows to compute EOperation/EAttribute results from a global workspace model search query.
	 */
	private void initAdapters() {
		IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(
				GenModelTextualEngine.ID //$NON-NLS-1$
		);
		final IModelSearchQuery query = new GenModelTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(
			"*",
			GenModelPackage.Literals.GEN_TYPED_ELEMENT,
			scope
		);
		
		UIJob queryMonitoredJob = new UIJob (Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.EStringFeaturesQueryUIJob")) { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				return query.run(monitor);
			}
		};
		
		queryMonitoredJob.addJobChangeListener(new IJobChangeListener() {
			public void sleeping(IJobChangeEvent event) { }
			public void scheduled(IJobChangeEvent event) { }
			public void running(IJobChangeEvent event) { }
			public void done(IJobChangeEvent event) {
				IModelSearchResult results = query.getModelSearchResult();
				if (results != null && results.getResultsObjectsAsList().size() > 0) {
					IModelSearchResult eStringResultsOnly = buildEStringOnlyModelSearchResult(results);
					if (eStringResultsOnly.getTotalMatches() > 0) {
						AdapterFactoryLabelProvider lbl = new AdapterFactoryLabelProvider(
								new ComposedAdapterFactory(getMetaElementComposeableAdapterFactories())){
									@Override
									public String getText(Object object) {
										if (object instanceof GenBase) {
											EObject elem = ((GenBase)object).getEcoreModelElement();
											return ((ENamedElement)elem).getName();
										}
										return super.getText(object);
									}
									public Image getImage(Object object) {
										try {
											if (object instanceof ENamedElement) {
												String imagePath = "/icons/full/obj16/E" + ((ENamedElement)object).getName().substring(3) + ".gif"; //$NON-NLS-1$  //$NON-NLS-2$
												URL url = FileLocator.find(EcoreEditPlugin.getPlugin().getBundle(), new Path(imagePath), null);
												if (url != null) {
													return ModelSearchImagesUtil.getImage(url);
												}
											}
										} catch (Throwable t) {
											Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Error while attempmting to retrieve image from edit" + GenModelEditPlugin.getPlugin().getBundle() + " bundle")); //$NON-NLS-1$  //$NON-NLS-2$
										} 
										return super.getImage(object);
									}
							};
						eStringMetaElementContainerCheckboxFilteredTree.getCheckboxTreeViewer().setLabelProvider(new DecoratingModelSearchResultLabelProvider(lbl.getAdapterFactory(), eStringResultsOnly));
					    eStringMetaElementContainerCheckboxFilteredTree.getCheckboxTreeViewer().setContentProvider(new ModelSearchResultItemProvider());
					    eStringMetaElementContainerCheckboxFilteredTree.getCheckboxTreeViewer().setInput(eStringResultsOnly);
					   
					    // Force result tree to expand to EString leaves !
					    eStringMetaElementContainerCheckboxFilteredTree.getFilterControl().setText("*"); //$NON-NLS-1$
					} else {
						queryErrorHandling();
					}
				} else {
					queryErrorHandling();
				}
			}
			public void awake(IJobChangeEvent event) { }
			public void aboutToRun(IJobChangeEvent event) { }
		});
		
		queryMonitoredJob.schedule();
	}
	
	/**
	 * Get EString checked EString leaves from FilteredContainerCheckedTreeViewer
	 * 
	 * @return EString EOperation/EAttribute element list
	 */
	public List<GenTypedElement> getETypeLeavesSelectionAsList() {
		List<GenTypedElement> objList = new ArrayList<GenTypedElement>();
		TreeViewer viewer = getEStringMetaElementContainerCheckboxFilteredTree().getViewer();
		if (viewer instanceof ContainerCheckedTreeViewer) {
			Object[] elements = ((ContainerCheckedTreeViewer)viewer).getCheckedElements();
			for (Object o : elements) {
				if (o instanceof IModelResultEntry) {
					IModelResultEntry entry = (IModelResultEntry) o;
					if (entry.getSource() instanceof GenTypedElement) {
						objList.add((GenTypedElement)entry.getSource());
					}
				}
			}
		}
		return objList;
	}
	
	/**
	 * Check whether or not a selection is only composed of EString stuff 
	 * 
	 * @return true if a selection is only composed of EString stuff, false otherwise
	 */
	public boolean selectionIsOnePlusETypedArtifact() {
		return getETypeLeavesSelectionAsList().size() > 0;
	}
	
	/**
	 * Build an intermediate result hierarchy from EOperation/EAttribute model search query.
	 * 
	 * @param results EOperation/EAttribute model search query results
	 * 
	 * @return an intermediate EOperation/EAttribute result hierarchy, void {@link IModelSearchResult} otherwise 
	 */
	private IModelSearchResult buildEStringOnlyModelSearchResult(IModelSearchResult results) {
		GenModelSearchQuery nullQuery = new GenModelSearchQuery(null, null);
		ModelSearchResult nullResult = new ModelSearchResult(nullQuery);
		for (EObject o : refineResult(results.getResultsObjectsAsList())) {
			if (belongsToAGenModelUsedEPackage(o)) {
				nullResult.insert(o.eResource(), nullQuery.buildSearchResultEntryHierarchy(o.eResource(), o), false);
			}
		}
		return nullResult;
	}
	
	private List<String> getSupportedETypeList() {
		return Arrays.asList(new String[] {
				"EBigDecimal",
				"EBigInteger",
				"EBoolean",
				"EBooleanObject",
				"EByte",
				"EByteArray",
				"EByteObject",
				"EChar",
				"ECharacterObject",
				"EDate",
				"EDouble",
				"EDoubleObject",
				"EFloat",
				"EFloatObject",
				"EInt",
				"EIntegerObject",
				"EJavaClass<T>",
				"EJavaObject",
				"ELong",
				"ELongObject",
				"EShort",
				"EShortObject",
				"EString"
		});
	}
	
	/**
	 * Keep only EString typed artifacts such as : 
	 * 
	 * <ul>
	 * <li>EAttribute</li>
	 * </ul>
	 * 
	 * @param results a collection of EString typed EAttribute artifacts 
	 * 
	 * @return a refined collection of EString typed EAttribute elements
	 */
	private Collection<GenBase> refineResult(Collection<Object> results) {
		Collection<GenBase> col = new ArrayList<GenBase>();
		for (Object r : results) {
			if (r instanceof GenTypedElement) {
				GenTypedElement gTypeElem = (GenTypedElement) r;
				if (gTypeElem.getEcoreModelElement() instanceof EAttribute) {
					EAttribute eAttribute = (EAttribute) gTypeElem.getEcoreModelElement();
					if (   !eAttribute.isMany() && 
							eAttribute.getEAttributeType() != null && 
							eAttribute.getEAttributeType().isSerializable()) {
						
						String type = eAttribute.getEAttributeType().getName();//EcoreUtil.convertToString(eAttribute.getEAttributeType(), eAttribute.getEAttributeType().getDefaultValue());
						if (type != null && getSupportedETypeList().contains(type)) {
							col.add(gTypeElem);
						}
					}
				}
			}
		}
		return col;
	}
	
	/**
	 * Checks whether an EObject belongs to an EPackage referenced by the currently considered 
	 * {@link GenModel}
	 * 
	 * @param o EObject the EPAckage belonging has to be checked
	 * 
	 * @return true if the given EObject belongs to an EPackage referenced by the currently 
	 * considered {@link GenModel}, false otherwise
	 */
	private boolean belongsToAGenModelUsedEPackage(EObject o) {
		if (o != null) {
			if (o instanceof GenPackage) {
				for (GenPackage gPkg : genModel.getAllGenUsedAndStaticGenPackagesWithClassifiers()) {
					if (gPkg.getEcorePackage().getNsURI().equals(((GenPackage)o).getEcorePackage().getNsURI())) {
						return true;
					}
				}
			}
			return belongsToAGenModelUsedEPackage(o.eContainer());
		}
		return false;
	}
	
	/**
	 * Handles error while initializing page
	 */
	private void queryErrorHandling() {
		MessageBox msgBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
		msgBox.setMessage(Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.QueryErrorHandlingMsgBoxMessage")); //$NON-NLS-1$
		msgBox.setText(Messages.getString("ModelSearchTextualCodeGenEStringElementsWizardPage.QueryErrorHandlingMsgBoxText")); //$NON-NLS-1$
		msgBox.open();
		
		getWizard().getContainer().getShell().close();
		getWizard().dispose();
	}
}