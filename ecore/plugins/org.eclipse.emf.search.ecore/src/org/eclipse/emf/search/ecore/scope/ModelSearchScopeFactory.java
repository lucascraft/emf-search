/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchScopeFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.scope;

import java.io.File;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.core.scope.IModelSearchScopeFactory;
import org.eclipse.emf.search.core.scope.SearchScopeKindEnum;
import org.eclipse.emf.search.ecore.scope.eobject.EcoreModelSearchEObjectScopeFactory;
import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchDirectoryScopeFactory;
import org.eclipse.emf.search.ecore.scope.http.EcoreModelSearchHttpScopeFactory;
import org.eclipse.emf.search.ecore.scope.workspace.ModelSearchWorkspaceScopeFactory;
import org.eclipse.ui.IWorkingSet;

/**
 * Utility for search scope creation. It allows user to create scope that will
 * be able to collect EMF resources for given parameters.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public final class ModelSearchScopeFactory implements IModelSearchScopeFactory {
	
    /**
     * Singleton access to the ModelSearchDirectoryScopeFactory instance.
     */
	// shared ModelSearchScopeFactory instance
	public static ModelSearchScopeFactory INSTANCE = new ModelSearchScopeFactory();
	
	public IModelSearchScope<Object, Resource>  createScope(SearchScopeKindEnum scopeKind, String engineID, Object... parameters) {
		IModelSearchScope<Object, Resource> searchScope = ModelSearchWorkspaceScopeFactory.getInstance().createModelSearchWorkspaceScope(engineID);
		switch(scopeKind) {
//			case EOBJECT: 
//				searchScope = EcoreModelSearchEObjectScopeFactory.INSTANCE.createModelSearchEObjectScope(engineID, (EObject[])parameters);
//				break;
			case HTTP: // Http URLs
			    searchScope = EcoreModelSearchHttpScopeFactory.getInstance().createModelSearchHttpScope(engineID, (String[]) parameters);
				break;
			case IPROJECT:
			    searchScope = ModelSearchWorkspaceScopeFactory.getInstance().createModelSearchProjectScope(engineID, (String[]) parameters);
				break;
			case IRESOURCE:
			    searchScope = ModelSearchWorkspaceScopeFactory.getInstance().createModelSearchScope(engineID, parameters);
				break;
			case IWORKINGSET:
			    searchScope = ModelSearchWorkspaceScopeFactory.getInstance().createModelSearchScope(engineID, parameters);
				break;
			case FILE_SYSTEM: // Directories only
			    searchScope = EcoreModelSearchDirectoryScopeFactory.getInstance().createModelSearchFileSystemDirectoryScope(engineID, (File[])parameters);
				break;
			case IWORKSPACE:
			default:
			    searchScope = ModelSearchWorkspaceScopeFactory.getInstance().createModelSearchWorkspaceScope(engineID);
				break;
		}
		return searchScope;
	}

//	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(
//			String engineId, Collection<EObject> models) {
//		return createScope(SearchScopeKindEnum.EOBJECT, engineId, models.toArray());
//	}
//
//	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(
//			String engineId, EObject model) {
//		return createScope(SearchScopeKindEnum.EOBJECT, engineId, new EObject[] { model });
//	}
//
//	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(
//			String engineId, EObject[] models) {
//		return createScope(SearchScopeKindEnum.EOBJECT, engineId, models);
//	}

	public IModelSearchScope<Object, Resource> createModelSearchFileSystemDirectoryScope(
			String engineId, File[] fsNodes) {
		return createScope(SearchScopeKindEnum.FILE_SYSTEM, engineId, fsNodes);
	}

	public IModelSearchScope<Object, Resource> createModelSearchFileSystemScope(
			String engineId) {
		return createScope(SearchScopeKindEnum.FILE_SYSTEM, engineId, File.listRoots());
	}

	public IModelSearchScope<Object, Resource> createModelSearchHttpScope(
			String engineId, String url) {
		return createScope(SearchScopeKindEnum.HTTP, engineId, new String[] { url });
	}

	public IModelSearchScope<Object, Resource> createModelSearchHttpScope(
			String engineId, String[] urls) {
		return createScope(SearchScopeKindEnum.HTTP, engineId, urls);
	}

	public IModelSearchScope<Object, Resource> createModelSearchProjectScope(
			String engineId, String[] projectNames) {
		return createScope(SearchScopeKindEnum.IPROJECT, engineId, projectNames);
	}

	public IModelSearchScope<Object, Resource> createModelSearchScope(
			String engineId, IWorkingSet[] workingSets) {
		return createScope(SearchScopeKindEnum.IWORKINGSET, engineId, workingSets);
	}

	public IModelSearchScope<Object, Resource> createModelSearchScope(
			String engineId, Object selection) {
		return createScope(SearchScopeKindEnum.SELECTION, engineId, selection);
	}

	public IModelSearchScope<Object, Resource> createModelSearchWorkspaceScope(
			String engineId) {
		return createScope(SearchScopeKindEnum.IWORKSPACE, engineId);
	}
}
