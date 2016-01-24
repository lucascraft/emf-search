/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IWorkspaceModelSearchScopeFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.scope;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.IWorkingSet;

public interface IWorkspaceModelSearchScopeFactory {
	IModelSearchScope<Object, Resource> createModelSearchProjectScope(String engineID, String[] projectNames);
	IModelSearchScope<Object, Resource> createModelSearchScope(String engineID, IWorkingSet[] workingSets);
	IModelSearchScope<Object, Resource> createModelSearchScope(String engineID, Object selection);
	IModelSearchScope<Object, Resource> createModelSearchWorkspaceScope(String engineID);
}
