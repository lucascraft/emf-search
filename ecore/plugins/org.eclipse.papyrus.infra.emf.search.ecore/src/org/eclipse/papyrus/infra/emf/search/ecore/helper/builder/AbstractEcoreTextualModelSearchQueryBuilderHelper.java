/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractEcoreTextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - API fixing for multiple packages handling
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.helper.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.ecore.evaluators.EcoreTextualModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.factories.EcoreModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.factories.EcoreModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;

/**
 * Ecore textual query builder helper.
 * 
 * It basically ease creation of model search queries by exposing minimal arguments APIs to user.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractEcoreTextualModelSearchQueryBuilderHelper 
	implements 
			ITextualModelSearchQueryBuilderHelper, 
			IRegexModelSearchQueryBuilderHelper, 
			ICaseSensitiveModelSearchQueryBuilderHelper {

	/**
	 * Creates model search query parameters from user settings such as search scope
	 * @param scope model search scope against which a query will be evaluated
	 * @param participants model search meta elements participants
	 * @param textualExpression textual expression, as {@link ModelSearchQueryTextualExpressionEnum} enum 
	 * (CASE_SENSITIVE, REGULAR_EXPRESSION,	NORMAL_TEXT)
	 * 
	 * @return model search query parameters coming from user settings, default parameters otherwise
	 */
	protected IModelSearchQueryParameters createParameters(
			IModelSearchScope<Object, Resource> scope,
			List<? extends Object> participants,
			ModelSearchQueryTextualExpressionEnum textualExpression) {
		
		IModelSearchQueryParameters parameters = EcoreModelSearchQueryParametersFactory.getInstance().createSearchQueryParameters();

		parameters.setEvaluator(new EcoreTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());
		parameters.setParticipantElements(participants);          
		parameters.setScope(scope);
		
		initTextualQueryParametersFromPatternKind(parameters, textualExpression);

		return parameters;
	}
	
	/**
	 * Creates a model search query from an expression and its corresponding parameters
	 *  
	 * @param pattern a textual pattern
	 * @param parameters model search query parameters coming from user settings
	 * 
	 * @return a newly created model search query a dummy default one otherwise 
	 */
	protected IModelSearchQuery createQuery(
			String pattern,
			IModelSearchQueryParameters parameters) {		
		return EcoreModelSearchQueryFactory.getInstance().createModelSearchQuery(pattern, parameters);
	}
	
	/**
	 * Basically initialize parameters expression evaluation setting from its enumaration representation coming from 
	 * {@link ModelSearchQueryTextualExpressionEnum} enum (CASE_SENSITIVE, REGULAR_EXPRESSION,	NORMAL_TEXT)
	 * 
	 * @param parameters model search query parameters coming from user settings
	 * @param textualExpression a textual expression
	 */
	protected void initTextualQueryParametersFromPatternKind(IModelSearchQueryParameters parameters, ModelSearchQueryTextualExpressionEnum textualExpression){
		switch (textualExpression) {
			case REGULAR_EXPRESSION:
				parameters.setData(EcoreModelSearchQuery.CASE_SENSITIVE_SEARCH, Boolean.FALSE);
			    parameters.setData(EcoreModelSearchQuery.REGEX_SEARCH, Boolean.TRUE);
			    break;
			case CASE_SENSITIVE:
				parameters.setData(EcoreModelSearchQuery.CASE_SENSITIVE_SEARCH, Boolean.TRUE);
			    parameters.setData(EcoreModelSearchQuery.REGEX_SEARCH, Boolean.FALSE);
			    break;
			case NORMAL_TEXT:
			default:
				parameters.setData(EcoreModelSearchQuery.CASE_SENSITIVE_SEARCH, Boolean.FALSE);
				parameters.setData(EcoreModelSearchQuery.REGEX_SEARCH, Boolean.FALSE);
				break;
		}
	}
	
	/**
	 * Builds a an Ecore model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param textualExpression expression evaluation kind as ModelSearchQueryTextualExpressionEnum enum literal
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created model search query 
	 */
	protected IModelSearchQuery buildTextualModelSearchQuery(String pattern, List<? extends Object> participants, ModelSearchQueryTextualExpressionEnum textualExpression, IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQueryParameters parameters = createParameters(scope, participants, textualExpression);
		return createQuery(pattern, parameters);
	}
	
	// Implementations
	
	/**
	 * Builds a NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participant model search meta element participant
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created common model search query 
	 */
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, List<EClassifier> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, nsURI);
	}
	
	/**
	 * Builds a NORMAL Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created NORMAL model search query 
	 */
	public IModelSearchQuery buildFilteredTextualModelSearchQuery(String pattern, EClassifier participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EClassifier> participants = new ArrayList<EClassifier>();
		participants.add(participant);
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, nsURI);
	}

	/**
	 * Builds a REGEX Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participant model search meta element participant
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created REGEX model search query 
	 */
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			List<EClassifier> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, nsURI);
	}

	/**
	 * Builds a REGEX Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created REGEX model search query 
	 */
	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
	        EClassifier participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EClassifier> participants = new ArrayList<EClassifier>();
		participants.add(participant);		
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, nsURI);
	}

	/**
	 * Builds a CASE SENSITIVE Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participant model search meta element participant
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created CASE SENSITIVE model search query 
	 */
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, EClassifier participant, IModelSearchScope<Object, Resource> scope, String nsURI) {
		List<EClassifier> participants = new ArrayList<EClassifier>();
		participants.add(participant);
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, nsURI);
	}

	/**
	 * Builds a CASE SENSITIVE Ecore textual model search query
	 * 
	 * @param pattern textual pattern to be evaluated 
	 * @param participants model search meta element participants
	 * @param scope model search query scope
	 * @param nsURI package URI the meta elements participants belong
	 * 
	 * @return newly created CASE SENSITIVE model search query 
	 */
	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EClassifier> participants, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, participants, ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, nsURI);
	}

	//
	// EObject scope
	//

