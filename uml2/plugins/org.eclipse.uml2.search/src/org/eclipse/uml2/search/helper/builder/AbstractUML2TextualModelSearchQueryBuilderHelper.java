/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractUML2TextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.helper.builder;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.helper.builder.AbstractEcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.uml2.search.evaluators.UML2TextualModelSearchQueryEvaluator;
import org.eclipse.uml2.search.factories.UML2ModelSearchQueryFactory;
import org.eclipse.uml2.search.factories.UML2ModelSearchQueryParametersFactory;

/**
 * UML2 textual query builder helper.
 * 
 * It basically ease creation of UML2 model search queries by exposing minimal arguments APIs to user.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractUML2TextualModelSearchQueryBuilderHelper 
	extends AbstractEcoreTextualModelSearchQueryBuilderHelper {
	
	/**
	 * {@inheritDoc}
	 */
	protected IModelSearchQueryParameters createParameters(
			IModelSearchScope<Object, Resource> scope,
			List<? extends Object> participants,
			ModelSearchQueryTextualExpressionEnum textualExpression) {
		
		IModelSearchQueryParameters parameters = UML2ModelSearchQueryParametersFactory.getInstance().createSearchQueryParameters();

		parameters.setEvaluator(new UML2TextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());
		parameters.setParticipantElements(participants);          
		parameters.setScope(scope);
		
		initTextualQueryParametersFromPatternKind(parameters, textualExpression);

		return parameters;
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected IModelSearchQuery createQuery(
			String pattern,
			IModelSearchQueryParameters parameters) {		

		return UML2ModelSearchQueryFactory.getInstance().createModelSearchQuery(pattern, parameters);
	}
}
