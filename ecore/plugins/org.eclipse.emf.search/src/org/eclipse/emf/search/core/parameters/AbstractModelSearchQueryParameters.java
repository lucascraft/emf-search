/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelExtensibleSearchParameters.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - documentation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.scope.IModelSearchScope;

/**
 * Describes parameters for a given model search query. Some of the parameters are common to all kind of queries such as queryText,
 *  modelSearchEngineID, selectedScope, selection. Users can describe arbitrary parameters by using the data Object map. 
 * <br>
 * <br>
 * <b>Attributes:</b>
 * <dl>
 * <dd><b>queryText</b> : Query text
 * <dd><b>modelSearchEngineID</b> : ID of an existing model search engine registred to 
 * <b>org.eclipse.emf.search.core.modelSearchEngine</b> extension point.
 * <dd><b>selectedScope</b> : a selection scope among all ISearchPageContainer possible kinds.
 * <ul>{ <i>WORKSPACE_SCOPE</i> | <i>SELECTION_SCOPE</i> | <i>WORKING_SET_SCOPE</i> | <i>SELECTED_PROJECTS_SCOPE</i> | <i>CUSTOM ???</i>}.</ul>
 * <dd><b>selection</b> : selection with which the search dialog has been opened (IResource, IFile, IProject, other ???).
 * <dd><b>data</b> : map of arbitrary specific parameters <i>(Map< key:String, value:Object >)</i>
 * <br>
 * </dl>
 * 
 * <br> 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractModelSearchQueryParameters implements IModelSearchQueryParameters {
	private IModelSearchQueryEvaluator<IModelSearchQuery, ?> evaluator;
	
	/** 
	 * Participants are EClassifiers onto which a search query will apply
	 */ 
	private List<? extends Object> participantElements;
	
	/**
	 * EStructuralFeature list involved in query
	 */
	private List<EStructuralFeature> features;
	
	/**
	 * Model Search Scope
	 */
	private IModelSearchScope<?, Resource> scope;
	
	/**
	 * Map of arbitrary search query parameters
	 */
	private Map<String, Object> data;
	
	/**
	 * public constructor for model search query parameters
	 */
	public AbstractModelSearchQueryParameters() {
		data = new HashMap<String, Object>();
	}
	
	/**
	 * Getter for model search engine to which parameters are currently associated
	 */
	public abstract String getModelSearchEngineID();
	
	/**
	 * Getter for model search query text (text depends on contributed query nature : can be OCL, XPath, regex, ...)
	 * 
	 * @return query (possibly non textual)
	 */
	public IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {
		return evaluator;
	}

	/**
	 * Setter for model search query text (text depend on contributed query nature : can be OCL, XPath, regex, ...)
	 * 
	 * @return query (possibly non textual)
	 */
	public void setEvaluator(IModelSearchQueryEvaluator<IModelSearchQuery, ?> evaluator) {
		this.evaluator = evaluator;
	}
	
	/**
	 * Getter for arbitrary model search query parameter
	 * 
	 * @param key arbitrary model search query parameter key ID
	 * 
	 * @return arbitrary model search query parameter for the given key ID
	 */
	public Object getData(String key) {
		return data.get(key);
	}
	
	/**
	 * Setter for arbitrary model search query parameter
	 * 
	 * @param key arbitrary model search query parameter key ID
	 * @param data arbitrary model search query parameter object
	 * 
	 * @return arbitrary model search query parameter for the given key ID
	 */
	public void setData(String key, Object data) {
		this.data.put(key, data);
	}
	
	/**
	 * Getter for model search query participant elements onto which current query apply
	 * 
	 * @return list of model search query participant elements (Usually EClassifiers or specializations)
	 */
	public List<? extends Object> getParticipantElements() {
		return participantElements;
	}
	
	public void setEStructuralFeatures(List<EStructuralFeature> filteredElemList) {
	    features = filteredElemList;
	}
	
	public List<EStructuralFeature> getEStructuralFeatures() {
        return features;
    }
	
	/**
	 * Getter for model search query participant elements onto which current query apply.
	 * @param filteredElemList list of model search query participant elements (Usually EClassifiers or specializations)
	 */
	public void setParticipantElements(List<? extends Object> filteredElemList) {
		participantElements = filteredElemList;
	}

	/**
	 * {@inheritDoc}
	 */
	public IModelSearchScope<?, Resource> getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setScope(IModelSearchScope<?, Resource> scope) {
		this.scope = scope;
	}
}
