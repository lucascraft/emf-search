/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchDirectoryScopeFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - UML2 resources http visitor
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.util;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchDirectoryScopeFactory;
import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchScopeFileSystemVisitor;

/**
 * Utility for file system search scope creation. It allows user to create UML2 scope that
 * will be able to collect resources on file system for given directories.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class UML2ModelSearchDirectoryScopeFactory extends
		EcoreModelSearchDirectoryScopeFactory {

	// shared ModelSearchScopeFactory instance
	private static UML2ModelSearchDirectoryScopeFactory instance;
	
	/**
	 * Singleton access to the ModelSearchDirectoryScopeFactory instance.
	 * 
	 * @return New ModelSearchDirectoryScopeFactory instance or previously created one
	 */
	public static UML2ModelSearchDirectoryScopeFactory getInstance() {
		return instance==null?instance=new UML2ModelSearchDirectoryScopeFactory():instance;
	}

	@Override
	protected EcoreModelSearchScopeFileSystemVisitor getModelSearchFileSystemVisitor(
			IModelSearchScope<Object, Resource> scope, String engineID) {
		return new UML2ModelSearchScopeFileSystemeVisitor(scope, engineID);
	}
}
