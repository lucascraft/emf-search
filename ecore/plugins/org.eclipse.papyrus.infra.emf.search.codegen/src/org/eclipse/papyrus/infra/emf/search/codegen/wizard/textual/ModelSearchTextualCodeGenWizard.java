/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchTextualCodeGenWizard.java
 * 
 * Contributors: 
 * 			Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.wizard.textual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.core.ModelSearchCoreGenerator;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.ui.ModelSearchUIGenerator;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.EStringAccessor;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.GeneratorFactory;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;
import org.eclipse.papyrus.infra.emf.search.genmodel.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.wizard.Wizard;

public final class ModelSearchTextualCodeGenWizard extends Wizard {
	
	private GenModel genModel;
	private ModelSearchTextualCodeGenEStringElementsWizardPage estringFeatureSelectionWizardPage;
	
	public ModelSearchTextualCodeGenWizard(GenModel gMdl) {
		genModel = gMdl;
		estringFeatureSelectionWizardPage = new ModelSearchTextualCodeGenEStringElementsWizardPage(genModel);
		setHelpAvailable(false);
		setDefaultPageImageDescriptor(
                ModelSearchImagesUtil.getImageDescriptor(
                        Activator.getDefault().getBundle(),
                        "/icons/wizban/gensearch_wiz.gif" //$NON-NLS-1$
                )
		);
	}
	
	@Override
	public boolean performFinish() {
		generateCoreAndUISearchInfrastructure(
			estringFeatureSelectionWizardPage.getETypeLeavesSelectionAsList()
		);
		return true;
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
	
	private void generateCoreAndUISearchInfrastructure(final Collection<GenTypedElement> eStringTypeElements) {
		new Job("Generate " + genModel.getModelName() + " Search Core and UI") { //$NON-NLS-1$  //$NON-NLS-2$
			@Override
			public IStatus run(IProgressMonitor monitor) {
				
				ModelSearchGenSettings modelSearchGenSettings = GeneratorFactory.eINSTANCE.createModelSearchGenSettings();

				modelSearchGenSettings.setTextualSettings(GeneratorFactory.eINSTANCE.createTextualSettings());
				
				modelSearchGenSettings.getTextualSettings().getEStringAccessors().addAll(buildEStringAccessors(eStringTypeElements));
				modelSearchGenSettings.setGenModel(genModel);
				
				try {
					TextualGenEnum generationKind = estringFeatureSelectionWizardPage.getGenerationKind();
					if (TextualGenEnum.CORE.equals(generationKind) ||
							TextualGenEnum.CORE_AND_UI.equals(generationKind) ) {
						new ModelSearchCoreGenerator(modelSearchGenSettings).generate(BasicMonitor.toMonitor(monitor));
					}
					if (TextualGenEnum.UI.equals(generationKind) ||
							TextualGenEnum.CORE_AND_UI.equals(generationKind) ) {
						new ModelSearchUIGenerator(modelSearchGenSettings).generate(BasicMonitor.toMonitor(monitor));
					}
				} catch (JETException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				} catch (CoreException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		}.schedule();
	}
	
	@Override
	public boolean canFinish() {
		return estringFeatureSelectionWizardPage.selectionIsOnePlusETypedArtifact()
			&& estringFeatureSelectionWizardPage.getGenerationKind() != TextualGenEnum.NONE;
	}
	
	@Override
	public void addPages() {
		addPage(estringFeatureSelectionWizardPage);
	}
	
	public GenModel getGenModel() {
		return genModel;
	}
}
