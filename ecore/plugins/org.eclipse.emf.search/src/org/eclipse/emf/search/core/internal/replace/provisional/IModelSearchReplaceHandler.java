/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchReplaceHandler.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.internal.replace.provisional;

import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelResultEntry;

/**
 * <p>
 * API contract for entities responsible to handle Replace for meta elements
 * </p>
 * <p>
 * It practically consists in getting/setting names for "named" elements of a model
 * </p>
 * <p>
 * It also handles transformation operations applied to target element
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 */
public interface IModelSearchReplaceHandler{
	/**
	 * Getter for currently considered occurence label
	 * 
	 * @param entry a given {@link IModelResultEntry} entry to compute occurence label from
	 * 
	 * @return occurence label
	 */
	String getOccurenceLabel(IModelResultEntry entry);
	
	/**
	 * Handles textual replace for given meta element, matched from given query for given value
	 * 
	 * @param element currently considered meta element
	 * @param query currently considered model search query
	 * @param value value to get replacing value from
	 */
	void handleReplace(Object element, IModelSearchQuery query, Object value);
}
