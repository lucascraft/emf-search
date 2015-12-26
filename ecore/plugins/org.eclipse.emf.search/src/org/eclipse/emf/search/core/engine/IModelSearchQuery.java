/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQuery.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - Add {@link ICompositeModelResultEntry} APIs
 ******************************************************************************/

package org.eclipse.emf.search.core.engine;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.resource.IModelResourceValidator;
import org.eclipse.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 */
public interface IModelSearchQuery extends ISearchQuery, INamedQuery, IModelSearchQueryExpressionProvider {
	
    /**
     * User custom model result icon.
     * 
     * @return User custom model result icon path relative to its containing bundle.
     */
    abstract String getResultImagePath();

    /**
     * User custom model query icon.
     * 
     * @return User custom model query icon path relative to its containing bundle.
     */
    abstract String getQueryImagePath();
    
    /**
     * <p>
     * Retrieves currently executed query's bundle symbolic name
     * </p>
     * <p>
     * It is important to know the foreign bundle symbolic name for 
     * loading purposes
     * </p> 
     * <p>
     * For example, user contributing new plugins with custom
     * queries implementations usually wants the framework to use
     * its own custom query icons
     * </p>
     * 
     * @return the foreign bundle unique symbolic name
     */
    abstract String getBundleSymbolicName();
    
    /**
     * @return all currently considered meta model nsURI IDs as String, void list otherwise
     */
    abstract Collection<String> getTargetMetaModelIDs();
    
	boolean isValidTargetMetaModel(String nsURI);

    /**
     * <p>
     * Builds an ascending hierarchy for the given matched object according to current model
     * search query evaluator
     * </p>
     * <p>
     * This hierarchy will be used by model search result framework
     * mechanism to perform result subtrees based insertion algorithms.
     * </p>
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o A matched object accordingly to current model search evaluator processing
     * 
     * @return A model result entry ascending hierarchy. Basically the full EObject
     * containment reverse list to the ResourceSet root
     */
    abstract IModelResultEntry buildSearchResultEntryHierarchy(Object resource, Object o);
    
    /**
     * Method responsible in processing an object being matched by the current model
     * search query evaluator
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param object A matched object accordingly to current model search evaluator processing
     * @param notify true if matching has to be notified to Search Result page for display purposes, false otherwise
     */
	Object processSearchResultMatching(Object resource, Object object, boolean notify);
	
    /**
     * Method responsible in processing an object being matched by the current model
     * search query evaluator.
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o An occurence in the match object accordingly to current model search evaluator processing
     * @param e A matched object accordingly to current model search evaluator processing
     */
	Object processSearchResultOccurence(Object resource, Object o, Object e, String occurence, boolean notify);
	
	/**
     * Users wanting to get results must override this method
     * 
     * @param resource A valid participant resource according to {@link IModelResourceValidator} checking
     * @param notify True if matches have to be notified to UI, false otherwise
     * 
     * @param monitor Queries should deal with this monitor in order to be canceled
     */
	abstract IStatus search(Object resource, boolean notify, IProgressMonitor monitor);
    
	/**
     * Users wanting to get results must override this method.
     * 
     * @param resource A valid participant resource according to {@link IModelResourceValidator} checking
     * @param notify True if matches have to be notified to UI, false otherwise
     * 
     * @return Status.OK if search succeeded, Status.CANCEL otherwise
     */
	abstract IStatus search(Object resource, boolean notify);

	/**
     * Users wanting to get results must override this method.
     * 
     * @param resource A valid participant resource according to {@link IModelResourceValidator} checking
     * 
     * @return Status.OK if search succeeded, Status.CANCEL otherwise
     */
	abstract IStatus search(Object resource);
	
	/**
     * Users wanting to run a query *without* IProgressMonitor support in a *UI* mode must implement this method.
     * 
     * @return Status.OK if search succeeded, Status.CANCEL otherwise
     */
	IStatus run();
    
	/**
     * Users wanting to run a query *without* IProgressMonitor support in a *Runtime* mode must implement this method.
     * 
     * @return Status.OK if search succeeded, Status.CANCEL otherwise
     */
	IStatus runWithoutNotifications();
    
	
	/**
	 * Getter for the currently considered {@link IModelSearchQuery} parameters
	 * 
	 * @return {@link IModelSearchQueryParameters} parameters for the currently considered
	 * {@link IModelSearchQuery}
	 */
	IModelSearchQueryParameters getModelSearchParameters();
	
	/**
	 * Getter for the currently considered {@link IModelSearchQuery} result
	 * 
	 * @return {@link IModelSearchResult} for the currently considered {@link IModelSearchQuery}
	 */
	IModelSearchResult getModelSearchResult();
	
	/**
	 * Getter for the currently considered {@link IModelSearchQuery} evaluator
	 * 
	 * @return {@link IModelSearchQueryEvaluator} for the currently considered {@link IModelSearchQuery}
	 */
	IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator();
	
	/**
	 * Get currently considered {@link IModelSearchQuery} name to be displayed
	 * 
	 * @return textual name to be displayed
	 */
	String getName();

	/**
	 * Getter for the currently considered {@link IModelSearchQuery} valid participant meta elements
	 * 
	 * @return the currently considered valid meta elements to consider
	 */
	Collection<?> getValidParticipantMetaElements();
	
    /**
     * <p>
     * Builds an ascending hierarchy for the given matched object according to current model
     * search query evaluator
     * </p>
     * <p>
     * This hierarchy will be used by model search result framework
     * mechanism to perform result subtrees based insertion algorithms.
     * </p>
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o A matched object accordingly to current model search evaluator processing
     * 
     * @return A model result entry ascending hierarchy. Basically the full EObject
     * containment reverse list to the {@link ICompositeModelResultEntry} reference.
     */
	abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource);

	/**
	 * <p>
     * Builds an ascending hierarchy for the given matched object according to current model
     * search query evaluator
     * </p>
     * <p>
     * This hierarchy will be used by model search result framework
     * mechanism to perform result subtrees based insertion algorithms
     * </p>
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o A matched object accordingly to current model search evaluator processing
     * @param label current label
     * 
     * @return A model result entry ascending hierarchy. Basically the full EObject
     * containment reverse list to the {@link ICompositeModelResultEntry} reference.
     */
	abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource, String label);

    /**
     * <p>
     * Builds an ascending hierarchy for the given matched object according to current model
     * search query evaluator
     * </p>
     * <p>
     * This hierarchy will be used by model search result framework
     * mechanism to perform result subtrees based insertion algorithms
     * </p>
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o A matched object accordingly to current model search evaluator processing
     * @param label current label
     * @param image current image
     * 
     * @return A model result entry ascending hierarchy. Basically the full EObject
     * containment reverse list to the {@link ICompositeModelResultEntry} reference.
     */
	abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource, String label, Image image);
	
	abstract IModelResultEntry buildSearchResultOccurenceHierarchy(Object resource, Object o, Object e, String occurence, boolean notify);

}
