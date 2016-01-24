/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchDirectoryScopeFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - introduced UML2 resource http visitor
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.util;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.http.EcoreModelSearchHttpScopeFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.http.EcoreModelSearchScopeHttpVisitor;


/**
 * Utility for HTTP search scope creation. It allows user to create UML2 scope that
 * will be able to collect resources on file system for given HTTP URI.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class UML2ModelSearchHttpScopeFactory extends
		EcoreModelSearchHttpScopeFactory {

	// shared ModelSearchScopeFactory instance
	private static UML2ModelSearchHttpScopeFactory instance;
	
	/**
	 * Singleton access to the ModelSearchDirectoryScopeFactory instance.
	 * 
	 * @return New UML2ModelSearchHttpScopeFactory instance or previously created one
	 */
	public static UML2ModelSearchHttpScopeFactory getInstance() {
		return instance==null?instance=new UML2ModelSearchHttpScopeFactory():instance;
	}

	@Override
	protected EcoreModelSearchScopeHttpVisitor getModelSearchHttpVisitor(
			IModelSearchScope<Object, Resource> scope, String engineID) {
		return new UML2ModelSearchScopeHttpVisitor(scope, engineID);
	}
}
