/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * RegexFilteredUML2QueryHelperTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.tests.testcases;


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.uml2.search.engine.UML2TextualEngine;
import org.eclipse.uml2.search.helper.builder.UML2TextualModelSearchQueryBuilderHelper;
import org.eclipse.uml2.uml.UMLPackage;

public class RegexFilteredUML2QueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public RegexFilteredUML2QueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initUML2ModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(UML2TextualEngine.ID);
	}
	
	public void testCreateWorkspaceModelFilteredRegexSearchQuery() {
		query = UML2TextualModelSearchQueryBuilderHelper.getInstance().buildFilteredRegexModelSearchQuery(".*", UMLPackage.Literals.CLASS, scope, UMLPackage.eNS_URI);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelFilteredRegexSearchQuery(String regex, int nbMatch, EClassifier participant) {
		query = UML2TextualModelSearchQueryBuilderHelper.getInstance().buildFilteredRegexModelSearchQuery(regex, participant, scope, UMLPackage.eNS_URI);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.runWithoutNotifications();
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
		
		Assert.assertTrue("Search result must be instance of ModelSearchResult", result instanceof IModelSearchResult);
		
		if (result instanceof IModelSearchResult) {
			Collection<IModelResultEntry> reultEntries = ((IModelSearchResult)result).getResultsFlatenned();
			
			Assert.assertTrue("the '" + regex + "' query should have not return void result collection", reultEntries.size() > 0);
			
			Assert.assertTrue("the '" + regex + "' query should have returned " + nbMatch + " result" + (nbMatch==1?"":"s") + " instead of " + reultEntries.size(), reultEntries.size() == nbMatch);
		}
	}

	public void testRunWorkspaceModelFilteredRegexSearchQuery1() {
		_testRunWorkspaceModelFilteredRegexSearchQuery(".*", 80, UMLPackage.Literals.CLASS);
	}
	
	public void testRunWorkspaceModelFilteredRegexSearchQuery2() {
		_testRunWorkspaceModelFilteredRegexSearchQuery("Document.*", 8, UMLPackage.Literals.CLASS);
	}
}
