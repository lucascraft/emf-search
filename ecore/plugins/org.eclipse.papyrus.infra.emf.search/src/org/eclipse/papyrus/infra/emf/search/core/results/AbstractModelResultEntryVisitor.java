/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelResultEntryVisitor.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add EMF Resource target specific convenience methods
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Describes general mechanisms involved in model result hierarchy trees visiting process.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @param <E> model result specialization to be considered
 * 
 */
public abstract class AbstractModelResultEntryVisitor<E extends IModelResultEntry> implements
		IModelResultEntryVisitor<E> {
	/**
	 * @return E collected element results collection, void collection otherwise 
	 */
	private Collection<E> results;
	
	/**
	 * Constructor
	 */
	public AbstractModelResultEntryVisitor() {
		results = new ArrayList<E>();
	}
	
	/**
	 * Clears previously collected results 
	 */
	public void clearResults() {
		results.clear();
	}
	
	/**
	 * @return collected results, void collection otherwise
	 */
	public Collection<E> getResults() {
		return results;
	}
	
	/**
	 * Visits result hierarchy and collect each valid result
	 * 
	 * @return true when complete
	 */
	public boolean visit(E entry) {
		if (isValid(entry)) {
			results.add(entry);
		}
		return true;
	}
	
	/**
	 * Check whether an E result entry is valid and may be collected
	 * 
	 * @param entry an E result entry
	 * 
	 * @return true if E entry is valid and may be collected
	 */
	protected boolean isValid(E entry) {
		return entry.wasMatchedAtleastOnce();
	}
}
