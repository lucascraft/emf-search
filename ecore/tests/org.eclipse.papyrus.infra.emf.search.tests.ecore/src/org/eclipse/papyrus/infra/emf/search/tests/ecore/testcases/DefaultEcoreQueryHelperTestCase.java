/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * DefaultEcoreQueryHelperTestCase.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases;


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;

public class DefaultEcoreQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public DefaultEcoreQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
	}
	
	public void testCreateWorkspaceModelSimpleTextualSearchQuery() {
		query = new EcoreTextualModelSearchQueryBuilderHelper().buildGlobalTextualModelSearchQuery("*", scope, EcorePackage.eNS_URI);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelSimpleTextualSearchQuery(String regex, int nbMatch) {
		query = new EcoreTextualModelSearchQueryBuilderHelper().buildGlobalTextualModelSearchQuery(regex, scope);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.run(new NullProgressMonitor());
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
		
		Assert.assertTrue("Search result must be instance of ModelSearchResult", result instanceof IModelSearchResult);
		
		if (result instanceof IModelSearchResult) {
			Collection<IModelResultEntry> reultEntries = ((IModelSearchResult)result).getResultsFlatenned();
			
			Assert.assertTrue("the '" + regex + "' query should have not return void result collection", reultEntries.size() > 0);
			
			Assert.assertTrue("the '" + regex + "' query should have returned " + nbMatch + " result" + (nbMatch==1?"":"s") + " instead of " + reultEntries.size(), reultEntries.size() == nbMatch);
		}
	}

	public void testRunWorkspaceModelSimpleTextualSearchQuery1() {
		_testRunWorkspaceModelSimpleTextualSearchQuery("*", 710);
	}
	
	public void testRunWorkspaceModelSimpleTextualSearchQuery2() {
		_testRunWorkspaceModelSimpleTextualSearchQuery("DocumentRoot", 5);
	}
}
