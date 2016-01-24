/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModeOCLlSearchQueryFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ocl.factories;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.engine.EcoreOCLModelSearchQuery;

public class EcoreOCLModelSearchQueryFactory implements IModelSearchQueryFactory {
	private static EcoreOCLModelSearchQueryFactory instance;
	public EcoreOCLModelSearchQueryFactory() {}
	public static EcoreOCLModelSearchQueryFactory getInstance() {
		return instance == null ? instance = new EcoreOCLModelSearchQueryFactory() : instance;
	}
	public IModelSearchQuery createModelSearchQuery(String expr,
			IModelSearchQueryParameters p) {
		return new EcoreOCLModelSearchQuery(expr, p);
	}
}
