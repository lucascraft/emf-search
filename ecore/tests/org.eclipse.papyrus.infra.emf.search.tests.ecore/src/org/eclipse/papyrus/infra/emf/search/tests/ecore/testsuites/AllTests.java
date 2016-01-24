/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AllTests.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.ecore.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.papyrus.infra.emf.search.tests.ecore.ocl.testcases.DefaultEcoreOCLQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.CaseSensitiveFilteredEcoreQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.DefaultEcoreQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.DefaultFilteredEcoreOnEObjectQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.ExhaustiveFilteredEcoreQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.FilteredEcoreQueryHelperTestCase;
import org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases.RegexFilteredEcoreQueryHelperTestCase;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.papyrus.infra.emf.search.tests.ecore.testsuites");

        //
		// Default Ecore Queries
        //
		suite.addTest(new DefaultEcoreQueryHelperTestCase("testCreateWorkspaceModelSimpleTextualSearchQuery"));
		suite.addTest(new DefaultEcoreQueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery1"));
		suite.addTest(new DefaultEcoreQueryHelperTestCase("testRunWorkspaceModelSimpleTextualSearchQuery2"));

        //
		// Filtered Ecore Queries
        //
		suite.addTest(new FilteredEcoreQueryHelperTestCase("testCreateWorkspaceModelFilteredTextualSearchQuery"));
		suite.addTest(new FilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery1"));
		suite.addTest(new FilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredTextualSearchQuery2"));
		
        //
		// Regex Filtered Ecore Queries
        //
		suite.addTest(new RegexFilteredEcoreQueryHelperTestCase("testCreateWorkspaceModelFilteredRegexSearchQuery"));
		suite.addTest(new RegexFilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery1"));
		suite.addTest(new RegexFilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredRegexSearchQuery2"));
		
        //
		// Case Sensitive Filtered Ecore Queries
        //
		suite.addTest(new CaseSensitiveFilteredEcoreQueryHelperTestCase("testCreateWorkspaceModelFilteredCaseSensitiveSearchQuery"));
		suite.addTest(new CaseSensitiveFilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery1"));
		suite.addTest(new CaseSensitiveFilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery2"));
		suite.addTest(new CaseSensitiveFilteredEcoreQueryHelperTestCase("testRunWorkspaceModelFilteredCaseSensitiveSearchQuery3"));
		
        //
		// Exhaustive Filtered Ecore Queries
        //
		suite.addTest(new ExhaustiveFilteredEcoreQueryHelperTestCase("testRunWorkspaceExhaustiveMetaModelElementsFilteredTextualSearchQuery"));
		
		//
		// Default Ecore Queries
		//
//		suite.addTest(new DefaultFilteredEcoreOnEObjectQueryHelperTestCase("testCreateModelFilteredRegexSearchEObjectQueries"));
//		suite.addTest(new DefaultFilteredEcoreOnEObjectQueryHelperTestCase("testCreateModelGlobalRegexSearchEObjectQueries"));

//		//
//		// Regex Ecore Queries
//		//
//		suite.addTest(new RegexEcoreOnEObjectQueryHelperTestCase("testExecuteModelFilteredRegexSearchEObjectQueries"));
//        suite.addTest(new RegexEcoreOnEObjectQueryHelperTestCase("testExecuteModelGlobalRegexSearchEObjectQueries"));
//		
//		//
//		// Case Sensitive Ecore Queries
//		//
//        suite.addTest(new CaseSensitiveEcoreOnEObjectQueryHelperTestCase("testExecuteModelFilteredCaseSensitiveSearchEObjectQueries"));
//        suite.addTest(new CaseSensitiveEcoreOnEObjectQueryHelperTestCase("testExecuteModelGlobalCaseSensitiveSearchEObjectQueries"));
//		
//		//
//		// Textual Ecore Queries
//		//
//        suite.addTest(new TextualEcoreOnEObjectQueryHelperTestCase("testExecuteModelFilteredTextualSearchEObjectQueries"));
//        suite.addTest(new TextualEcoreOnEObjectQueryHelperTestCase("testExecuteModelGlobalTextualSearchEObjectQueries"));
		
		
		//
		// OCL Ecore queries
		//
		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testCreateGlobalOCLSearchEObjectQueries"));
		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testRunWorkspaceModelSimpleOCLSearchQuery1"));
		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testRunWorkspaceModelSimpleOCLSearchQuery2"));

		return suite;
	}

}