//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					Arrays.asList(new EObject[] { obj })
//			);
//		return buildFilteredTextualModelSearchQuery(pattern, participant, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(String pattern, 
//	        List<EClassifier> participants, EObject obj, String nsURI) {
//        IModelSearchScope<Object, Resource> scope = 
//            ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//                    Arrays.asList(new EObject[] { obj })
//            );
//        return buildFilteredTextualModelSearchQuery(pattern, participants, scope, nsURI);
//	}
//
//    public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					Arrays.asList(new EObject[] { obj })
//			);
//		return buildGlobalTextualModelSearchQuery(pattern, scope, nsURI);
//	}

	public IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQueryParameters parameters = createParameters(
				scope, 
				EcorePackage.eINSTANCE.getEClassifiers(),
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT
		);
		return createQuery(pattern, parameters);
	}


//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				obj
//		);
//		return buildFilteredRegexModelSearchQuery(pattern, participant, scope, nsURI);
//	}
//
//
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					obj
//			);
//		return buildFilteredTextualModelSearchQuery(pattern, participants, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					obj
//			);
//		return buildGlobalTextualModelSearchQuery(pattern, scope, nsURI);
//	}
//
//	
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					Arrays.asList(new EObject[] { obj })
//			);
//		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					obj
//			);
//		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject obj,
//			String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					Arrays.asList(new EObject[] { obj })
//			);
//		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					obj
//			);
//		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					Arrays.asList(new EObject[] { obj })
//			);
//		return buildGlobalCaseSensitiveModelSearchQuery(pattern, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            		EcoreTextualEngine.ID,
//					obj
//			);
//		return buildGlobalCaseSensitiveModelSearchQuery(pattern, scope, nsURI);
//	}

	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(
			String pattern, IModelSearchScope<Object, Resource> scope,
			String nsURI) {
		IModelSearchQueryParameters parameters = createParameters(
				scope,
				EcorePackage.eINSTANCE.getEClassifiers(),
				ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE
		);
		return createQuery(pattern, parameters);
	}

