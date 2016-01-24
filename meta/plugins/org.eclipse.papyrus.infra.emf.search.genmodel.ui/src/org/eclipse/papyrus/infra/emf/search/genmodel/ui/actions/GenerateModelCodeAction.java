/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenerateModelCodeAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.ui.actions;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapter;

public class GenerateModelCodeAction extends AbstractGenerateCodeAction {
	public GenerateModelCodeAction() {
		super("Generate Model");
	}
	@Override
	protected String[] getTargetProjectTypes() {
		return new String[] { GenModelGeneratorAdapter.MODEL_PROJECT_TYPE };
	}

}
