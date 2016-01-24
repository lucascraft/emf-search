/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenerateSearchAction.java
 * 
 * Contributors: Lucas Bigeardel - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.popup.actions;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.infra.emf.search.codegen.wizard.textual.ModelSearchTextualCodeGenWizard;
import org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional.ModelSearchWizardDialog;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class GenerateSearchAction implements IObjectActionDelegate {
	private GenModel genModel = null;
	
	/**
	 * Constructor.
	 */
	public GenerateSearchAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		ModelSearchTextualCodeGenWizard wizard = new ModelSearchTextualCodeGenWizard(genModel);
		ModelSearchWizardDialog wizardDialog = new ModelSearchWizardDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				wizard
		);
		wizardDialog.create();
		wizardDialog.getShell().setSize(Math.max(500, wizardDialog.getShell().getSize().x), 500);
	    wizardDialog.getShell().setText("Textual Model Search Generation Wizard");

		wizardDialog.open();
	}
	
	/**
	 * Visit given file and add it if potential search participant.
	 */
	public boolean visit(File file) {
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
						genModel = (GenModel)o;
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
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
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.getFirstElement() instanceof IFile) {
			IFile file = (IFile)structuredSelection.getFirstElement();
			visit(new File(file.getLocationURI()));
		}
	}
}
