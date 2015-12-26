/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
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


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;

public class FilteredEcoreQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public FilteredEcoreQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
	}
	
	public void testCreateWorkspaceModelFilteredTextualSearchQuery() {
		query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery("*", EcorePackage.Literals.ECLASS, scope, EcorePackage.eNS_URI);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelFilteredTextualSearchQuery(String regex, int nbMatch, EClassifier participant) {
		query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(regex, participant, scope, EcorePackage.eNS_URI);
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

	public void testRunWorkspaceModelFilteredTextualSearchQuery1() {
		_testRunWorkspaceModelFilteredTextualSearchQuery("*", 59, EcorePackage.Literals.ECLASS);
	}
	
	public void testRunWorkspaceModelFilteredTextualSearchQuery2() {
		_testRunWorkspaceModelFilteredTextualSearchQuery("DocumentRoot", 5, EcorePackage.Literals.ECLASS);
	}
}
