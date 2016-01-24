/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchQuery.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.engine;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.resource.IModelResourceValidator;
import org.eclipse.papyrus.infra.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.results.ModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.l10n.Messages;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * Describes an abstract model search query to be sub classed by users
 * </p>
 * <p>
 * This abstraction describes the fact each query needs parameters common
 *  to all kind of queries such as :
 * </p>
 *  <p>
 *  <li>Query expression
 *  <li>Model search engine ID
 *  <li>Scope
 *  </p> 
 * <br>
 * 
 *  Users wanting to describe such query parameters must extends this class. It allows to describe arbitrary parameters using a Object map.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 *
 */
public abstract class AbstractModelSearchQuery implements IModelSearchQuery {
    private IModelSearchResult modelSearchResult;
    
    /**
     * model search query parameters:
     * <ul>
     * <li> <b>String</b>: model search engine ID
     * <li> <b>List<? extends Object></b>: participant Elements
     * <li> <b>IModelSearchQueryEvaluator<IModelSearchQuery, ?></b>: evaluator
     * <li> <b>IModelSearchScope<?, Resource></b>: scope
     * <li> <b>List<?, ?></b>: data
     * </ul>
     */
    private IModelSearchQueryParameters modelSearchParameters;
    
    /**
     * textual expression of query as typed by user or equivalent textual
     * notation if coming from UI configuration. 
     */
	private String expression;
	
	/**
	 * participant elements as defined in  model search query parameters
	 */
	protected List<?> participantElements;
	
	/**
	 * 
	 */
	protected List<EStructuralFeature> features;
	
	/**
	 * Valid participants meta elements 
	 */
	protected Collection<?> validParticipantMetaElements;
	
	/**
	 * Evaluator having to be called whenever visiting a valid meta element 
	 * participant for current query
	 */
	protected IModelSearchQueryEvaluator<IModelSearchQuery, ?> evaluator;

	/**
	 * Builds a new model search query to be evaluated from current parameters
	 *  
	 * @param parameters scope, kind & others ... @see {@link AbstractModelSearchQueryParameters}.
	 */
    public AbstractModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {
    	expression = expr;
        modelSearchResult = new ModelSearchResult(this);
        modelSearchParameters = parameters;
        if (modelSearchParameters != null) {
        	participantElements = getModelSearchParameters().getParticipantElements();
        	features = getModelSearchParameters().getEStructuralFeatures();
        }
   }
    
    /**
     * User custom model result icon.
     * 
     * @return User custom model result icon path relative to its containing bundle.
     */
    public abstract String getResultImagePath();

    /**
     * User custom model query icon.
     * 
     * @return User custom model query icon path relative to its containing bundle.
     */
    public abstract String getQueryImagePath();
    
