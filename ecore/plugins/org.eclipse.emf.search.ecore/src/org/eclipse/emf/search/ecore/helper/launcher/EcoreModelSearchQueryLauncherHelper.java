/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchQueryLauncherHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - API fixing for multiple packages handling
 ******************************************************************************/

package org.eclipse.emf.search.ecore.helper.launcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;

/**
 * Ecore Model Search query helper.
 * 
 * Entity aiming to ease Ecore Model Search queries by exposing minimal settings APIs to users. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreModelSearchQueryLauncherHelper extends EcoreTextualModelSearchQueryBuilderHelper implements ICaseSensitiveModelSearchQueryLauncherHelper, IRegexModelSearchQueryLauncherHelper, ITextualModelSearchQueryLauncherHelper {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// WORKSPACE & RELATED SCOPES
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// NORMAL TEXT
	//
	public IModelSearchResult launchGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, EcorePackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}
	
	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, List<EObject> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}
	
	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, EObject participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EObject> participants = new ArrayList<EObject>();
		participants.add(participant);
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}

	//
	// REGEX
	//
	public IModelSearchResult launchGlobalRegexModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, EcorePackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}
	
	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern,
			List<EObject> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern,
			EObject participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EObject> participants = new ArrayList<EObject>();
		participants.add(participant);		
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}

	//
	// CASE SENSITIVE
	//
	public IModelSearchResult launchGlobalCaseSensitiveModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, EcorePackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(
			String pattern, EObject participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EObject> participants = new ArrayList<EObject>();
		participants.add(participant);
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EObject> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQuery q = buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, EcorePackage.eNS_URI);
		q.run(new NullProgressMonitor());
		return q.getModelSearchResult();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FILE SYSTEM & DIRECTORY SCOPES
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//
	// NORMAL TEXT
	//
	public IModelSearchResult launchGlobalTextualModelSearchQuery(String pattern, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchGlobalTextualModelSearchQuery(pattern, rootDirectoryScope, nsURI);
	}
	
	public IModelSearchResult launchGlobalTextualModelSearchQuery(String pattern) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchGlobalTextualModelSearchQuery(pattern, rootDirectoryScope, EcorePackage.eNS_URI);
	}
	
	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, List<EObject> participants, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredTextualModelSearchQuery(pattern, participants, rootDirectoryScope, nsURI);
	}
	
	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, List<EObject> participants) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredTextualModelSearchQuery(pattern, participants, rootDirectoryScope, EcorePackage.eNS_URI);
	}
	
	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, EObject participant, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredTextualModelSearchQuery(pattern, participant, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchFilteredTextualModelSearchQuery(String pattern, EObject participant) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredTextualModelSearchQuery(pattern, participant, rootDirectoryScope, EcorePackage.eNS_URI);
	}

	//
	// REGEX
	//
	public IModelSearchResult launchGlobalRegexModelSearchQuery(String pattern, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchGlobalRegexModelSearchQuery(pattern, rootDirectoryScope, nsURI);
	}
	
	public IModelSearchResult launchGlobalRegexModelSearchQuery(String pattern) {
		return launchGlobalRegexModelSearchQuery(pattern, EcorePackage.eNS_URI);
	}
	
	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern, List<EObject> participants, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredRegexModelSearchQuery(pattern, participants, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern, List<EObject> participants) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredRegexModelSearchQuery(pattern, participants, rootDirectoryScope, EcorePackage.eNS_URI);
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern, EObject participant, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredRegexModelSearchQuery(pattern, participant, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(String pattern, EObject participant) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredRegexModelSearchQuery(pattern, participant, rootDirectoryScope, EcorePackage.eNS_URI);
	}

	//
	// CASE SENSITIVE
	//
	public IModelSearchResult launchGlobalCaseSensitiveModelSearchQuery(String pattern, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchGlobalCaseSensitiveModelSearchQuery(pattern, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchGlobalCaseSensitiveModelSearchQuery(String pattern) {
		return launchGlobalCaseSensitiveModelSearchQuery(pattern, EcorePackage.eNS_URI);
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(String pattern, List<EObject> participants, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participants, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(String pattern, List<EObject> participants) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participants, rootDirectoryScope, EcorePackage.eNS_URI);
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(String pattern, EObject participant, String nsURI) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participant, rootDirectoryScope, nsURI);
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(String pattern, EObject participant) {
		IModelSearchScope<Object, Resource> rootDirectoryScope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemScope(EcoreTextualEngine.ID);
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participant, rootDirectoryScope, EcorePackage.eNS_URI);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// EOBJECT & SELECTION RELATED SCOPES
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//
	// NORMAL TEXT
	//
//	public IModelSearchResult launchGlobalTextualModelSearchEObjectQuery(String pattern, EObject eObj, String nsURI) {
//		return launchGlobalTextualModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), nsURI);
//	}
//	
//	public IModelSearchResult launchGlobalTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, String nsURI) {
//		IModelSearchQuery query = buildGlobalTextualModelSearchEObjectQuery(pattern, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//	
//	public IModelSearchResult launchGlobalTextualModelSearchEObjectQuery(String pattern, EObject eObj) {
//		return launchGlobalTextualModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//	}
//	
//	public IModelSearchResult launchGlobalTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj) {
//		return launchGlobalTextualModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//	}
//	
//	
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants, String nsURI) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), participants, nsURI);
//	}
//	
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants, String nsURI) {
//		IModelSearchQuery query = buildFilteredTextualModelSearchEObjectQuery(pattern, participants, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//	
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), participants, EcorePackage.eNS_URI);
//	}
//	
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, eObj, participants, EcorePackage.eNS_URI);
//	}
//	
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant, String nsURI) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant, String nsURI) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), nsURI);
//	}
//
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredTextualModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant) {
//		return launchFilteredTextualModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	//
//	// REGEX
//	//
//	public IModelSearchResult launchGlobalRegexModelSearchEObjectQuery(String pattern, EObject eObj, String nsURI) {
//		return launchGlobalRegexModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), nsURI);
//	}
//	
//	public IModelSearchResult launchGlobalRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj, String nsURI) {
//		IModelSearchQuery query = buildGlobalRegexModelSearchEObjectQuery(pattern, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//	
//	public IModelSearchResult launchGlobalRegexModelSearchEObjectQuery(String pattern, EObject eObj) {
//		return launchGlobalRegexModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//	}
//	
//	public IModelSearchResult launchGlobalRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj) {
//		return launchGlobalRegexModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//	}
//	
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants, String nsURI) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), participants, nsURI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants, String nsURI) {
//		IModelSearchQuery query = buildFilteredRegexModelSearchEObjectQuery(pattern, participants, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), participants, EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, eObj, participants, EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant, String nsURI) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), nsURI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant, String nsURI) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), nsURI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredRegexModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant) {
//		return launchFilteredRegexModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	//
//	// CASE SENSITIVE
//	//
//	public IModelSearchResult launchGlobalCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj, String nsURI) {
//		IModelSearchQuery query = buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchGlobalCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj, String nsURI) {
//		IModelSearchQuery query = buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchGlobalCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj) {
//		IModelSearchQuery query = buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchGlobalCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj) {
//		IModelSearchQuery query = buildGlobalCaseSensitiveModelSearchEObjectQuery(pattern, eObj, EcorePackage.eNS_URI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants, String nsURI) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), participants, EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants, String nsURI) {
//		IModelSearchQuery query = buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, nsURI);
//		query.run();
//		return query.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj, List<EClassifier> participants) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, eObj, participants, EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj, List<EClassifier> participants) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, eObj, participants, EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant, String nsURI) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), nsURI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant, String nsURI) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), nsURI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, EObject eObj, EClassifier participant) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, Arrays.asList(new EObject[] { eObj }), Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}
//
//	public IModelSearchResult launchFilteredCaseSensitiveModelSearchEObjectQuery(String pattern, List<EObject> eObj, EClassifier participant) {
//		return launchFilteredCaseSensitiveModelSearchEObjectQuery(pattern, eObj, Arrays.asList(new EClassifier[] { participant }), EcorePackage.eNS_URI);
//	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EObject> participants,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, "");
	}

	public IModelSearchResult launchFilteredCaseSensitiveModelSearchQuery(
			String pattern, EObject participant,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchResult launchGlobalCaseSensitiveModelSearchQuery(
			String pattern, IModelSearchScope<Object, Resource> scope) {
		return launchGlobalCaseSensitiveModelSearchQuery(pattern, scope, "");
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(
			String pattern, List<EObject> participants,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredRegexModelSearchQuery(pattern, participants, scope, "");
	}

	public IModelSearchResult launchFilteredRegexModelSearchQuery(
			String pattern, EObject participant,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredRegexModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchResult launchGlobalRegexModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope) {
		return launchGlobalRegexModelSearchQuery(pattern, scope, "");
	}

	public IModelSearchResult launchFilteredTextualModelSearchQuery(
			String pattern, List<EObject> participants,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredTextualModelSearchQuery(pattern, participants, scope, "");
	}

	public IModelSearchResult launchFilteredTextualModelSearchQuery(
			String pattern, EObject participant,
			IModelSearchScope<Object, Resource> scope) {
		return launchFilteredTextualModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchResult launchGlobalTextualModelSearchQuery(
			String pattern, IModelSearchScope<Object, Resource> scope) {
		return launchGlobalTextualModelSearchQuery(pattern, scope, "");
	}

}
