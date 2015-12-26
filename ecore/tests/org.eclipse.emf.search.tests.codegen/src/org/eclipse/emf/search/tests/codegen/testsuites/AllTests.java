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

package org.eclipse.emf.search.tests.codegen.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.emf.search.tests.codegen.testcases.CollectTextualEStructuralFeaturesFromFenModelTestCase;
import org.eclipse.emf.search.tests.codegen.testcases.GenerateMoviesDbSearchCoreAndUITestCase;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.emf.search.tests.gcodegen.testsuites");

		suite.addTest(new CollectTextualEStructuralFeaturesFromFenModelTestCase("testCollectTextualEStructuralFeaturesFromGenModel"));
		suite.addTest(new GenerateMoviesDbSearchCoreAndUITestCase("testGenerateMoviesSearchCoreAndUI"));
		
		return suite;
	}
}
