/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchQuery.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - clean code
 ******************************************************************************/

package org.eclipse.emf.search.ecore.engine;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.search.core.engine.AbstractModelSearchQuery;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.results.AbstractModelSearchResultEntry;
import org.eclipse.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.Activator;
import org.eclipse.emf.search.ecore.evaluators.EcoreTextualModelSearchQueryEvaluator;
import org.eclipse.emf.search.ecore.l10n.Messages;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.emf.search.ecore.results.EcoreCompoundModelSearchResultEntry;
import org.eclipse.emf.search.ecore.results.EcoreModelSearchResultEntry;
import org.eclipse.emf.search.ecore.utils.EcoreModelLoaderUtil;
import org.eclipse.swt.graphics.Image;

/**
 * Gather all model search settings to run an Ecore query.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreModelSearchQuery extends AbstractModelSearchQuery implements IModelSearchQuery {
	public final static String ECORE_MODEL_SEARCH_IMAGE_PATH = "icons/ecore.gif"; //$NON-NLS-1$
	public final static String ECORE_MODEL_SEARCH_RESULT_IMAGE_PATH = "icons/esearch.gif"; //$NON-NLS-1$
	
	public final static String CASE_SENSITIVE_SEARCH = "searchCaseSensitive"; //$NON-NLS-1$
	public final static String REGEX_SEARCH = "searchRegularExpression"; //$NON-NLS-1$
	public final static String NORMAL_SEARCH = "searchTextual"; //$NON-NLS-1$

	public ModelSearchQueryTextualExpressionEnum getKind() {
		if (getModelSearchParameters() != null) {
			Object o1 = getModelSearchParameters().getData(CASE_SENSITIVE_SEARCH);
			boolean caseSensitiveSearch = o1 instanceof Boolean?((Boolean)o1).booleanValue():false;
			
			Object o2 = getModelSearchParameters().getData(REGEX_SEARCH);
			boolean regexSearch = o2 instanceof Boolean?((Boolean)o2).booleanValue():false;
			
			if (caseSensitiveSearch) {
				return ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE;
			} else if (regexSearch) {
				return ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION;
			}
		}
		return ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT;
	}

	public EcoreModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {
		super(expr, parameters);
	}
	
	public IStatus search(Object resource, boolean notify, IProgressMonitor monitor) {
		try {
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			EObject root = EcoreModelLoaderUtil.openFile(resource, false);

			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, EcoreSupportedElements.getSupportedElements(participantElements));
			
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			((IModelSearchQueryEvaluator)getEvaluator()).eval(this, resource, notify);
			
			monitor.setTaskName(getLabel());
			//FIXME: LB to find a way to unload model after each model search query
			//EcoreModelLoaderUtil.unloadModel((EObject)root);

			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
		} catch (Exception e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public IStatus search(Object resource, boolean notify) {
		try {
			EObject root = EcoreModelLoaderUtil.openFile(resource, false);
			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, EcoreSupportedElements.getSupportedElements(participantElements));
			((IModelSearchQueryEvaluator)getEvaluator()).eval(this, resource, notify);
			//FIXME: LB to find a way to unload model after each model search query
			//EcoreModelLoaderUtil.unloadModel((EObject)root);
		} catch (Exception e) {
			return Status.OK_STATUS; 
			// return Status.CANCEL_STATUS
			//
			// CANCEL_STATUS should be reserved for a "real" user cancel action
			//
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=248525
			//
		}
		return Status.OK_STATUS;
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public IStatus search(Object resource) {
		search(resource, false);
		return Status.OK_STATUS;
	}
	
    public String getResultImagePath() {
    	return ECORE_MODEL_SEARCH_RESULT_IMAGE_PATH;
    }
    
    @Override
    public String getBundleSymbolicName() {
    	return Activator.getDefault().getBundle().getSymbolicName();
    }
    
    @Override
    public String getQueryImagePath() {
    	return ECORE_MODEL_SEARCH_IMAGE_PATH;
    }
    
    public Collection<String> getTargetMetaModelIDs() {
    	return Arrays.asList(new String[] { EcorePackage.eNS_URI });
    }
	
	@Override
	public Collection<?> getValidParticipantMetaElements() {
		return super.getValidParticipantMetaElements();
	}
	
	@Override
	public String getName() {
		if (getEvaluator() != null) {
			switch(getKind()) {
				case NORMAL_TEXT:
					return Messages.getString("EcoreModelSearchQuery.EcoreNormalTextMessage"); //$NON-NLS-1$
				case CASE_SENSITIVE:
					return Messages.getString("EcoreModelSearchQuery.EcoreCaseSensitiveMessage"); //$NON-NLS-1$
				case REGULAR_EXPRESSION:
					return Messages.getString("EcoreModelSearchQuery.EcoreRegularExpressionMessage"); //$NON-NLS-1$
				default:
					return ""; //$NON-NLS-1$
			}
		}
		return Messages.getString("EcoreModelSearchQuery.EcoreMessage"); //$NON-NLS-1$
	}

	// Recursively build an EObject ascending containment hierarchy
	protected AbstractModelSearchResultEntry buildSearchResultEntryHierarchy(Object resource, AbstractModelSearchResultEntry intermediate, Object container) {
		if (container instanceof EObject ) {
			AbstractModelSearchResultEntry entryContainer = new EcoreModelSearchResultEntry(null, resource, container, false);
			entryContainer.addChildren(intermediate);
			intermediate.setParent(entryContainer);
			return buildSearchResultEntryHierarchy(resource, entryContainer, ((EObject)container).eContainer());
		} else {
			return intermediate;
		}
	}
	
	@Override
	public ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(Object resource) {
		return new EcoreCompoundModelSearchResultEntry(null, resource, true);
	}

	@Override
	public ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(
			Object resource, String label) {
		return  new EcoreCompoundModelSearchResultEntry(null, resource, true, label);
	}
	
	@Override
	public ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(
			Object resource, String label, Image image) {
		return  new EcoreCompoundModelSearchResultEntry(null, resource, true, label, image);
	}
	
	@Override
	public IModelResultEntry buildSearchResultOccurenceHierarchy(Object resource, Object eObj, Object eTypedElem, String valuation, boolean notify) {
		IModelResultEntry entry = buildSearchResultEntryHierarchy(resource, eObj);
		
		getModelSearchResult().insert(resource, entry, notify);
		IModelResultEntry entryIntoWhichInsert = findEquivalentExistingEntry(getLeafEntryFromWantedEObject(entry, eObj));
		
		return getModelSearchResult().insert(resource, entryIntoWhichInsert, eTypedElem, valuation, notify);
	}
	
	private IModelResultEntry getLeafEntryFromWantedEObject(IModelResultEntry entry, Object eObj) {
		if (entry instanceof AbstractModelSearchResultEntry) {
			AbstractModelSearchResultEntry e = (AbstractModelSearchResultEntry) entry;
			if (e.getSource().equals(eObj)) {
				return e;
			} else {
				if (e.getResults().size()==1) {
					if (e.getResults().toArray()[0] instanceof IModelResultEntry) {
						return getLeafEntryFromWantedEObject((IModelResultEntry) e.getResults().toArray()[0], eObj);
					}
				}
			}
		}
		return null;
	}

    private IModelResultEntry _findEquivalentExistingEntry(IModelResultEntry entryToFind, IModelResultEntry currentEntry) {
    	if (currentEntry.equals(entryToFind)) return currentEntry;
    	for (Object o : currentEntry.getResults()) {
    		if (o instanceof IModelResultEntry) {
    			IModelResultEntry r = _findEquivalentExistingEntry(entryToFind, (IModelResultEntry) o);
    			if (r instanceof IModelResultEntry) {
    				return (IModelResultEntry) r;
    			}
    		}
    	}
    	return null;
    }
    
    private IModelResultEntry findEquivalentExistingEntry(IModelResultEntry entryToFind) {
    	for (Object o : getModelSearchResult().getResultsFlatenned()) {
    		if (o instanceof IModelResultEntry) {
    			IModelResultEntry e = _findEquivalentExistingEntry(entryToFind, (IModelResultEntry)o);
    			if (e instanceof IModelResultEntry) {
    				return (IModelResultEntry) e;
    			}
    		}
    	}
    	return entryToFind;
    }
    

	@Override
	public IModelResultEntry buildSearchResultEntryHierarchy(Object resource, Object o) {
		AbstractModelSearchResultEntry e = new EcoreModelSearchResultEntry(null, resource, o, true);
		if (o instanceof EObject) {
		    return buildSearchResultEntryHierarchy(resource, e, ((EObject)o).eContainer());
		}
		// Just in case we could deal with some nested exotic objects without containment notions ^^
		return e;
	}

	@Override
	public IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {
		evaluator = getModelSearchParameters().getEvaluator();
		return evaluator!=null?evaluator:(evaluator=new EcoreTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());
	}

	@Override
	protected IModelSearchScope<?, ?> getModelSearchScope() {
		return getModelSearchParameters().getScope();
	}

	@Override
	protected boolean getMatchesNotificationMode() {
		//FIXME: find a way to detect runtime/workspace modes and 
		// disable notification in runtime/headless contexts.
		// Always return true (eg: always notify) until fixing it.
		return true;
	}

	@Override
	public String getLabel() {
		String scopeLabel = getModelSearchScope().getLabel();
		String queryName = getName();
		String queryLabel = Messages.getString("EcoreTextualModelSearchQueryEvaluator.Label");
		int matches = getModelSearchResult().getTotalMatches();
		String matchesLabel = Messages.getString("EcoreModelSearchQuery.matchesMessage2") + (matches>1?Messages.getString("EcoreModelSearchQuery.matchesMessage3"):"");//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return scopeLabel + " " + queryName + " " + queryLabel + " : \'" + getQueryExpression() + "\' (" + matches + " " + matchesLabel + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
}
