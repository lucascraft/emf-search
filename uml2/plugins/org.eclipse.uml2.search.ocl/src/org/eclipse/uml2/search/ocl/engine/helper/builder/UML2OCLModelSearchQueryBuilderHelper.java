/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2TextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.ocl.engine.helper.builder;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.scope.IModelSearchScope;

public class UML2OCLModelSearchQueryBuilderHelper extends AbstractUML2OCLModelSearchQueryBuilderHelper {
	
	private static UML2OCLModelSearchQueryBuilderHelper instance;
	
	// Singleton
	public static UML2OCLModelSearchQueryBuilderHelper getInstance() {
		return instance==null?instance = new UML2OCLModelSearchQueryBuilderHelper():instance;
	}

	public IModelSearchQuery buildOCLModelSearchQuery(String expr,
			EClassifier ctx, IModelSearchScope<Object, Resource> scope) {
		return createQuery(expr, createParameters(scope, ctx));
	}
}
