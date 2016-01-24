/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * RegexFilteredGenModelQueryHelperTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.genmodel.testcases;


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.genmodel.engine.GenModelTextualEngine;
import org.eclipse.papyrus.infra.emf.search.genmodel.helper.builder.GenModelTextualModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;

public class RegexFilteredGenModelQueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public RegexFilteredGenModelQueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(GenModelTextualEngine.ID);
	}
	
	public void testCreateWorkspaceModelFilteredRegexSearchQuery() {
		query = GenModelTextualModelSearchQueryBuilderHelper.getInstance().buildFilteredRegexModelSearchQuery(".*", GenModelPackage.Literals.GEN_CLASS, scope, EcorePackage.eNS_URI);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelFilteredRegexSearchQuery(String regex, int nbMatch, EClassifier participant) {
		query = GenModelTextualModelSearchQueryBuilderHelper.getInstance().buildFilteredRegexModelSearchQuery(regex, participant, scope, EcorePackage.eNS_URI);
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

	public void testRunWorkspaceModelFilteredRegexSearchQuery1() {
		_testRunWorkspaceModelFilteredRegexSearchQuery(".*", 56, GenModelPackage.Literals.GEN_CLASS);
	}
	
	public void testRunWorkspaceModelFilteredRegexSearchQuery2() {
		_testRunWorkspaceModelFilteredRegexSearchQuery("Document.*", 5, GenModelPackage.Literals.GEN_CLASS);
	}
}
