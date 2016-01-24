/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryParameters.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.parameters;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;

/**
 * <p>
 * Defines APIs contract to be implemented by users wanting to contribute parameters
 * to a model search query
 * </p>
 * <p>
 * Such parameters generally deals with common informations like : model search engine ID,
 * model search scope, model search participants
 * </p>
 * <p>
 * An additional mechanism allows user to define custom parameters trough key - value pairs map
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 */ 
public interface IModelSearchQueryParameters {
	/**
	 * Getter for model search engine to which parameters are currently associated with
	 * 
	 * @return model search engine ID which parameters are currently associated with
	 */
	String getModelSearchEngineID();
	
	/**
	 * Getter for arbitrary model search query parameter.
	 * 
	 * @param key arbitrary model search query parameter key ID
	 * 
	 * @return arbitrary model search query parameter for the given key ID
	 */
	Object getData(String key);
	
	/**
	 * Setter for arbitrary model search query parameter.
	 * 
	 * @param key arbitrary model search query parameter key ID
	 * @param data arbitrary model search query parameter object
	 * 
	 * @return arbitrary model search query parameter for the given key ID
	 */
	void setData(String key, Object data);
	
	/**
	 * Getter for model search query participant elements onto which current query apply.
	 * 
	 * @return list of model search query participant elements (Usually EClassifiers or specializations), null otherwise
	 */
	List<? extends Object> getParticipantElements();
	
    /**
     * Getter for model search query participant elements onto which current query apply.
     * 
     * @param filteredElemList list of model search query participant elements (Usually EClassifiers or specializations)
     */
    void setParticipantElements(List<? extends Object> filteredElemList);

    /**
     * Getter for model search query participant features onto which current query apply.
     * 
     * @return filteredElemList list of model search query participant features
     */
    List<EStructuralFeature>  getEStructuralFeatures();

    /**
     * Setter for model search query participant elements onto which current query apply.
     * 
     * @param filteredElemList list of model search query participant elements (Usually EClassifiers or specializations)
     */
    void setEStructuralFeatures(List<EStructuralFeature> filteredElemList);

	/**
	 * Getter for {@link IModelSearchScope} associated to current query {@link IModelSearchQueryParameters}
	 * 
	 * @return {@link IModelSearchScope} associated to current query {@link IModelSearchQueryParameters}, null otherwise
	 */
	IModelSearchScope<?, Resource> getScope();

	/**
	 * Setter for {@link IModelSearchScope} to associate to current query {@link IModelSearchQueryParameters}
	 * 
	 * @param scope {@link IModelSearchScope} to associate to current query {@link IModelSearchQueryParameters}
	 */
	void setScope(IModelSearchScope<?, Resource> scope);
	
	/**
	 * Getter for {@link IModelSearchQueryEvaluator} associated to current query {@link IModelSearchQueryParameters}
	 * 
	 * @return {@link IModelSearchQueryEvaluator} associated to current query {@link IModelSearchQueryParameters}, null otherwise
	 */
	IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator();
	
	/**
	 * Setter for {@link IModelSearchQueryEvaluator} to associate to current query {@link IModelSearchQueryParameters}
	 * 
	 * @param scope {@link IModelSearchQueryEvaluator} to associate to current query {@link IModelSearchQueryParameters}
	 */
	void setEvaluator(IModelSearchQueryEvaluator<IModelSearchQuery, ?> evaluator);
}
