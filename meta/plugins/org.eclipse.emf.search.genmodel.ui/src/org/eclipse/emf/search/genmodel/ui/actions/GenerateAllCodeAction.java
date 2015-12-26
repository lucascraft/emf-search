/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenerateAllCodeAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.ui.actions;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapter;

public class GenerateAllCodeAction extends AbstractGenerateCodeAction {
	public GenerateAllCodeAction() {
		super("Generate All");
	}
	@Override
	protected String[] getTargetProjectTypes() {
		return new String[] { 
			GenModelGeneratorAdapter.MODEL_PROJECT_TYPE,
			GenModelGeneratorAdapter.EDIT_PROJECT_TYPE, 
			GenModelGeneratorAdapter.EDITOR_PROJECT_TYPE, 
			GenModelGeneratorAdapter.TESTS_PROJECT_TYPE, 
		};
	}

}
