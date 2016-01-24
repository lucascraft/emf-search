/*******************************************************************************
 * Copyright (c) 2009 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AllTests.java
 * 
 * Contributors: Lucas Bigeardel - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.ecore.ocl.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.papyrus.infra.emf.search.tests.ecore.ocl.testcases.DefaultEcoreOCLQueryHelperTestCase;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.papyrus.infra.emf.search.tests.ecore.ocl.testsuites");

		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testCreateGlobalOCLSearchEObjectQueries"));
		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testRunWorkspaceModelSimpleOCLSearchQuery1"));
		suite.addTest(new DefaultEcoreOCLQueryHelperTestCase("testRunWorkspaceModelSimpleOCLSearchQuery2"));
		
		return suite;
	}
}
