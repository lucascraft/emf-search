/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchSelectionScopeFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.scope.eobject;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.scope.IEObjectModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.scope.ModelSearchScope;

/**
 * Utility for Ecore search scope creation. It allows user to create scope that will
 * be able to collect resources on file system for given http URLs.  
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public class EcoreModelSearchEObjectScopeFactory implements IEObjectModelSearchScopeFactory{
	
    /**
     * Singleton access to the ModelSearchDirectoryScopeFactory instance.
     */
	// shared ModelSearchScopeFactory instance
	public static EcoreModelSearchEObjectScopeFactory INSTANCE = new EcoreModelSearchEObjectScopeFactory();
	
	/*
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given resource.
	 */
	private IModelSearchScope<Object, Resource> collectModelSearchScopeParticipants(Collection<EObject> models, EcoreModelSearchEObjectScopeVisitor visitor) {
		for (EObject model : models) {
			accept(model , visitor);
		}
		return visitor.getModelSearchScope();
	}
	
	private void accept(EObject model, EcoreModelSearchEObjectScopeVisitor visitor) {
		if (model != null) {
			visitor.visit(model);
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
	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, EObject[] models) {
		return collectModelSearchScopeParticipants(Arrays.asList(models), getModelSearchEcoreVisitor(new ModelSearchScope<Object, Resource>("Global Ecore Search Scope"), engineID)); //$NON-NLS-1$
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * workspace root.
	 * 
	 * @param params Current search query parameters
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, Collection<EObject> models) {
		return collectModelSearchScopeParticipants(models, getModelSearchEcoreVisitor(new ModelSearchScope<Object, Resource>("Global Ecore Search Scope"), engineID)); //$NON-NLS-1$
	}
	
	protected EcoreModelSearchEObjectScopeVisitor getModelSearchEcoreVisitor(IModelSearchScope<Object, Resource> scope, String engineID) {
		return new EcoreModelSearchEObjectScopeVisitor(scope, engineID);
	}
	
	/**
	 * Populates {@link ModelSearchScope} with potential resource participants for the 
	 * given Project names array.
	 * 
	 * @param params Current search query parameters
	 * @param model Ecore object coming from current search scope selection
	 * 
	 * @return Populated {@link ModelSearchScope} if any potential participant matching
	 */
	public IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, EObject model) {
		return createModelSearchEObjectScope(engineID, Arrays.asList(model));
	}
}
