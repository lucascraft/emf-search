/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelResultEntry.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import java.util.Collection;
import java.util.Stack;

/**
 * Exposes the APIs contract of a model search result entry matched at least once by a model search query
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 *
 */
public interface IModelResultEntry {
	/**
	 * Convenience getter which adapts EMF Resource target coming from getTartget()
	 * into java.io.File whenever it is possible
	 * 
	 * @return an adapted java.io.File whenever it is possible, null otherwise. 
	 */
    Object getFile();
    
	/** 
	 * @return EObject source for current query search match occurence (eg. the match itself)
	 */
    Object getSource();
    
	/**
	 * @return EMF Resource Target of the current search query result
	 */
    Object getTarget();
    
    /**
     * @return IModelResultEntry parent, potentially match itself or intermediary structural object(s) for ordering purposes only
     */
    IModelResultEntry getParent();
    
    /**
     * Sets IModelResultEntry parent, potentially match itself or intermediary structural object(s) for ordering purposes only
     */
    void setParent(IModelResultEntry parent);
    
	/**
	 * @return true if a real match (eg. not an intermediary structural object for ordering purpose only), false otherwise
	 */
	boolean wasMatchedAtleastOnce();
	
	/**
	 * Set to true if a real match (eg. not an intermediary structural object for ordering purpose only), false otherwise
	 */
	void setMatchedOnce(boolean matchedOnce);
	
    /**
     *  @return IModelResultEntry children, potentially matches themselves or intermediary structural object(s) for ordering purposes only,
     *   void Collection otherwise
 	 */
    abstract Collection<Object> getResults();
    
 	/**
 	 * Adds IModelResultEntry child, potentially match itself or intermediary structural object(s) for ordering purposes only
 	 */
	abstract void addChildren(IModelResultEntry e);
	
 	/**
 	 * Removes IModelResultEntry child, potentially match itself or intermediary structural object(s) for ordering purposes only
 	 */
	abstract void removeChildren(IModelResultEntry e);
	
	/**
	 * @return IModelResultEntry hierarchy in a root to leaf order (eg. tree like), void Stack otherwise
	 */
	abstract Stack<IModelResultEntry> getHierarchyFromRootToLeaf();
	
	/**
	 * @return true if this entry subtree has been excluded from its containing result tree, false otherwise
	 */
	boolean isExcluded();
}
