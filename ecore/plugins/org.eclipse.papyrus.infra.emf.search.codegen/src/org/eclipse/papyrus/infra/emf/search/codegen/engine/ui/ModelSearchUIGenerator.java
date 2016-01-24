/*******************************************************************************
 * Copyright (c) 2007,2008 ANYWARE TECHNOLOGIES and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchUIGenerator.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel  (Anyware Technologies) - initial API
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.engine.ui;

import org.eclipse.papyrus.infra.emf.search.codegen.constants.GenConstants;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.AbstractModelSearchGenerator;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;

/**
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 * 
 */
public final class ModelSearchUIGenerator extends AbstractModelSearchGenerator
{
	public ModelSearchUIGenerator(ModelSearchGenSettings settings) {
		super(settings);
	}

	@Override
	protected String getProjectIDSuffix() {
		return ".search.ui"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTemplatesDirectory() {
		return GenConstants.UI_TEMPLATES_DIRECTORY;
	}
}