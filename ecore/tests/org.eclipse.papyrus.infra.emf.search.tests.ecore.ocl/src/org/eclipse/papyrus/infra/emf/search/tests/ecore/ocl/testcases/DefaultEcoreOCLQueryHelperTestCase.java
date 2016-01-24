/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * DefaultEcoreOCLQueryHelperTestCase.java
 * 
 * Contributors:
 * 			Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.ecore.ocl.testcases;


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.helper.builder.EcoreOCLModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;

public class DefaultEcoreOCLQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public DefaultEcoreOCLQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
	}
	
	public void testCreateGlobalOCLSearchEObjectQueries() {
		query = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery("name <> ''", EcorePackage.Literals.ECLASS, scope);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelSimpleTextualSearchQuery(String expr, int nbMatch) {
		query = EcoreOCLModelSearchQueryBuilderHelper.getInstance().buildOCLModelSearchQuery(expr, EcorePackage.Literals.ECLASS, scope);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.run();
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
		
		Assert.assertTrue("Search result must be instance of ModelSearchResult", result instanceof IModelSearchResult);
		
		if (result instanceof IModelSearchResult) {
			Collection<IModelResultEntry> reultEntries = ((IModelSearchResult)result).getResultsFlatenned();
			
			Assert.assertTrue("the '" + expr + "' query should have not return void result collection", reultEntries.size() > 0);
			
			Assert.assertTrue("the '" + expr + "' query should have returned " + nbMatch + " result" + (nbMatch==1?"":"s") + " instead of " + reultEntries.size(), reultEntries.size() == nbMatch);
		}
	}

	public void testRunWorkspaceModelSimpleOCLSearchQuery1() {
		_testRunWorkspaceModelSimpleTextualSearchQuery("name <> ''", 59);
	}
	
	public void testRunWorkspaceModelSimpleOCLSearchQuery2() {
		_testRunWorkspaceModelSimpleTextualSearchQuery("name = 'DocumentRoot'", 5);
	}
}
