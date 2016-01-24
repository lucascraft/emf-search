/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ExhaustiveFilteredUML2QueryHelperTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.tests.testcases;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.papyrus.uml2.search.engine.UML2TextualEngine;
import org.eclipse.papyrus.uml2.search.helper.builder.UML2TextualModelSearchQueryBuilderHelper;
import org.eclipse.uml2.uml.UMLPackage;

public class ExhaustiveFilteredUML2QueryHelperTestCase extends TestCase {
	private IModelSearchQuery query;
	private IModelSearchScope<Object, Resource> scope;
	
	public ExhaustiveFilteredUML2QueryHelperTestCase(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initUML2ModelSearchTestProject();
		scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(UML2TextualEngine.ID);
	}
	
	
	private void _testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery(String regex, EClassifier participant) {
		query = UML2TextualModelSearchQueryBuilderHelper.getInstance().buildFilteredTextualModelSearchQuery(regex, participant, scope, UMLPackage.eNS_URI);
		Assert.assertNotNull("query object shouldn't be null", query);
		
		query.runWithoutNotifications();
		
		ISearchResult result = query.getModelSearchResult();
		
		Assert.assertNotNull("Search result shouldn't be null", result);
	}

	public void testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery() {
		for (EClassifier metaElement : UMLPackage.eINSTANCE.getEClassifiers()) {
			_testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery("*", metaElement);
		}
	}
}
