/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchDirectoryScopeFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - moved to specific package
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.scope.file;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.scope.ModelSearchScope;

/**
 * Utility for file system search scope creation. It allows user to create scope that
 * will be able to collect resources on file system for given directories.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class EcoreModelSearchDirectoryScopeFactory {
	
	// shared ModelSearchScopeFactory instance
	private static EcoreModelSearchDirectoryScopeFactory instance;
	
	/**
	 * Singleton access to the ModelSearchDirectoryScopeFactory instance.
	 * 
	 * @return New ModelSearchDirectoryScopeFactory instance or previously created one
	 */
	public static EcoreModelSearchDirectoryScopeFactory getInstance() {
		return instance==null?instance=new EcoreModelSearchDirectoryScopeFactory():instance;
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given resource.
	 */
	private IModelSearchScope<Object, Resource> collectModelSearchScopeParticipants(File[] nodes, EcoreModelSearchScopeFileSystemVisitor visitor) {
		for (File node : nodes) {
			accept(node, visitor);
		}
		return visitor.getModelSearchScope();
	}
	
	private void accept(File node, EcoreModelSearchScopeFileSystemVisitor visitor) {
		if (node instanceof File && node.exists() && node.canRead() && !node.isHidden()) {
			if (node.isDirectory()) {
				for (File n : node.listFiles()) {
					accept(n, visitor);
				}
			} else if (node.isFile()) {
				visitor.visit(node);
			}
		}
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * workspace root.
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchFileSystemScope(String engineID) {
		return collectModelSearchScopeParticipants(File.listRoots(), getModelSearchFileSystemVisitor(new ModelSearchScope<Object, Resource>("Global File System Scope"), engineID)); //$NON-NLS-1$
	}
	
	protected EcoreModelSearchScopeFileSystemVisitor getModelSearchFileSystemVisitor(IModelSearchScope<Object, Resource> scope, String engineID) {
		return new EcoreModelSearchScopeFileSystemVisitor(scope, engineID);
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given Project names array.
	 * 
	 * @param params Current search query parameters
	 * @param directoryNames Project names array coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchFileSystemDirectoryScope(String engineID, File[] fsNodes) {
		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>("Directory Scope"); //$NON-NLS-1$
		return scope = collectModelSearchScopeParticipants(fsNodes, getModelSearchFileSystemVisitor(scope, engineID));
	}
}
