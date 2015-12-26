/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * CaseSensitiveFilteredEcoreOnEObjectQueryHelperTestCase.java
 * 
 * Contributors: Lucas Bigeardel - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.tests.ecore.testcases;

import java.net.URL;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.ecore.helper.launcher.EcoreModelSearchQueryLauncherHelper;
import org.eclipse.emf.search.tests.utils.ModelSearchResultUtils;

public class CaseSensitiveEcoreOnEObjectQueryHelperTestCase extends AbstractEcoreQueryOnEObjectHelperTestCase {
	public CaseSensitiveEcoreOnEObjectQueryHelperTestCase(String name) {
		super(name);
	}
	
//    public void testExecuteModelFilteredCaseSensitiveSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchResult results = new EcoreModelSearchQueryLauncherHelper().launchFilteredCaseSensitiveModelSearchEObjectQuery(
//                    "*", 
//                    eObj, 
//                    EcorePackage.Literals.ECLASS, 
//                    EcorePackage.eNS_URI
//            );
//            ModelSearchResultUtils.dumpResultsAsTextualHierarchy(results);
//            
//            Assert.assertNotNull("Newly computed result object shouldn't be null", results);
//        }
//    }
//    
//    public void testExecuteModelGlobalCaseSensitiveSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchResult   results = new EcoreModelSearchQueryLauncherHelper().launchGlobalCaseSensitiveModelSearchEObjectQuery(
//                    "*", 
//                    eObj, 
//                    EcorePackage.eNS_URI
//            );
//            ModelSearchResultUtils.dumpResultsAsTextualHierarchy(results);
//            
//            Assert.assertNotNull("Newly computed result object shouldn't be null", results);
//        }
//    }

}
