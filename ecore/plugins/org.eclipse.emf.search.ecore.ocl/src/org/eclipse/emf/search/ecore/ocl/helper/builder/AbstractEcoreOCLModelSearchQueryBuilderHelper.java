/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractEcoreOCLModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.ecore.ocl.helper.builder;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.ocl.evaluators.EcoreOCLInvariantModelSearchQueryEvaluator;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryFactory;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryParametersFactory;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.emf.search.ocl.engine.OCLModelSearchQueryEnum;

public abstract class AbstractEcoreOCLModelSearchQueryBuilderHelper implements IEcoreOCLModelSearchQueryHelper {
	
	protected IModelSearchQueryParameters createParameters(
			IModelSearchScope<Object, Resource> scope,
			List<? extends Object> participants,
			EClassifier ctx,
			ModelSearchQueryTextualExpressionEnum textualExpression) {
		
		IModelSearchQueryParameters parameters = 
			EcoreOCLModelSearchQueryParametersFactory.
				getInstance().
					createSearchQueryParameters();

		parameters.setEvaluator(
				new EcoreOCLInvariantModelSearchQueryEvaluator<IModelSearchQuery, Object>()
		);
		parameters.setParticipantElements(
				participants
		);  
		parameters.setData(
				OCLModelSearchQueryEnum.OCL_MODEL_SEARCH_CONTEXT.name(), 
				ctx
		);
		parameters.setScope(
				scope
		);
		
		return parameters;
	}
	
	protected IModelSearchQuery createQuery(String expr, IModelSearchQueryParameters parameters) {		
		return EcoreOCLModelSearchQueryFactory.getInstance().createModelSearchQuery(expr, parameters);
	}
}
