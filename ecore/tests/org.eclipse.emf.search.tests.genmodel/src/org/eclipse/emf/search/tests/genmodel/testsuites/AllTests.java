/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AllTests.java
 *
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.tests.genmodel.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.emf.search.tests.genmodel.testcases.CaseSensitiveFilteredGenModelQueryHelperTestCase;
import org.eclipse.emf.search.tests.genmodel.testcases.DefaultGenModelQueryHelperTestCase;
import org.eclipse.emf.search.tests.genmodel.testcases.ExhaustiveFilteredGenModelQueryHelperTestCase;
import org.eclipse.emf.search.tests.genmodel.testcases.FilteredGenModelQueryHelperTestCase;
import org.eclipse.emf.search.tests.genmodel.testcases.RegexFilteredGenModelQueryHelperTestCase;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.emf.search.tests.genmodel.testsuites");

		suite.addTest(new DefaultGenModelQueryHelperTestCase("testCreateWorkspaceModelSimpleTextualSearchQuery"));
		suite.addTest(new DefaultGenModelQueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery1"));
		suite.addTest(new DefaultGenModelQueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery2"));

		suite.addTest(new FilteredGenModelQueryHelperTestCase("testCreateWorkspaceModelFilteredTextualSearchQuery"));
		suite.addTest(new FilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery1"));
		suite.addTest(new FilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery2"));
		
		suite.addTest(new RegexFilteredGenModelQueryHelperTestCase("testCreateWorkspaceModelFilteredRegexSearchQuery"));
		suite.addTest(new RegexFilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery1"));
		suite.addTest(new RegexFilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery2"));
		
		suite.addTest(new CaseSensitiveFilteredGenModelQueryHelperTestCase("testCreateWorkspaceModelFilteredCaseSensitiveSearchQuery"));
		suite.addTest(new CaseSensitiveFilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery1"));
		suite.addTest(new CaseSensitiveFilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery2"));
		suite.addTest(new CaseSensitiveFilteredGenModelQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery3"));
		
		suite.addTest(new ExhaustiveFilteredGenModelQueryHelperTestCase("testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery"));
		
		return suite;
	}

}
