/*******************************************************************************
 * Copyright (c) 2007, 2009 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * FilteredEcoreQueryHelperTestCase.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.tests.ecore.testcases;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.emf.search.tests.utils.ModelSearchResultUtils;

public class ExhaustiveFilteredEcoreQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public ExhaustiveFilteredEcoreQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
	}
	
	
	private void _testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery(String regex, EClassifier participant) {
		query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(regex, participant, scope, EcorePackage.eNS_URI);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.run(new NullProgressMonitor());
		
		IModelSearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
		
		ModelSearchResultUtils.dumpResultsAsTextualHierarchy(result);
	}

	public void testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery() {
		for (EClassifier metaElement : EcorePackage.eINSTANCE.getEClassifiers()) {
			_testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery("*", metaElement);
		}
	}
}
