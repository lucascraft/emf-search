/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchQueryFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.factories;

import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;

/**
 * Users wanting to create EcoreModelSearchQuery instances should 
 * implement this factory.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreModelSearchQueryFactory implements IModelSearchQueryFactory {
	private static EcoreModelSearchQueryFactory instance;
	public EcoreModelSearchQueryFactory() {}
	public static EcoreModelSearchQueryFactory getInstance() {
		return instance == null ? instance = new EcoreModelSearchQueryFactory() : instance;
	}
	public IModelSearchQuery createModelSearchQuery(String expr,
			IModelSearchQueryParameters p) {
		return new EcoreModelSearchQuery(expr, p);
	}
}
