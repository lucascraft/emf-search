/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLModelSearchQuery.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ecore.ocl.engine;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.papyrus.uml2.search.ecore.ocl.evaluators.UML2OCLInvariantModelSearchQueryEvaluator;
import org.eclipse.papyrus.uml2.search.ecore.ocl.l10n.Messages;
import org.eclipse.papyrus.uml2.search.engine.UML2ModelSearchQuery;
import org.eclipse.papyrus.uml2.search.ocl.Activator;

/**
 * Gathers all model search settings to run an OCL Ecore query.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class UML2OCLModelSearchQuery extends UML2ModelSearchQuery {
	public final static String OCL_MODEL_SEARCH_IMAGE_PATH = "icons/obj16/uml2.gif"; //$NON-NLS-1$
	public final static String OCL_MODEL_SEARCH_RESULT_IMAGE_PATH = "icons/obj16/osearch.gif"; //$NON-NLS-1$
	
	public UML2OCLModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {
		super(expr, parameters);
	}
	
    public String getResultImagePath() {
    	return OCL_MODEL_SEARCH_RESULT_IMAGE_PATH;
    }
    
    @Override
    public String getBundleSymbolicName() {
    	return Activator.getDefault().getBundle().getSymbolicName();
    }
    
    @Override
    public String getQueryImagePath() {
    	return OCL_MODEL_SEARCH_IMAGE_PATH;
    }

	@Override
	public String getName() {
		return Messages.getString("OCLModelSearchQuery.OCLMessage"); //$NON-NLS-1$
	}

	@Override
	public IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {
		evaluator = getModelSearchParameters().getEvaluator();
		return evaluator!=null?evaluator:(evaluator=new UML2OCLInvariantModelSearchQueryEvaluator<IModelSearchQuery, Object>());
	}
	@Override
	public ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(
			Object resource) {
		return super.buildCompositeSearchResultEntryHierarchy(resource);
	}
}
