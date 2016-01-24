/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchQueryFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.factories;

import org.eclipse.papyrus.infra.emf.search.core.engine.AbstractModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.uml2.search.engine.UML2ModelSearchQuery;

/**
 * Wraps UML2ModelSearchQuery creation.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class UML2ModelSearchQueryFactory implements IModelSearchQueryFactory {
	private static UML2ModelSearchQueryFactory instance;
	public UML2ModelSearchQueryFactory() {}
	public static UML2ModelSearchQueryFactory getInstance() {
		return instance == null ? instance = new UML2ModelSearchQueryFactory() : instance;
	}
	public AbstractModelSearchQuery createModelSearchQuery(String expr, IModelSearchQueryParameters p) {
		return new UML2ModelSearchQuery(expr, p);
	}
}
