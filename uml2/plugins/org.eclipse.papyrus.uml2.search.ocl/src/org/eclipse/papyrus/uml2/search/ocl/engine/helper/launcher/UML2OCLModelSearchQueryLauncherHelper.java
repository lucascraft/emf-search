/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLModelSearchQueryLauncherHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ocl.engine.helper.launcher;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.engine.EcoreOCLEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.factories.EcoreOCLModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.helper.builder.EcoreOCLModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.ocl.engine.OCLModelSearchQueryEnum;
import org.eclipse.papyrus.uml2.search.ecore.ocl.engine.UML2OCLEngine;

public class UML2OCLModelSearchQueryLauncherHelper extends EcoreOCLModelSearchQueryBuilderHelper implements IUML2OCLModelSearchQueryLauncher {
	
	private static UML2OCLModelSearchQueryLauncherHelper instance;
	
	// Singleton
	public static UML2OCLModelSearchQueryLauncherHelper getInstance() {
		return instance==null?instance = new UML2OCLModelSearchQueryLauncherHelper():instance;
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
//		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, ctx, ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(UML2OCLEngine.ID, eObj));
//		q.run();
//		return q.getModelSearchResult();
//	}
//
//	public IModelSearchResult launchOCLModelSearchQuery(String expr,
//			EClassifier ctx, List<EObject> eObjLst) {
//		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, ctx, ModelSearchScopeFactory.INSTANCE.createModelSearchEObjectScope(UML2OCLEngine.ID, eObjLst));
//		q.run();
//		return q.getModelSearchResult();
//	}

	public IModelSearchResult launchOCLModelSearchQuery(String expr,
			EClassifier ctx) {
		IModelSearchQuery q = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(
						expr, 
						ctx, 
						ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(UML2OCLEngine.ID)
				);
		q.run();
		return q.getModelSearchResult();
	}
}
