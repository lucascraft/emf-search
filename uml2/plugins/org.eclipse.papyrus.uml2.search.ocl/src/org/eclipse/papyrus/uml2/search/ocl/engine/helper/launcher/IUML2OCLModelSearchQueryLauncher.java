/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IUML2OCLModelSearchQueryLauncher.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ocl.engine.helper.launcher;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;

public interface IUML2OCLModelSearchQueryLauncher {
	IModelSearchResult launchOCLModelSearchQuery(String expr, EClassifier ctx, IModelSearchScope<Object, Resource> scope);
//	IModelSearchResult launchOCLModelSearchQuery(String expr, EClassifier ctx, EObject eObj);
//	IModelSearchResult launchOCLModelSearchQuery(String expr, EClassifier ctx, List<EObject> eObjLst);
	IModelSearchResult launchOCLModelSearchQuery(String expr, EClassifier ctx);

}
