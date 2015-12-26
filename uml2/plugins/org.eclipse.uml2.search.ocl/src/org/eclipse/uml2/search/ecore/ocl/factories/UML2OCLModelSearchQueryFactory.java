/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLModelSearchQueryFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.ecore.ocl.factories;

import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.uml2.search.ecore.ocl.engine.UML2OCLModelSearchQuery;

public final class UML2OCLModelSearchQueryFactory implements IModelSearchQueryFactory {
	private static UML2OCLModelSearchQueryFactory instance;
	public UML2OCLModelSearchQueryFactory() {}
	public static UML2OCLModelSearchQueryFactory getInstance() {
		return instance == null ? instance = new UML2OCLModelSearchQueryFactory() : instance;
	}
	public IModelSearchQuery createModelSearchQuery(String expr,
			IModelSearchQueryParameters p) {
		return new UML2OCLModelSearchQuery(expr, p);
	}
}
