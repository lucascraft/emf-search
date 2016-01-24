/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelResultEntryVisitor.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - improve visitor APIs
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import java.util.Collection;

/**
 * Defines visitor pattern API contract
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 *
 * @param <E> Result Entry to consider
 */
public interface IModelResultEntryVisitor<E> {
	
	/**
	 * Visitor pattern implementation
	 * 
	 * @param entry a resource entry to visit
	 * 
	 * @return true if visit sucessful, false otherwise§§
	 */
	boolean visit(E entry);
	
	/**
	 * Getter for &lt;E&gt; collected entries
	 * 
	 * @return collected &lt;E&gt; entries
	 */
	Collection<E> getResults();
}
