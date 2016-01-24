/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchGenAdapter.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.engine;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;

/**
 * Describes APIs contract for Model Search Generation settings.
 * {@link ModelSearchGenSettings} basically has a reference on a given {@link GenModel}
 * user want to generate Model Search for, plus some custom settings mainly dealing with
 * model editors open/selection handling.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 *
 */
public interface IModelSearchGenAdapter {
	ModelSearchGenSettings initModelSearchGenSettings(GenModel genModel);
}
