/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchQueryFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.factories;

import org.eclipse.papyrus.infra.emf.search.core.engine.AbstractModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.genmodel.engine.GenModelSearchQuery;

public class GenModelSearchQueryFactory implements IModelSearchQueryFactory {
	private static GenModelSearchQueryFactory instance;
	public GenModelSearchQueryFactory() {}
	public static GenModelSearchQueryFactory getInstance() {
		return instance == null ? instance = new GenModelSearchQueryFactory() : instance;
	}
	public AbstractModelSearchQuery createModelSearchQuery(String expr, IModelSearchQueryParameters p) {
		return new GenModelSearchQuery(expr, p);
	}

}
