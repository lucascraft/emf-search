/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - API fixing for multiple packages handling
 ******************************************************************************/

package org.eclipse.emf.search.ecore.helper.builder;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.scope.IModelSearchScope;

/**
 * Ecore NORMAL textual query builder helper APIs contract
 * 
 * It basically eases creation of NORMAL model search queries by exposing minimal arguments APIs to user.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public interface ITextualModelSearchQueryBuilderHelper {
	/**
	 * Builds a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI);
	/**
	 * Builds a filtered NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, List<EClassifier> participants, IModelSearchScope<Object, Resource> scope, String nsURI);
	/**
	 * Builds a filtered NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, EClassifier participant, IModelSearchScope<Object, Resource> scope, String nsURI);
//	/**
//	 * Builds a global NORMAL Ecore textual model search T query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(String pattern, EObject eObj, String nsURI);
//	/**
//	 * Builds a global NORMAL Ecore textual model search T query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, String nsURI);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, List<EClassifier> participants, EObject eObj, String nsURI);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, List<EClassifier> participants, List<EObject> eObj, String nsURI);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, EClassifier participant, EObject eObj, String nsURI);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, EClassifier participant, List<EObject> eObj, String nsURI);

















	/**
	 * Builds a global NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope);
	/**
	 * Builds a filtered NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, List<EClassifier> participants, IModelSearchScope<Object, Resource> scope);
	/**
	 * Builds a filtered NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, EClassifier participant, IModelSearchScope<Object, Resource> scope);
//	/**
//	 * Builds a global NORMAL Ecore textual model search T query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(String pattern, EObject eObj);
//	/**
//	 * Builds a global NORMAL Ecore textual model search T query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, List<EClassifier> participants, EObject eObj);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, List<EClassifier> participants, List<EObject> eObj);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, EClassifier participant, EObject eObj);
//	/**
//	 * Builds a filtered NORMAL Ecore textual model search query
//	 * 
//	 * @param pattern textual pattern to be evaluated 
//	 * @param participants model search meta element participants
//	 * @param eObj model search query eObj "scope"
//	 * @param nsURI package URI the meta elements participants belong
//	 * 
//	 * @return newly created NORMAL model search query 
//	 */
//	IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, EClassifier participant, List<EObject> eObj);
}
