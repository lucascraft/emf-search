/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchUIGeneratorAdapter.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.engine.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.papyrus.infra.emf.search.codegen.engine.AbstractModelSearchGenAdapter;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;

/**
 * {@link AbstractModelSearchGenAdapter} are created by corresponding {@link GenModelGeneratorAdapterFactory}, 
 * they are responsible of Model Search code generations triggers registred to 
 * org.eclipse.emf.codegen.ecore.generatorAdapters extension point.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 *
 */
public final class GenModelSearchUIGeneratorAdapter extends  AbstractModelSearchGenAdapter{
    
	  public GenModelSearchUIGeneratorAdapter(GeneratorAdapterFactory generatorAdapterFactory)
	  {
	    super(generatorAdapterFactory);
	  }

	  @Override
	  protected Diagnostic generateModelSearchStuff(ModelSearchGenSettings settings, Monitor monitor) {
		  try {
			  return new ModelSearchUIGenerator(settings).generate(monitor);
		  } catch (CoreException e) {
			  return Diagnostic.CANCEL_INSTANCE;
		  } catch (JETException e) {
			  return Diagnostic.CANCEL_INSTANCE;
		  }
	  }
}
