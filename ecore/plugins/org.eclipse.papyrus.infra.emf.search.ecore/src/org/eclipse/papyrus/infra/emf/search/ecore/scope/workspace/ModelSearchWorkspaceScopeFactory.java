/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchWorkspaceScopeFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.scope.workspace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.scope.ModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.l10n.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkingSet;

/**
 * Defines Model Search Scope lifecycle for Eclipse IDE workspace context.
 * This model search scope only deals at a IResource level.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchWorkspaceScopeFactory {
	
	// shared ModelSearchScopeFactory instance
	static ModelSearchWorkspaceScopeFactory instance;
	
	/**
	 * Singleton access to the ModelSearchScopeFactory instance.
	 * 
	 * @return New ModelSearchScopeFactory instance or previously created one
	 */
	public static ModelSearchWorkspaceScopeFactory getInstance() {
		return instance==null?instance=new ModelSearchWorkspaceScopeFactory():instance;
	}
	
	/*
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given resource.
	 */
	private IModelSearchScope<Object, Resource> collectModelSearchScopeParticipants(IResource resource, ModelSearchScopeResourceVisitor visitor) {
		try {
			resource.accept(visitor, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("ModelSearchScopeFactory.ModelSearchScopeVisitorErrorMessage"), e)); //$NON-NLS-1$
		}
		return visitor.getModelSearchScope();
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * workspace root.
	 * 
	 * @param params Current search query parameters
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchWorkspaceScope(String engineID) {
		String workspaceScopeLabel = Messages.getString("AbstractModelSearchPage.workspaceScopeMessage"); //$NON-NLS-1$

		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>(workspaceScopeLabel);
			
		return collectModelSearchScopeParticipants(ResourcesPlugin.getWorkspace().getRoot(), new ModelSearchScopeResourceVisitor(scope, engineID));
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given workspace selection.
	 * 
	 * @param params Current search query parameters
	 * @param selection IStructuredSelection coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchScope(String engineID, Object selection) {
		String selectionScopeLabel = Messages.getString("AbstractModelSearchPage.selectionScopeMessage"); //$NON-NLS-1$

		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>(selectionScopeLabel);
		ModelSearchScopeResourceVisitor visitor = new ModelSearchScopeResourceVisitor(scope, engineID);
		if (selection instanceof IStructuredSelection) {
			for (Object o : ((IStructuredSelection)selection).toList()) {
				if (o instanceof IAdaptable) {
					Object resource = ((IAdaptable)o).getAdapter(IResource.class);
					if (resource instanceof IResource)  {
						scope = collectModelSearchScopeParticipants((IResource)resource, visitor);
					}
				}
			}
		}
		return scope;
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given Project names array.
	 * 
	 * @param params Current search query parameters
	 * @param projectNames Project names array coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchProjectScope(String engineID, String[] projectNames) {
		String projectsScopeLabel = Messages.getString("AbstractModelSearchPage.projectsScopeMessage"); //$NON-NLS-1$

		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>(projectsScopeLabel);
		ModelSearchScopeResourceVisitor visitor = new ModelSearchScopeResourceVisitor(scope, engineID);
		
		for (String projectName : projectNames) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project != null && project.isAccessible()) {
				scope = collectModelSearchScopeParticipants(project, visitor);
			}
		}
		return scope;
	}

	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given {@link IWorkingSet} array.
	 * 
	 * @param params Current search query parameters
	 * @param workingSets {@link IWorkingSet} array coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchScope(String engineID, IWorkingSet[] workingSets) {
		String workingsetScopeLabel = Messages.getString("AbstractModelSearchPage.workingsetScopeMessage"); //$NON-NLS-1$

		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>(workingsetScopeLabel);
		ModelSearchScopeResourceVisitor visitor = new ModelSearchScopeResourceVisitor(scope, engineID);
		
		for (IWorkingSet workingSet : workingSets) {
			for (IAdaptable adaptable : workingSet.getElements()) {
				IProject project = (IProject) adaptable.getAdapter(IProject.class);
				if (project != null && project.isAccessible()) {
					scope = collectModelSearchScopeParticipants(project, visitor);
				}
			}
		}
		return scope;
	}
}
