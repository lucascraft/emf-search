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

package org.eclipse.papyrus.infra.emf.search.core.services;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.core.resource.IModelResourceValidator;
import org.eclipse.papyrus.infra.emf.search.l10n.Messages;

/**
 * Data coming from each modelSearchEngine extension point contribution is stored in this class.
 * 
 * It allows user to cache data coming from extension point registry once and keep it at runtime time in a single place.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchEngineDescriptor {
	public final static String MODEL_SEARCH_ENGINE_ID = "id"; //$NON-NLS-1$
	public final static String MODEL_SEARCH_ENGINE_LABEL_ID = "label"; //$NON-NLS-1$
	public final static String MODEL_SEARCH_ENGINE_QUERY_CLASS_ID = "searchQueryFactory"; //$NON-NLS-1$
	public final static String MODEL_SEARCH_ENGINE_PARAMETERS_CLASS_ID = "queryParametersFactory"; //$NON-NLS-1$
	public final static String MODEL_SEARCH_ENGINE_RESOURCE_VALIDATOR_CLASS_ID = "modelResourceValidator"; //$NON-NLS-1$
	
	private String ID;
	private String label;
	private IModelSearchQueryFactory modelSearchQueryFactory;
	private IModelSearchQueryParametersFactory modelSearchParametersFactory;
	private IModelResourceValidator modelResourceValidator;

	private IConfigurationElement configElement;

	public ModelSearchEngineDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	
	private void load()throws CoreException {
		ID = configElement.getAttribute(MODEL_SEARCH_ENGINE_ID);
		label = configElement.getAttribute(MODEL_SEARCH_ENGINE_LABEL_ID);
		
		modelSearchQueryFactory = (IModelSearchQueryFactory)configElement.createExecutableExtension(MODEL_SEARCH_ENGINE_QUERY_CLASS_ID);
		
		modelSearchParametersFactory = (IModelSearchQueryParametersFactory)configElement.createExecutableExtension(MODEL_SEARCH_ENGINE_PARAMETERS_CLASS_ID);
		
		modelResourceValidator = (IModelResourceValidator)configElement.createExecutableExtension(MODEL_SEARCH_ENGINE_RESOURCE_VALIDATOR_CLASS_ID);
		
        // Sanity check.
        if (ID == null 
        		|| label == null
        		|| modelSearchParametersFactory == null
        		|| modelSearchQueryFactory == null
        		|| modelResourceValidator == null
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
	
	public String getLabel() {
		return label;
	}
	
	public IModelSearchQueryParametersFactory getModelSearchParametersFactory() {
		return modelSearchParametersFactory;
	}
	
	public IModelSearchQueryFactory getModelSearchQueryFactory() {
		return modelSearchQueryFactory;
	}
	
	public IModelResourceValidator getModelResourceValidator() {
		return modelResourceValidator;
	}
}
