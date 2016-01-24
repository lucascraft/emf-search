/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchResult.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - Add result hierarchies retrieval for search/replace
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.graphics.Image;

/**
 * Describes APIs contracts for model search results manipulation
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public interface IModelSearchResult extends ISearchResult {
	/**
	 * Clean result entries
	 */
	void clean();
	
	/**
	 * Creates & inserts a {@link IModelResultEntry} entry
	 * 
	 * @param file resource file actually containing meta elements
	 * @param entry a result entry to insert in a corresponding sub tree
	 * @param notify true if insertion has to be notified, false otherwise
	 * 
	 * @return newly created/inserted {@link IModelResultEntry} entry
	 */
	IModelResultEntry insert(Object file, IModelResultEntry entry, boolean notify);
	
	/**
	 * Creates & inserts a {@link IModelResultEntry} entry
	 * 
	 * @param file resource file actually containing meta elements
	 * @param entry a result entry into which to insert in a corresponding sub tree
	 * @param compoundEntry a result compound entry into which to insert in a corresponding sub tree
	 * @param notify true if insertion has to be notified, false otherwise
	 * 
	 * @return newly created/inserted {@link IModelResultEntry} entry
	 */
	IModelResultEntry insert(Object file, ICompositeModelResultEntry compoundEntry, IModelResultEntry entry, boolean notify);
	
	/**
	 * Creates & inserts a {@link IModelResultEntry} entry
	 * 
	 * @param file resource file actually containing meta elements
	 * @param entry a result entry into which to insert in a corresponding sub tree
	 * @param occurence a result match occurence entry into which to insert in a corresponding sub tree
	 * @param notify true if insertion has to be notified, false otherwise
	 * 
	 * @return newly created/inserted {@link IModelResultEntry} entry
	 */
	IModelResultEntry insert(Object file, IModelResultEntry entry, Object occurence, String text, boolean notify);
	
	/**
	 * Getter for results in a flattened form 
	 * 
	 * @return a collection of all {@link IModelResultEntry} entries results
	 */
	Collection<IModelResultEntry> getResultsFlatenned();
	
	/**
	 * Getter for results in a flattened form for given file
	 * 
	 * @param file given resource file containing corresponding {@link IModelResultEntry} entries
	 * 
	 * @return a collection of all {@link IModelResultEntry} entries results
	 */
	Collection<IModelResultEntry> getResultsFlatennedForFile(Object file);
	
	/**
	 * Getter for results meta element form as list
	 * 
	 * @return results meta element form as list
	 */
	List<Object> getResultsObjectsAsList();
	
	/**
	 * Getter for results meta element form as list for a given file
	 * 
	 * @param file given resource file containing corresponding {@link IModelResultEntry} entries

	 * @return results meta element form as list for a given file
	 */
	List<Object> getResultsObjectsForFileAsList(Object file);
	
	/**
	 * Getter for number of  match occurrences in a given results sub trees collection
	 * 
	 * @param eCollection a given results sub trees collection
	 * 
	 * @return number of  match occurrences in a given results sub trees collection
	 */
	int getMatches(Collection<Object> eCollection);
	
	/**
	 * Getter for number of  match occurrences in a given model file
	 * 
	 * @param file a given model file
	 * 
	 * @return number of  match occurrences in a given file
	 */
	int getMatchesNumberForFile(Object file);
	
	/**
	 * Getter for total number of  match occurrences in current {@link IModelSearchResult}
	 * @return total number of  match occurrences in current {@link IModelSearchResult}
	 */
	int getTotalMatches();
	
	/**
	 * Getter providing a map of all top {@link IModelResultEntry} hierarchical trees
	 * from the first level (root).
	 * 
	 * @return a map of all top {@link IModelResultEntry} hierarchical trees 
	 */
	Map <Object, Collection<Object>> getRootResultHierarchies();
	
	/**
	 * Image representing the current {@link IModelResultEntry} entry
	 * 
	 * @return the image representing the current {@link IModelResultEntry} entry
	 */
	Image getImage();
}