//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(String pattern,
//	        EClassifier participant, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				Arrays.asList(new EObject[] { obj })
//		);
//		return buildFilteredRegexModelSearchQuery(pattern, participant, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(String pattern,
//			List<EClassifier> participants, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				Arrays.asList(new EObject[] { obj })
//		);
//		return buildFilteredRegexModelSearchQuery(pattern, participants, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, EObject obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				Arrays.asList(new EObject[] { obj })
//		);
//		return buildGlobalRegexModelSearchQuery(pattern, scope, nsURI);
//	}
//
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, List<EObject> obj, String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				obj
//		);
//		return buildGlobalRegexModelSearchQuery(pattern, scope, nsURI);
//	}

	public IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope, String nsURI) {
		IModelSearchQueryParameters parameters = createParameters(
				scope, 
				EcorePackage.eINSTANCE.getEClassifiers(), 
				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION
		);
		return createQuery(pattern, parameters);
	}

//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> obj,
//			String nsURI) {
//		IModelSearchScope<Object, Resource> scope = 
//			ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(
//            	EcoreTextualEngine.ID,
//				obj
//		);
//		IModelSearchQueryParameters parameters = createParameters(
//				scope, 
//				participants,
//				ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION
//		);
//		return createQuery(pattern, parameters);
//	}
	
	
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildFilteredTextualModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> eObj) {
//		return buildFilteredTextualModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildFilteredTextualModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredTextualModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildFilteredTextualModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}

	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredTextualModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchQuery buildFilteredTextualModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredTextualModelSearchQuery(pattern, participants, scope, "");
	}

//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildGlobalTextualModelSearchEObjectQuery(pattern, eObj, "");
//	}
//
//	public IModelSearchQuery buildGlobalTextualModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildGlobalTextualModelSearchEObjectQuery(pattern, eObj, "");
//	}

	public IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope) {
		return buildGlobalTextualModelSearchQuery(pattern, scope, "");
	}

//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, EClassifier participant, List<EObject> eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildFilteredCaseSensitiveModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}

	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, EClassifier participant,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchQuery buildFilteredCaseSensitiveModelSearchQuery(
			String pattern, List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, "");
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

//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, EClassifier participant, EObject eObj) {
//		return buildFilteredRegexModelSearchEObjectQuery(pattern, participant, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, EObject eObj) {
//		return buildFilteredRegexModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}
//
//	public IModelSearchQuery buildFilteredRegexModelSearchEObjectQuery(
//			String pattern, List<EClassifier> participants, List<EObject> eObj) {
//		return buildFilteredRegexModelSearchEObjectQuery(pattern, participants, eObj, "");
//	}

	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			EClassifier participant, IModelSearchScope<Object, Resource> scope) {
		return buildFilteredRegexModelSearchQuery(pattern, participant, scope, "");
	}

	public IModelSearchQuery buildFilteredRegexModelSearchQuery(String pattern,
			List<EClassifier> participants,
			IModelSearchScope<Object, Resource> scope) {
		return buildFilteredRegexModelSearchQuery(pattern, participants, scope, "");
	}

//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, EObject eObj) {
//		return buildGlobalRegexModelSearchEObjectQuery(pattern, eObj, "");
//	}
//
//	public IModelSearchQuery buildGlobalRegexModelSearchEObjectQuery(
//			String pattern, List<EObject> eObj) {
//		return buildGlobalRegexModelSearchEObjectQuery(pattern, eObj, "");
//	}

	public IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern,
			IModelSearchScope<Object, Resource> scope) {
		return buildGlobalRegexModelSearchQuery(pattern, scope, "");
	}
}
