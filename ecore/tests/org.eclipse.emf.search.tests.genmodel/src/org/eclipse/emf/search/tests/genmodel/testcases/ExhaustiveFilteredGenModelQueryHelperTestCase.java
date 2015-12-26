/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ExhaustiveFilteredGenModelQueryHelperTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.tests.genmodel.testcases;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.genmodel.engine.GenModelTextualEngine;
import org.eclipse.emf.search.genmodel.helper.builder.GenModelTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;

public class ExhaustiveFilteredGenModelQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public ExhaustiveFilteredGenModelQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(GenModelTextualEngine.ID);
	}
	
	
	private void _testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery(String regex, EClassifier participant) {
		query = GenModelTextualModelSearchQueryBuilderHelper.getInstance().buildFilteredTextualModelSearchQuery(regex, participant, scope, EcorePackage.eNS_URI);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.run(new NullProgressMonitor());
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
	}

	public void testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery() {
		for (EClassifier metaElement : GenModelPackage.eINSTANCE.getEClassifiers()) {
			_testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery("*", metaElement);
		}
	}
}
