package org.eclipse.papyrus.infra.emf.search.tests.testsuites;

import org.eclipse.papyrus.infra.emf.search.tests.testcases.DummyTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.eclipse.papyrus.infra.emf.search.tests.testcases");
		//$JUnit-BEGIN$
		suite.addTestSuite(DummyTestCase.class);
		//$JUnit-END$
		return suite;
	}
}
