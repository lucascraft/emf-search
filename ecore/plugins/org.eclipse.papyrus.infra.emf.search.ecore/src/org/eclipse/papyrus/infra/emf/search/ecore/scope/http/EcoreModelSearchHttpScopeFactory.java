/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchHttpScopeFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.scope.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.scope.IHttpModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.scope.ModelSearchScope;

/**
 * Utility for http search scope creation. It allows user to create scope that will
 * be able to collect resources on file system for given http URLs.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class EcoreModelSearchHttpScopeFactory implements IHttpModelSearchScopeFactory {
	
	// shared ModelSearchScopeFactory instance
	private static EcoreModelSearchHttpScopeFactory instance;
	
	/**
	 * Singleton access to the ModelSearchDirectoryScopeFactory instance.
	 * 
	 * @return New ModelSearchDirectoryScopeFactory instance or previously created one
	 */
	public static EcoreModelSearchHttpScopeFactory getInstance() {
		return instance==null?instance=new EcoreModelSearchHttpScopeFactory() : instance;
	}
	
	/*
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given resource.
	 */
	private IModelSearchScope<Object, Resource> collectModelSearchScopeParticipants(URL[] urls, EcoreModelSearchScopeHttpVisitor visitor) {
		for (URL url : urls) {
			accept(url, visitor);
		}
		return visitor.getModelSearchScope();
	}
	
	private void accept(URL url, EcoreModelSearchScopeHttpVisitor visitor) {
		if (url != null) {
//			if (url.isDirectory()) {
//				for (File n : node.listFiles()) {
//					accept(n, visitor);
//				}
//			} else if (node.isFile()) {
				visitor.visit(url);
//			}
		}
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * workspace root.
	 * 
	 * @param params Current search query parameters
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchHttpScope(String engineID, String url) {
		return createModelSearchHttpScope(engineID, new String[]{ url });
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * workspace root.
	 * 
	 * @param params Current search query parameters
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchHttpScope(String engineID, String[] urls) {
		List<URL> urlList = new ArrayList<URL>();
		for (String u : urls) {
			try {
				urlList.add(new URL(u));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		return collectModelSearchScopeParticipants(urlList.toArray(new URL[0]), getModelSearchHttpVisitor(new ModelSearchScope<Object, Resource>("Global Http URL Scope"), engineID)); //$NON-NLS-1$
	}
	
	protected EcoreModelSearchScopeHttpVisitor getModelSearchHttpVisitor(IModelSearchScope<Object, Resource> scope, String engineID) {
		return new EcoreModelSearchScopeHttpVisitor(scope, engineID);
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given Project names array.
	 * 
	 * @param params Current search query parameters
	 * @param urls URLs array coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchEcoreModelSearchHttpScopeFactory(String engineID, URL[] urls) {
		IModelSearchScope<Object, Resource> scope = new ModelSearchScope<Object, Resource>("Http Scope"); //$NON-NLS-1$
		return scope = collectModelSearchScopeParticipants(urls, getModelSearchHttpVisitor(scope, engineID));
	}
}
