/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2TextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.helper.builder;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;

public class GenModelTextualModelSearchQueryBuilderHelper extends AbstractGenModelTextualModelSearchQueryBuilderHelper {
	
	private static GenModelTextualModelSearchQueryBuilderHelper instance;
	
	// Singleton
	public static GenModelTextualModelSearchQueryBuilderHelper getInstance() {
		return instance==null?instance = new GenModelTextualModelSearchQueryBuilderHelper():instance;
	}
	
	//
	// NORMAL
	//
	public IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, GenModelPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, GenModelPackage.eNS_URI);
	}
	
	//
	// REGEX
	//
	public IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, GenModelPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, GenModelPackage.eNS_URI);
	}
	
	//
	// CASE SENSITIVE
	//
	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, GenModelPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, GenModelPackage.eNS_URI);
	}

//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject obj, String nsUri) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, obj);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				GenModelPackage.eNS_URI
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj, GenModelPackage.eNS_URI);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				GenModelPackage.eNS_URI
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				GenModelPackage.eNS_URI
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				GenModelPackage.eNS_URI
//		);
//	}

	@Override
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope, String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				GenModelPackage.eNS_URI
		);
	}

	@Override
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				GenModelPackage.eNS_URI
		);
	}

	@Override
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope, String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				nsUri
		);
	}

	@Override
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				GenModelPackage.eNS_URI
		);
	}

//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}

	@Override
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			EClassifier participant, IModelSearchScope<Object, Resource> scope,
			String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
				scope,
				nsUri
		);
	}

	@Override
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			EClassifier participant, IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
				scope,
				""
		);
	}

	@Override
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope, String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
				scope,
				nsUri
		);
	}

	@Override
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
				scope,
				""
		);
	}

//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				Arrays.asList(new EClassifier[]{ participant }),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				participants,
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}

	@Override
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope, String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
				scope,
				nsUri
		);
	}

	@Override
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				Arrays.asList(new EClassifier[]{ participant }),
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
				scope,
				""
		);
	}

	@Override
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope, String nsUri) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
				scope,
				nsUri
		);
	}

	@Override
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				participants,
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
				scope,
				""
		);
	}

//	@Override
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}

	@Override
	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(
			String pattern, IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				GenModelPackage.eINSTANCE.getEClassifiers(),
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				""
		);
	}

//	@Override
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}

	@Override
	public IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				GenModelPackage.eINSTANCE.getEClassifiers(),
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
				scope,
				""
		);
	}

//	@Override
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsUri) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, obj),
//				nsUri
//		);
//	}
//
//	@Override
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildTextualModelSearchQuery(
//				pattern,
//				GenModelPackage.eINSTANCE.getEClassifiers(),
//				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
//				ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(GenModelTextualEngine.ID, eObj),
//				""
//		);
//	}

	@Override
	public IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope) {
		return buildTextualModelSearchQuery(
				pattern,
				GenModelPackage.eINSTANCE.getEClassifiers(),
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
				scope,
				""
		);
	}
	
}
