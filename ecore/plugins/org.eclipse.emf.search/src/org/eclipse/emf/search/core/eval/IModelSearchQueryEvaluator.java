/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryEvaluator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.core.eval;

import java.util.List;

/**
 * Entity responsible of a <Q> query evaluation against a given <T> target
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 */
public interface IModelSearchQueryEvaluator<Q, T> {
	/**
	 * <p>
	 * Basically runs a user defined query on the given target (resource ?)
	 * </p>
	 * <p>
	 * Users wanting to evaluate such queries must implement this interface
	 * </p>
	 * 
	 * @param query User defined <Q> query to be evaluated
	 * @param target A given (resource ?) <T> target to evaluate the query onto
	 * @param notify true if matches have to be notified to search result page UI, false otherwise
	 * 
	 * @return A possibly null or void list, some useful elements otherwise
	 */
	List<?> eval(Q query, T target, boolean notify);
	
	/**
	 * Users wanting display a custom query name for the current evaluator in 
	 * the search result page should provide a meaningful query description here.
	 * 
	 * @return The query kind description
	 */
	String getLabel();
}
