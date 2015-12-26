/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITextualModelSearchQueryLauncherHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - API fixing for multiple packages handling
 ******************************************************************************/

package org.eclipse.emf.search.ecore.helper.launcher;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;

/**
 * Ecore NORMAL textual query launching helper APIs contract
 * 
 * It basically eases creation & launch of NORMAL model search queries by exposing minimal arguments APIs to user.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public interface ITextualModelSearchQueryLauncherHelper {
	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI);
	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, List<EObject> participants, IModelSearchScope<Object, Resource> scope, String nsURI);
	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, EObject participant, IModelSearchScope<Object, Resource> scope, String nsURI);







	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param scope model search query scope
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope);
	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, List<EObject> participants, IModelSearchScope<Object, Resource> scope);
	/**
	 * Builds & launches a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, EObject participant, IModelSearchScope<Object, Resource> scope);
}
