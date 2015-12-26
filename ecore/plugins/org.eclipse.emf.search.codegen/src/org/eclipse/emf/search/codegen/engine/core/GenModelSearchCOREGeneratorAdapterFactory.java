/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchCOREGeneratorAdapterFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.codegen.engine.core;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;

/**
 * Extends {@link GenModelGeneratorAdapterFactory} in order to realize contracts needed
 * by org.eclipse.emf.codegen.ecore.generatorAdapters extension point.
 * 
 * It allows to react to Ecore Model generation and triggering a Model Search Core
 * plugin generation each time a user generate its Ecore models.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 *
 */
public final class GenModelSearchCOREGeneratorAdapterFactory extends
		GenModelGeneratorAdapterFactory {

	  @Override
	  public Adapter createGenPackageAdapter() { return null; }
	  
	  @Override
	  public Adapter createGenEnumAdapter() { return null; }

	  @Override
	  public Adapter createGenModelAdapter()
	  {
	    if (genModelGeneratorAdapter == null)
	    {
	      genModelGeneratorAdapter = new GenModelSearchCOREGeneratorAdapter(this);
	    }
	    return genModelGeneratorAdapter;
	  }
}
