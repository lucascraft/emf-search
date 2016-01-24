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

package org.eclipse.papyrus.uml2.search.tests.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.papyrus.uml2.search.tests.testcases.CaseSensitiveFilteredUML2QueryHelperTestCase;
import org.eclipse.papyrus.uml2.search.tests.testcases.DefaultUML2QueryHelperTestCase;
import org.eclipse.papyrus.uml2.search.tests.testcases.ExhaustiveFilteredUML2QueryHelperTestCase;
import org.eclipse.papyrus.uml2.search.tests.testcases.FilteredUML2QueryHelperTestCase;
import org.eclipse.papyrus.uml2.search.tests.testcases.RegexFilteredUML2QueryHelperTestCase;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.papyrus.uml2.search.tests.testsuites");

//		suite.addTest(new DefaultUML2QueryHelperTestCase("testCreateWorkspaceModelSimpleTextualSearchQuery"));
//		suite.addTest(new DefaultUML2QueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery1"));
//		suite.addTest(new DefaultUML2QueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery2"));

		suite.addTest(new FilteredUML2QueryHelperTestCase("testCreateWorkspaceModelFilteredTextualSearchQuery"));
//		suite.addTest(new FilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery1"));
//		suite.addTest(new FilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery2"));
		
		suite.addTest(new RegexFilteredUML2QueryHelperTestCase("testCreateWorkspaceModelFilteredRegexSearchQuery"));
//		suite.addTest(new RegexFilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery1"));
//		suite.addTest(new RegexFilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery2"));
		
		suite.addTest(new CaseSensitiveFilteredUML2QueryHelperTestCase("testCreateWorkspaceModelFilteredCaseSensitiveSearchQuery"));
		suite.addTest(new CaseSensitiveFilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery1"));
//		suite.addTest(new CaseSensitiveFilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery2"));
//		suite.addTest(new CaseSensitiveFilteredUML2QueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery3"));
		
//		suite.addTest(new ExhaustiveFilteredUML2QueryHelperTestCase("testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery"));
		
		return suite;
	}

}
