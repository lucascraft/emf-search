/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IEObjectModelSearchScopeFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.scope;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface IEObjectModelSearchScopeFactory {
	IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, Collection<EObject> models);
	IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, EObject model);
	IModelSearchScope<Object, Resource> createModelSearchEObjectScope(String engineID, EObject[] models);
}
