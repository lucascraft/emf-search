/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * CodegenTestsUtil.java
 *
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.codegen.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.core.ModelSearchCoreGenerator;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.ui.ModelSearchUIGenerator;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.EStringAccessor;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.GeneratorFactory;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;
import org.eclipse.papyrus.infra.emf.search.codegen.wizard.textual.TextualGenEnum;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.results.ModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.genmodel.engine.GenModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.genmodel.engine.GenModelTextualEngine;
import org.eclipse.papyrus.infra.emf.search.genmodel.helper.builder.GenModelTextualModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;

public final class CodegenTestsUtil {
	
	private static CodegenTestsUtil instance;
	
	public static CodegenTestsUtil instance() {
		return instance == null ? instance = new CodegenTestsUtil() : instance; 
	}
	
	private abstract class CodegenResourceProxyVisitor implements IResourceProxyVisitor {
		File file;
		public File getFile() {
			return file;
		}
		public void setFile(File file) {
			this.file = file;
		}
	}
	
	public File findInProject(IProject project, final String filename) {
		if (	project == null || 
				filename == null || 
				"".equals(filename)
		) {
			return null;
		}
		CodegenResourceProxyVisitor codegenResourceProxyVisitor = new CodegenResourceProxyVisitor() {
			public boolean visit(IResourceProxy proxy) throws CoreException {
				if (proxy.getName().equals(filename)) {
					try {
						setFile(new File(proxy.requestResource().getRawLocationURI().toURL().getFile()));
						return false;
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
				return true;
			}
		};
		try {
			project.accept(codegenResourceProxyVisitor, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return codegenResourceProxyVisitor.getFile();
	}
	
	/**
	 * Visit given file and add it if potential search participant.
	 */
	public GenModel visit(File file) {
		if (isParticipantCurrentSearchEngineValid(file)) {
			// Create a resource set.
			ResourceSet resourceSet = new ResourceSetImpl();

			// Get the URI of the model file.
			URI fileURI = URI.createFileURI(file.getAbsolutePath());

			initResourceSet(resourceSet, fileURI);
			
			try {
				Resource resource = resourceSet.getResource(fileURI, true);
				if (!resource.getContents().isEmpty()) {
					EObject o = resource.getContents().get(0);
					if (o instanceof GenModel) {
						return (GenModel)o;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	protected void initResourceSet(ResourceSet resourceSet, URI fileURI) {
		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}
	
	
	/**
	 * Evaluates whether a resource is a potential search participant or not.
	 * 
	 * @param resource Currently visited resource
	 * 
	 * @return true if resource is potential search participant according to the 
	 * contributed validator, false otherwise
	 */
	protected boolean isParticipantCurrentSearchEngineValid(File f) {
		if (f instanceof File && f.canRead() && f.exists() && ! f.isHidden()) {
			return f.getName().endsWith(".genmodel"); //$NON-NLS-1$
		} 
		return false;
	}

	/**
	 * Initializes adapters for FilteredContainerCheckedTreeViewer. 
	 * 
	 * It allows to compute EOperation/EAttribute results from a global workspace model search query.
	 */
	public List<GenTypedElement> queryEStringStructuralFeaturesFromGivenGenModel(final GenModel genModel, File file) {
		IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchProjectScope(
				GenModelTextualEngine.ID, //$NON-NLS-1$
				new String[] {
					ModelSearchResourceUtils.getCodegenCommonTestProject().getName()
				}
		);
		final IModelSearchQuery query = new GenModelTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(
			"*",
			Arrays.asList(
				new EClassifier[] {
					GenModelPackage.Literals.GEN_TYPED_ELEMENT	
				}
			),
			scope,
			GenModelPackage.eNS_URI
		);
		
		query.run();
				
		IModelSearchResult results = buildEStringOnlyModelSearchResult(genModel, query.getModelSearchResult());
				
		List<GenTypedElement> genTList = new ArrayList<GenTypedElement>();
		for (Object e : results.getResultsObjectsAsList()) {
			if (e instanceof GenTypedElement) {
				genTList.add((GenTypedElement) e);
			}
		}
		
		return genTList;
	}
	
	/**
	 * Build an intermediate result hierarchy from EOperation/EAttribute model search query.
	 * 
	 * @param results EOperation/EAttribute model search query results
	 * 
	 * @return an intermediate EOperation/EAttribute result hierarchy, void {@link IModelSearchResult} otherwise 
	 */
	private IModelSearchResult buildEStringOnlyModelSearchResult(GenModel genModel, IModelSearchResult results) {
		GenModelSearchQuery nullQuery = new GenModelSearchQuery(null, null);
		ModelSearchResult nullResult = new ModelSearchResult(nullQuery);
		for (EObject o : refineResult(results.getResultsObjectsAsList())) {
			if (belongsToAGenModelUsedEPackage(genModel, o)) {
				nullResult.insert(o.eResource(), nullQuery.buildSearchResultEntryHierarchy(o.eResource(), o), false);
			}
		}
		return nullResult;
	}
	
	/**
	 * Keep only EString typed artifacts such as : 
	 * 
	 * <ul>
	 * <li>EOperation without EParameter (hum ... not quite yet)</li>
	 * <li>EAttribute</li>
	 * </ul>
	 * 
	 * @param results a collection of EString typed artifacts such as EOperation & EAttribute
	 * 
	 * @return a refined collection of EString typed artifacts such as EOperation without EParameter & EAttribute
	 */
	private Collection<GenBase> refineResult(Collection<Object> results) {
		Collection<GenBase> col = new ArrayList<GenBase>();
		for (Object r : results) {
			if (r instanceof GenTypedElement) {
				GenTypedElement gTypeElem = (GenTypedElement) r;
				if (gTypeElem.getEcoreModelElement() instanceof EAttribute) {
				EAttribute eAttribute = (EAttribute)gTypeElem.getEcoreModelElement();
				if (eAttribute.getEType() != null) {
					String signature = eAttribute.getEType().getInstanceTypeName();
						if("java.lang.String".equals(signature)) { //$NON-NLS-1$
							if (gTypeElem instanceof GenFeature) {
								col.add(gTypeElem);
							}
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
	 * @param o EObject the EPackage belonging has to be checked
	 * 
	 * @return true if the given EObject belongs to an EPackage referenced by the currently 
	 * considered {@link GenModel}, false otherwise
	 */
	private boolean belongsToAGenModelUsedEPackage(GenModel genModel, EObject o) {
		if (o != null) {
			if (o instanceof GenPackage) {
				for (GenPackage gPkg : genModel.getAllGenUsedAndStaticGenPackagesWithClassifiers()) {
					if (gPkg.getEcorePackage().getNsURI().equals(((GenPackage)o).getEcorePackage().getNsURI())) {
						return true;
					}
				}
			}
			return belongsToAGenModelUsedEPackage(genModel, o.eContainer());
		}
		return false;
	}

	private List<EStringAccessor> buildEStringAccessors(Collection<GenTypedElement> eStringTypeElements) {
		List<EStringAccessor> list = new ArrayList<EStringAccessor>();
		
		for (GenTypedElement e : eStringTypeElements) {
			EStringAccessor accessor = GeneratorFactory.eINSTANCE.createEStringAccessor();
			
			String literal = 
			    ((GenFeature)e).getGenPackage().getPackageInterfaceName() + 
			    ".Literals." +  //$NON-NLS-1$
			    ((GenFeature)e).getGenPackage().getClassifierID(((GenFeature)e).getGenClass()) + 
			    "__" + //$NON-NLS-1$
			    ((GenFeature)e).getFormattedName().toUpperCase().replace(' ', '_');
			
			accessor.setGenPackage(((GenFeature)e).getGenPackage());
			accessor.setGenTypedElement(e);
			accessor.setLiteral(literal);
			
			list.add(accessor);
		}
		
		return list;
	}
	
	public void generateModelEditAndEditor(GenModel genModel, Collection<GenTypedElement> eStringTypeElements, IProgressMonitor monitor) {
		Generator gen = new Generator();
				
		gen.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, CodeGenUtil.EclipseUtil.createMonitor(monitor, 1));
		gen.generate(genModel, GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE, CodeGenUtil.EclipseUtil.createMonitor(monitor, 1));
		gen.generate(genModel, GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE, CodeGenUtil.EclipseUtil.createMonitor(monitor, 1));
	}
	
	public void generateCoreAndUISearchInfrastructure(
			GenModel genModel, 
			Collection<GenTypedElement> eStringTypeElements, 
			TextualGenEnum generationKind, IProgressMonitor monitor) {
		
		ModelSearchGenSettings modelSearchGenSettings = GeneratorFactory.eINSTANCE.createModelSearchGenSettings();

		modelSearchGenSettings.setTextualSettings(GeneratorFactory.eINSTANCE.createTextualSettings());
		modelSearchGenSettings.getTextualSettings().getEStringAccessors().addAll(buildEStringAccessors(eStringTypeElements));
		modelSearchGenSettings.setGenModel(genModel);
				
		try {
			if (TextualGenEnum.CORE.equals(generationKind) || TextualGenEnum.CORE_AND_UI.equals(generationKind) ) {
				new ModelSearchCoreGenerator(modelSearchGenSettings).generate(BasicMonitor.toMonitor(monitor));
			}
			if (TextualGenEnum.UI.equals(generationKind) || TextualGenEnum.CORE_AND_UI.equals(generationKind) ) {
				new ModelSearchUIGenerator(modelSearchGenSettings).generate(BasicMonitor.toMonitor(monitor));
			}
		} catch (JETException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
