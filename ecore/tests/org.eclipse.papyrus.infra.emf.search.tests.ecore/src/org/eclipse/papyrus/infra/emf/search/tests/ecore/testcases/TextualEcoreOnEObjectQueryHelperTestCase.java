/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TextualFilteredEcoreOnEObjectQueryHelperTestCase.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.ecore.testcases;

import java.net.URL;

import junit.framework.Assert;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.ecore.helper.launcher.EcoreModelSearchQueryLauncherHelper;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResultUtils;

public class TextualEcoreOnEObjectQueryHelperTestCase extends AbstractEcoreQueryOnEObjectHelperTestCase {
	public TextualEcoreOnEObjectQueryHelperTestCase(String name) {
		super(name);
	}
	
//    public void testExecuteModelFilteredTextualSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchResult results = new EcoreModelSearchQueryLauncherHelper().launchFilteredTextualModelSearchEObjectQuery(
//                    "*", 
//                    eObj, 
//                    EcorePackage.Literals.ECLASS
//            );
//            ModelSearchResultUtils.dumpResultsAsTextualHierarchy(results);
//            
//            Assert.assertNotNull("Newly computed result object shouldn't be null", results);
//        }
//    }
//    
//    public void testExecuteModelGlobalTextualSearchEObjectQueries() {
//        for (URL url : roots.keySet()) {
//            EObject eObj = roots.get(url);
//            IModelSearchResult   results = new EcoreModelSearchQueryLauncherHelper().launchGlobalTextualModelSearchEObjectQuery(
//                    "*", 
//                    eObj
//            );
//            ModelSearchResultUtils.dumpResultsAsTextualHierarchy(results);
//            
//            Assert.assertNotNull("Newly computed result object shouldn't be null", results);
//        }
//    }

}
