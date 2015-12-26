/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchEngineDescriptor.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.services;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.core.scope.IModelSearchScopeFactory;
import org.eclipse.emf.search.l10n.Messages;

/**
 * Data coming from each modelSearchEngine extension point contribution is stored in this class.
 * 
 * It allows user to cache data coming from extension point registry once and keep it at runtime time in a single place.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchScopeDescriptor {
	public final static String MODEL_SEARCH_ENGINE_ID = "id"; //$NON-NLS-1$
	public final static String MODEL_SEARCH_SCOPE_FACTORY_ID = "factory"; //$NON-NLS-1$
	
	private String ID;
	private IModelSearchScopeFactory modelSearchScopeFactory;

	private IConfigurationElement configElement;

	public ModelSearchScopeDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	
	private void load()throws CoreException {
		ID = configElement.getAttribute(MODEL_SEARCH_ENGINE_ID);
		
		modelSearchScopeFactory = (IModelSearchScopeFactory)configElement.createExecutableExtension(MODEL_SEARCH_SCOPE_FACTORY_ID);
		
        // Sanity check.
        if (ID == null 
        		|| modelSearchScopeFactory == null
                ) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("ModelSearchEngineDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	
	public String getID() {
		return ID;
	}
	
	public IModelSearchScopeFactory getModelSearchScopeFactory() {
		return modelSearchScopeFactory;
	}
}
