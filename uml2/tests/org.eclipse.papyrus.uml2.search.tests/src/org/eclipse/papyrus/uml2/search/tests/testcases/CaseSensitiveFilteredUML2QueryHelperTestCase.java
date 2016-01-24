/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * CaseSensitiveFilteredUML2QueryHelperTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.tests.testcases;


import java.util.Collection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.papyrus.uml2.search.engine.UML2TextualEngine;
import org.eclipse.papyrus.uml2.search.helper.builder.UML2TextualModelSearchQueryBuilderHelper;
import org.eclipse.uml2.uml.UMLPackage;


public class CaseSensitiveFilteredUML2QueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public CaseSensitiveFilteredUML2QueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initUML2ModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(UML2TextualEngine.ID);
	}
	
	public void testCreateWorkspaceModelFilteredCaseSensitiveSearchQuery() {
		query = UML2TextualModelSearchQueryBuilderHelper.getInstance().buildFilteredCaseSensitiveModelSearchQuery("*", UMLPackage.Literals.CLASS, scope, UMLPackage.eNS_URI);
		Assert.assertNotNull("Newly created query object shouldn't be null", query);
	}
	
	private void _testRunWorkspaceModelFilteredCaseSensitiveSearchQuery(String pattern, int nbMatch, EClassifier participant) {
		query = UML2TextualModelSearchQueryBuilderHelper.getInstance().buildFilteredCaseSensitiveModelSearchQuery(pattern, participant, scope, UMLPackage.eNS_URI);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.runWithoutNotifications();
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
		
		Assert.assertTrue("Search result must be instance of ModelSearchResult", result instanceof IModelSearchResult);
		
		if (result instanceof IModelSearchResult) {
			Collection<IModelResultEntry> reultEntries = ((IModelSearchResult)result).getResultsFlatenned();
			
			Assert.assertTrue("the '" + pattern + "' query should have returned " + nbMatch + " result" + (nbMatch==1?"":"s") + " instead of " + reultEntries.size(), reultEntries.size() == nbMatch);
		}
	}

	public void testRunWorkspaceModelFilteredCaseSensitiveSearchQuery1() {
		_testRunWorkspaceModelFilteredCaseSensitiveSearchQuery("*", 48, UMLPackage.Literals.CLASS);
	}
	
	public void testRunWorkspaceModelFilteredCaseSensitiveSearchQuery2() {
		_testRunWorkspaceModelFilteredCaseSensitiveSearchQuery("Document*", 8, UMLPackage.Literals.CLASS);
	}
	public void testRunWorkspaceModelFilteredCaseSensitiveSearchQuery3() {
		_testRunWorkspaceModelFilteredCaseSensitiveSearchQuery("DOCUMENT*", 0, UMLPackage.Literals.CLASS);
	}
}