    /**
     * Retrieves currently executed query's bundle symbolic name.
     * It is important to know the foreign bundle symbolic name for 
     * loading purposes. 
     * 
     * For example, user contibuting new plugins with custom
     * queries implementations usually wants the framework to use
     * its own custom query icons.
     * 
     * @return the foreign bundle unique symbolic name
     */
    public abstract String getBundleSymbolicName();
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#canRerun()
     */
    public boolean canRerun() {
        return true;
    }
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#canRunInBackground()
     */
    public boolean canRunInBackground() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource);

    /**
     * {@inheritDoc}
     */
    public abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource, String label);

    /**
     * {@inheritDoc}
     */
    public abstract ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource, String label, Image image);
    
    /**
     * {@inheritDoc}
     */
	public abstract IModelResultEntry buildSearchResultOccurenceHierarchy(Object resource, Object eObj, Object eTypedElem, String valuation, boolean notify);

    /**
     * {@inheritDoc}
     */
    public abstract IModelResultEntry buildSearchResultEntryHierarchy(Object resource, Object o);
    
    /**
     * Method responsible in processing an object being matched by the current model
     * search query evaluator.
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o A matched object accordingly to current model search evaluator processing
     */
	public Object processSearchResultMatching(Object resource, Object o, boolean notify) {
		IModelResultEntry e = buildSearchResultEntryHierarchy(resource, o);
		getModelSearchResult().insert(resource, e, notify);
		return o;
	}
    
    /**
     * Method responsible in processing an object being matched by the current model
     * search query evaluator.
     * 
     * @param resource A file which the model search query is currently evaluating
     * @param o An occurence in the match object accordingly to current model search evaluator processing
     * @param e A matched object accordingly to current model search evaluator processing
     */
	public Object processSearchResultOccurence(Object resource, Object eObj, Object eTypedElem, String valuation, boolean notify) {
		return buildSearchResultOccurenceHierarchy(resource, eObj, eTypedElem, valuation, notify);
	}
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#getLabel()
     */
    public abstract String getLabel();

    /**
     * @see org.eclipse.search.ui.ISearchQuery#getSearchResult()
     */
    public ISearchResult getSearchResult() {
        return modelSearchResult;
	}

    /**
     * returns Model search scope in terms in participant resources
     * 
     * @return {@link IModelSearchScope} of collected resources to consider
     */
	protected abstract IModelSearchScope<?, ?> getModelSearchScope();

	/**
	 * Return result matching notification mode to be considered
	 * 
	 * @return true if matches have to be notified, false otherwise
	 */
    protected abstract boolean getMatchesNotificationMode();
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
        getModelSearchResult().clean();
    	IModelSearchScope<?, ?> scope = getModelSearchScope();
    	monitor.beginTask(getLabel(), scope.getParticipants().size());
        for (Object participant : scope.getParticipants()) {
        	if (!search(participant, getMatchesNotificationMode(), monitor).equals(Status.OK_STATUS)) {
        		monitor.setCanceled(true);
        		return Status.CANCEL_STATUS;
        	}
        	monitor.worked(1);
        }
        monitor.done();
        return new Status(IStatus.OK, Messages.getString("AbstractModelExtensibleSearchQuery.modelSearchFinishedMessage"), IStatus.OK, Messages.getString("AbstractModelExtensibleSearchQuery.noResultMessage"), null); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    public IStatus run() {
        getModelSearchResult().clean();
    	IModelSearchScope<?, ?> scope = getModelSearchScope();
        for (Object participant : scope.getParticipants()) {
        	if (!search(participant, getMatchesNotificationMode()).equals(Status.OK_STATUS)) {
        		return Status.CANCEL_STATUS;
        	}
        }
        return new Status(IStatus.OK, Messages.getString("AbstractModelExtensibleSearchQuery.modelSearchFinishedMessage"), IStatus.OK, Messages.getString("AbstractModelExtensibleSearchQuery.noResultMessage"), null); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /**
     * @see org.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    public IStatus runWithoutNotifications() {
        getModelSearchResult().clean();
    	IModelSearchScope<?, ?> scope = getModelSearchScope();
        for (Object participant : scope.getParticipants()) {
        	if (!search(participant).equals(Status.OK_STATUS)) {
        		return Status.CANCEL_STATUS;
        	}
        }
        return new Status(IStatus.OK, "Success" /* avoid l10n message : runtime mode */, IStatus.OK, "" /* avoid l10n message : runtime mode */, null); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Users wanting to get results must override this method.
     * 
     * @param resource A valid participant resource according to {@link IModelResourceValidator} checking
     * @param notify true if matches have to be notified to search result page, false otherwise
     * coming from search page selection. 
     * 
     * @return OK {@link IStatus} if search successfully finished, CANCEL {@link IStatus} otherwise
     */
    public abstract IStatus search(Object resource, boolean notify, IProgressMonitor monitor);
    
    /**
     * {@inheritDoc}
     */
	public IModelSearchQueryParameters getModelSearchParameters() {
		return modelSearchParameters;
	}
	
    /**
     * {@inheritDoc}
     */
	public IModelSearchResult getModelSearchResult() {
		return modelSearchResult;
	}
	
    /**
     * {@inheritDoc}
     */
	public abstract IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator();
	
    /**
     * {@inheritDoc}
     */
	public String getName() {
		return Messages.getString("AbstractModelExtensibleSearchQuery.modelNameMessage"); //$NON-NLS-1$
	}

    /**
     * {@inheritDoc}
     */
	public Collection<?> getValidParticipantMetaElements() {
		return validParticipantMetaElements;
	}
	
    /**
     * {@inheritDoc}
     */
	public String getQueryExpression() {
		return expression;
	}
	
    /**
     * {@inheritDoc}
     */
	public boolean isValidTargetMetaModel(String nsURI) {
		return getTargetMetaModelIDs().contains(nsURI);
	}
}
