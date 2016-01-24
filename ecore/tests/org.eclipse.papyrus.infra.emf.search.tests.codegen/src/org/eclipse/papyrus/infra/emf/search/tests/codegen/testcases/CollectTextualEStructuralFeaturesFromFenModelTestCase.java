/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * CollectTextualEStructuralFeaturesFromFenModelTestCase.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.tests.codegen.testcases;


import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;
import org.eclipse.papyrus.infra.emf.search.tests.codegen.utils.CodegenTestsUtil;
import org.eclipse.papyrus.infra.emf.search.tests.utils.ModelSearchResourceUtils;

public class CollectTextualEStructuralFeaturesFromFenModelTestCase extends TestCase {
	
	public CollectTextualEStructuralFeaturesFromFenModelTestCase(String testName) {
		super(testName);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		ModelSearchResourceUtils.initCodegenModelSearchTestProject();
	}	
	
	public void testCollectTextualEStructuralFeaturesFromGenModel() {
		File file = CodegenTestsUtil.instance().findInProject(ModelSearchResourceUtils.getCodegenCommonTestProject(), "moviesDb.genmodel");
		
		assertNotNull("moviesDb.genmodel haven't been found : tests resources have maybe not been copied in the workspace", file);
		
		GenModel genModel = CodegenTestsUtil.instance().visit(file);
		
		assertNotNull("moviesDb.genmodel haven't been sucessfully loaded : file corrupted ?", genModel);
		
		List<GenTypedElement> eStringEStructuralFeatures = CodegenTestsUtil.instance().queryEStringStructuralFeaturesFromGivenGenModel(genModel, file);
		
		assertNotNull("moviesDb.genmodel should not return null list for EString EAttribute query", eStringEStructuralFeatures);
		assertEquals("moviesDb.genmodel should contain 1 or more GenTypedElement in order to proceed", true, eStringEStructuralFeatures.size()>0);

		for (GenTypedElement e : eStringEStructuralFeatures) {
			assertEquals("all GenTypedElement collecteed from moviesDb.genmodel must have a GenFeature type", true,(e instanceof GenFeature)) ;
		}
	}
}
