/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractUML2OCLModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.ocl.engine.helper.builder;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.ocl.evaluators.EcoreOCLInvariantModelSearchQueryEvaluator;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryFactory;
import org.eclipse.emf.search.ecore.ocl.helper.builder.IEcoreOCLModelSearchQueryHelper;
import org.eclipse.emf.search.ocl.engine.OCLModelSearchQueryEnum;
import org.eclipse.uml2.search.ecore.ocl.factories.UML2OCLModelSearchQueryParametersFactory;

public abstract class AbstractUML2OCLModelSearchQueryBuilderHelper implements IEcoreOCLModelSearchQueryHelper{
	
	protected IModelSearchQueryParameters createParameters(
			IModelSearchScope<Object, Resource> scope, EClassifier ctx) {
		
		IModelSearchQueryParameters parameters = 
			UML2OCLModelSearchQueryParametersFactory.
				getInstance().
					createSearchQueryParameters();

		parameters.setEvaluator(
				new EcoreOCLInvariantModelSearchQueryEvaluator<IModelSearchQuery, Object>()
		);
		parameters.setParticipantElements(
				Arrays.asList(ctx)
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
