/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreOCLModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.ecore.ocl.helper.builder;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryFactory;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryParametersFactory;
import org.eclipse.emf.search.ocl.engine.OCLModelSearchQueryEnum;

public class EcoreOCLModelSearchQueryBuilderHelper extends AbstractEcoreOCLModelSearchQueryBuilderHelper {
	
	private static EcoreOCLModelSearchQueryBuilderHelper instance;
	
	// Singleton
	public static EcoreOCLModelSearchQueryBuilderHelper getInstance() {
		return instance==null?instance = new EcoreOCLModelSearchQueryBuilderHelper():instance;
	}

	public IModelSearchQuery buildOCLModelSearchQuery(
			String expr, EClassifier ctx,
			IModelSearchScope<Object, Resource> scope) {
		AbstractModelSearchQueryParameters parameters = EcoreOCLModelSearchQueryParametersFactory.getInstance().createSearchQueryParameters();
		parameters.setData(OCLModelSearchQueryEnum.OCL_MODEL_SEARCH_CONTEXT.name(), ctx);
		parameters.setParticipantElements(
				Arrays.asList(ctx)
		);
		parameters.setScope(scope);
		return EcoreOCLModelSearchQueryFactory.getInstance().createModelSearchQuery(expr, parameters);
	}
}
