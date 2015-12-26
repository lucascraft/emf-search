/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * DefaultFilteredEcoreOnEObjectQueryHelperTestCase.java
 * 
 * Contributors: Lucas Bigeardel - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.tests.ecore.testcases;

import java.net.URL;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;

public class DefaultFilteredEcoreOnEObjectQueryHelperTestCase extends AbstractEcoreQueryOnEObjectHelperTestCase {
	public DefaultFilteredEcoreOnEObjectQueryHelperTestCase(String name) {
		super(name);
	}
	
//    public void testCreateModelFilteredRegexSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredRegexModelSearchEObjectQuery(".*", EcorePackage.Literals.ECLASS, eObj, EcorePackage.eNS_URI);
//            Assert.assertNotNull("Newly created query object shouldn't be null", query);
//        }
//    }
//
//    public void testCreateModelGlobalRegexSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildGlobalRegexModelSearchEObjectQuery(".*", eObj, EcorePackage.eNS_URI);
//            Assert.assertNotNull("Newly created query object shouldn't be null", query);
//        }
//    }
}
