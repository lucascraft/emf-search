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

package org.eclipse.emf.search.ecore.ocl.helper.launcher;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.ocl.engine.EcoreOCLEngine;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryFactory;
import org.eclipse.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryParametersFactory;
import org.eclipse.emf.search.ecore.ocl.helper.builder.EcoreOCLModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.ocl.engine.OCLModelSearchQueryEnum;

public class EcoreOCLModelSearchQueryLauncherHelper extends EcoreOCLModelSearchQueryBuilderHelper implements IEcoreOCLModelSearchQueryLauncher {
	
	private static EcoreOCLModelSearchQueryLauncherHelper instance;
	
	// Singleton
	public static EcoreOCLModelSearchQueryLauncherHelper getInstance() {
		return instance==null?instance = new EcoreOCLModelSearchQueryLauncherHelper():instance;
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

	public IModelSearchResult launchOCLModelSearchQuery(String expr,
			EClassifier ctx, IModelSearchScope<Object, Resource> scope) {
		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, ctx, scope);
		q.run();
		return q.getModelSearchResult();
	}

//	public IModelSearchResult launchOCLModelSearchQuery(String expr,
//			EClassifier ctx, EObject eObj) {
//		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, ctx, ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(EcoreOCLEngine.ID, eObj));
//		q.run();
//		return q.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchOCLModelSearchQuery(String expr,
//			EClassifier ctx, List<EObject> eObjLst) {
//		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, ctx, ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(EcoreOCLEngine.ID, eObjLst));
//		q.run();
//		return q.getModelSearchResult();
//	}

	public IModelSearchResult launchOCLModelSearchQuery(String expr,
			EClassifier ctx) {
		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(
						expr, 
						ctx, 
						ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreOCLEngine.ID)
				);
		q.run();
		return q.getModelSearchResult();
	}
}
