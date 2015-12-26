/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreTextualModelSearchQueryBuilderHelper.java
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

public class EcoreTextualModelSearchQueryBuilderHelper extends AbstractEcoreTextualModelSearchQueryBuilderHelper {
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}

	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, "");
	}

	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, "");
	}

//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, "");
//	}
//
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, "");
//	}

	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(
			String pattern, IModelSearchScope<Object, Resource> scope) {
		return buildGlobalCaseSensitiveModelSearchQuery(pattern, scope, "");
	}
}