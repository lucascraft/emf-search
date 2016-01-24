/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ICompositeModelResultEntry.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;

/**
 * API contract for compound result.
 * 
 * A compound result is basically an additional structure level which aggregates model 
 * result hierarchies for organizational purposes
 *    
 * @author lucas
 *
 */
public interface ICompositeModelResultEntry extends IModelResultEntry {
	/**
	 * Getter for children model result entries
	 *  
	 * @return a Collection of children model result entries
	 */
	Collection<IModelResultEntry> getChildren();
	
	/**
	 * Adds a model result entry to current children collection
	 * 
	 * @param entry a model result entry to be added to current children collection
	 */
	void addChild(IModelResultEntry entry);
	
	/**
	 * Adds a model result entry collection to current children collection
	 * 
	 * @param entries a model result entry collection to be added to current children collection
	 */
	void addChildren(Collection<IModelResultEntry> entries);

	/**
	 * Removes a model result entry from current children collection
	 * 
	 * @param entry a model result entry to remove from current children collection
	 */
	void removeChild(IModelResultEntry entry);
	
	/**
	 * Removes a model result entry collection from current children collection
	 * 
	 * @param entries a model result entry collection to be removed from current children collection
	 */
	void removeChildren(Collection<IModelResultEntry> entries);
	
	/**
	 * Getter for label
	 */
	String getLabel();
	
	/**
	 * Getter for image
	 */
	Image getImage();
}
