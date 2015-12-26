/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchGenAdapter.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.codegen.engine;

import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.search.codegen.l10n.Messages;
import org.eclipse.emf.search.codegen.model.generator.GeneratorFactory;
import org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings;

/**
 *
 * {@link AbstractModelSearchGenAdapter} are created by corresponding {@link GenModelGeneratorAdapterFactory}, 
 * and are responsible of Model Search code generations triggers registred to 
 * org.eclipse.emf.codegen.ecore.generatorAdapters extension point.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 *
 */
public abstract class AbstractModelSearchGenAdapter extends GenBaseGeneratorAdapter implements IModelSearchGenAdapter{
	
	public AbstractModelSearchGenAdapter(GeneratorAdapterFactory generatorAdapterFactory) {
		super(generatorAdapterFactory);
	}
	
	/**
	 * Method responsible for model search plugins specific generations. This method is triggered everytime a user launch 
	 * a "model.edit" generation from right-click menu of EMF .genmodel editor.
	 * 
	 * @param settings {@link ModelSearchGenSettings} defining either specific model search settings & a reference to 
	 * {@link GenModel} originally triggerring the code generation
	 * @param monitor {@link Monitor} being seused to keep user up to date with generation status
	 * @return {@link Diagnostic} Diagnostic.OK_INSTANCE if generation succeeded, Diagnostic.OK_INSTANCE otherwise
	 */
	protected abstract Diagnostic generateModelSearchStuff(ModelSearchGenSettings settings, Monitor monitor);
	
	@Override
	public boolean canGenerate(Object object, Object projectType)
	{
		return EDIT_PROJECT_TYPE.equals(projectType) ? super.canGenerate(object, projectType) : false;
	}

	@Override
	protected Diagnostic generateEdit(Object object, Monitor monitor)
	{
		GenModel genModel = (GenModel) object;

	    monitor.beginTask("", 2); //$NON-NLS-1$
	    monitor.subTask(Messages.getString("AbstractModelSearchGenAdapter.GenModelSearch")); //$NON-NLS-1$

	    ensureProjectExists
	      (genModel.getEditDirectory(), genModel, EDIT_PROJECT_TYPE, genModel.isUpdateClasspath(), createMonitor(monitor, 1));

	    ModelSearchGenSettings settings = initModelSearchGenSettings(genModel);
	    
	    return generateModelSearchStuff(settings, monitor);
	}

	/**
	 * Method responsible for model search projects generation settings initialization.
	 * 
	 * @param genModel Reference to 
	 * {@link GenModel} originally triggerring the code generation
	 */
	public ModelSearchGenSettings initModelSearchGenSettings(GenModel genModel) {
		ModelSearchGenSettings modelSearchGenSettings = GeneratorFactory.eINSTANCE.createModelSearchGenSettings();
	    modelSearchGenSettings.setGenModel(genModel);
	    return modelSearchGenSettings;
	}
}
