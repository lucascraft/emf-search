/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.factories;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;


/**
 * Users wanting to create IModelSearchQuery instances should 
 * implement this interface
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 */
public interface IModelSearchQueryFactory {
	/**
	 * Creates a new Model Search Query from given parameters
	 * 
	 * @param expr Query expression to be handled by Model Search Query Evaluators
	 * @param p Model Search Query parameters
	 * 
	 * @return newly created IModelSearchQuery instance.
	 */
	IModelSearchQuery createModelSearchQuery(String expr, IModelSearchQueryParameters p);
}
