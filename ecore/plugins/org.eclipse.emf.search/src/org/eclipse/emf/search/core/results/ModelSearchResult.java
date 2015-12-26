/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResult.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - align on new APIs for model search replace
 * 		Lucas Bigeardel - improve integration with platform search result page
 * 		Lucas Bigeardel - added access to results as their meta elements list
 * 		Lucas Bigeardel - added compound result entries support
 ******************************************************************************/

package org.eclipse.emf.search.core.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.l10n.Messages;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.osgi.framework.Bundle;

/**
 * Defines a model search result to be computed by the eclipse search mechanism in order to 
 * contribute content to the eclipse search view. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchResult extends AbstractTextSearchResult implements IModelSearchResult, IEditorMatchAdapter, IFileMatchAdapter  {
    /**
     * Currently considered search query
     */
	private IModelSearchQuery searchQuery;
	
	/**
	 * Current Search Entries
	 */
    private Map <Object, Collection<Object>> searchEntries;
    
    /**
     * Search result listeners
     */
    private Collection<ISearchResultListener> searchResultListeners;
    
    /**
     * Constructor 
     * 
     * @param query model search query to consider
     */
    public ModelSearchResult(IModelSearchQuery query) {
        searchQuery = query;
        searchEntries = new HashMap <Object, Collection<Object>>();
        searchResultListeners = new ArrayList <ISearchResultListener>();
    }
    
    /**
     * {@inheritDoc}
     */
    public IModelResultEntry insert(Object file, ICompositeModelResultEntry compoundEntry, IModelResultEntry entry, boolean notify) {
        if (searchEntries.get(file) == null) {
        	searchEntries.put(file, new ArrayList<Object>());
        }  
        insert2(searchEntries.get(file), compoundEntry, notify);
        insert4(compoundEntry, entry, notify);
        return entry;
     }


    /**
     * {@inheritDoc}
     */
    public IModelResultEntry insert(Object file, IModelResultEntry entry, boolean notify) {
        if (searchEntries.get(file) == null) {
        	searchEntries.put(file, new ArrayList<Object>());
        }  
        insert2(searchEntries.get(file), entry, notify);
        return entry;
     }

    /**
     * {@inheritDoc}
     */
	public IModelResultEntry insert(Object file, IModelResultEntry entry, Object eTypedElem, String valuation, boolean notify) {
		return insert5(entry, eTypedElem, valuation, notify);
	}

    /**
     * Inserts & merges an entry sequence into an existing entry sequence hierarchy
     * 
     * @param currentEntrySubHierarchyCollection
     * @param entryToInsert
     * @param notify
     */
    private void insert2(Collection<Object> currentEntrySubHierarchyCollection, IModelResultEntry entryToInsert, boolean notify) {
    	if(entryToInsert instanceof ICompositeModelResultEntry) {
    		if (!currentEntrySubHierarchyCollection.contains(entryToInsert)) {
    			currentEntrySubHierarchyCollection.add(entryToInsert);
    			if (notify) {
    				fireItemAdded(entryToInsert);
    			}
    		}
    	} else {
	    	boolean alreadyExist = false;
	    	for (Object currentEntrySubHierarchy : currentEntrySubHierarchyCollection){
				if  (alreadyExist = currentEntrySubHierarchy.equals(entryToInsert)) {
					updateInsertionPoint((IModelResultEntry) currentEntrySubHierarchy, (IModelResultEntry) entryToInsert);
					insert3(currentEntrySubHierarchy, entryToInsert.getResults(), notify);
					break;
				}
			}
	    	if (!alreadyExist) {
	    		currentEntrySubHierarchyCollection.add(entryToInsert);
	    		if (notify) {
	    			fireItemAdded(entryToInsert);
	    		}
	    	}
    	}
    }
    
    /**
     * Inserts & merges an entry into an existing entry sequence hierarchy
     * 
     * @param currentEntrySubHierarchy
     * @param entrySubtreeToInsertCollection
     * @param notify
     */
    private void insert3(Object currentEntrySubHierarchy, Collection<Object> entrySubtreeToInsertCollection, boolean notify) {
		for (Object e2i : entrySubtreeToInsertCollection) {
			if (e2i instanceof IModelResultEntry && currentEntrySubHierarchy instanceof IModelResultEntry) {
				if  (currentEntrySubHierarchy.equals(e2i)) {
					updateInsertionPoint((IModelResultEntry) currentEntrySubHierarchy, (IModelResultEntry) e2i);
				}
				insert2(((IModelResultEntry)currentEntrySubHierarchy).getResults(), (IModelResultEntry)e2i, notify);
			}
		} 
    }
    
   /**
     * Inserts & merges an entry sequence into an existing compound entry
     * 
     * @param compoundEntryToInsert
     * @param entryToInsert
     * @param notify
     */
    private void insert4(ICompositeModelResultEntry compoundEntryToInsert, IModelResultEntry entryToInsert, boolean notify) {
    	boolean alreadyExist = false;
    	for (Object currentEntrySubHierarchy : compoundEntryToInsert.getChildren()){
			if  (alreadyExist = currentEntrySubHierarchy.equals(entryToInsert)) {
				updateInsertionPoint((IModelResultEntry) currentEntrySubHierarchy, (IModelResultEntry) entryToInsert);
				insert3(currentEntrySubHierarchy,  entryToInsert.getResults(), notify);
				break;
			}
		}
    	if (!alreadyExist) {
    		if (!compoundEntryToInsert.getChildren().contains(entryToInsert)) {
    			compoundEntryToInsert.addChild(entryToInsert);
    			if (notify) {
    				fireItemAdded(entryToInsert);
    			}
    		}
    	}
    }
    
    /**
     * Inserts & merges an entry sequence into an existing entry
     * 
     * @param compoundEntryToInsert
     * @param entryToInsert
     * @param notify
     * 
     * @return newly inserted occurence entry
     */
    private IModelResultEntry insert5(IModelResultEntry entryHierarchyIntoWhichInsert, Object eTypedElem, String text, boolean notify) {
    	ModelSearchResultOccurrence occurence = null;
    	for (Object result : entryHierarchyIntoWhichInsert.getResults()) {
    		if (result instanceof IModelResultOccurence) {
    			AbstractModelSearchResultEntry oc = (AbstractModelSearchResultEntry) result;
	    			if (
	    					isAnInvalidETypedElement(((EObject)oc.getSource()), (ETypedElement) eTypedElem)
	    					||
	    					isAnAlreadyExistingOccurenceValuation(text, (ETypedElement) eTypedElem, (IModelResultOccurence) oc)
	    			) {
	    				return occurence;
	    			}
    			}
    	}
	    occurence = new ModelSearchResultOccurrence(entryHierarchyIntoWhichInsert, entryHierarchyIntoWhichInsert.getSource(), eTypedElem, text, true);
	     	
	    entryHierarchyIntoWhichInsert.addChildren(occurence);
	     	
		if (notify) {
			fireItemAdded(occurence);
    	}
		return occurence;
    }
    
    private void updateInsertionPoint(IModelResultEntry oldEntry, IModelResultEntry newEntry) {
		oldEntry.setMatchedOnce(
				!oldEntry.wasMatchedAtleastOnce() ? 
						newEntry.wasMatchedAtleastOnce() : 
							true
		);

    }
    
    private boolean isAnAlreadyExistingOccurenceValuation(String valuation, ETypedElement eTypedElem, IModelResultOccurence oc) {
		return eTypedElem.equals(oc.getTypedElement()) &&
			valuation.equals(
				getTextFromETypedElement(
						((EObject)oc.getSource()),
						oc.getTypedElement())
				);
    }
    
    private boolean isAnInvalidETypedElement(EObject obj, ETypedElement eTypedElem) {
    	for (EAttribute attribute : obj.eClass().getEAllAttributes()) {
    		if (attribute.equals(eTypedElem)) {
    			return false;
    		}
    	}
    	return true;
    }
    
	private String getTextFromETypedElement(EObject obj, ETypedElement elem) {
		if (elem instanceof EAttribute) {
			return EcoreUtil.convertToString(((EAttribute) elem)
					.getEAttributeType(), obj.eGet((EStructuralFeature) elem));
		}
		return ""; //$NON-NLS-1$
	}

    
    /**
     * Computes the total amount of matches for all non zero result resources.
     * 
     * @return Total matches number
     */
    public int getTotalMatches() {
    	int result = 0;
    	for (Object f : searchEntries.keySet()) {
    		result += getMatchesNumberForFile(f);
    	}
    	return result;
    }
    
    /**
     * Computes the total amount of matches for all non zero result resources.
     * 
     * @param eCollection A collection to collect the matching results from
     * 
     * @return Total matches number
     */
    public int getMatches(Collection<Object> eCollection) {
        int result = 0;
        synchronized (eCollection) {
        	try {
	            for (Object e : eCollection) {
	            	if (e instanceof IModelResultEntry) {
	            		IModelResultEntry entry = (IModelResultEntry) e;
	            		result += (entry.wasMatchedAtleastOnce()?1:0) + getMatches(entry.getResults());
	            	}
	            }
        	} catch (Throwable e) {
        		// ugly
        	}
		}
        return result;
    }
    
    /**
     * Computes matches number for given resource.
     * 
     * @param file resource for which compute match number
     */
    public int getMatchesNumberForFile(Object file) {
        return getMatches(searchEntries.get(file));
    }
    
    /**
     * Computes matches number for given resource.
     * 
     * @param file resource for which compute match number
     */
    public Match[] getMatchesForFile(Object file) {
    	if (searchEntries.get(file) != null && ! searchEntries.get(file).isEmpty()) {
    		return searchEntries.get(file).toArray(new Match[0]);
    	}
    	return new Match[0];
    }
    
    /**
     * Called any time a new model search result is added in the existing hierarchy
     */
    private IModelResultEntry fireItemAdded(IModelResultEntry item) {
        SearchResultEvent searchResultEvent = new ModelSearchResultEvent(this);
        for (ISearchResultListener listener : searchResultListeners) {
            listener.searchResultChanged(searchResultEvent);
        }
        return item;
    }
    
    /**
     * Get root items to be displayed in search result page contributed to 
     * org.eclipse.search.searchResultViewPages extension point. 
     * 
     * @return A collection of result hierarchy tree roots
     */
    public Map <Object, Collection <Object>> getRootResultHierarchies()  {
        return searchEntries;
    }
    
    /**
     * Basically transforms result hierarchical trees with potential intermediate non matched items into a list 
     * of matched only result entries.
     * 
     * @param visitor A 'flattener' visitor to be applied
     */
    private Collection<IModelResultEntry> accept(IModelResultEntryVisitor<IModelResultEntry> visitor) {
    	for (Collection<Object> entries : searchEntries.values()) {
    		for (Object entry : entries) {
    			if (entry instanceof IModelResultEntry) {
   					accept2(visitor, (IModelResultEntry)entry);
    			}
    		}
    	}
    	return visitor.getResults();
    }
    
    /**
     * Applies the visitor to a particular result hierarchy tree root among all first level possibles.
     * 
     * @param visitor A 'flattener' visitor to be applied
     * @param entry A particular result hierarchy tree root
     */
    private void accept2(IModelResultEntryVisitor<IModelResultEntry> visitor, IModelResultEntry entry) {
		visitor.visit(entry);
		for (Object e :entry.getResults()){
			if (e instanceof IModelResultEntry) {
				if (e instanceof ICompositeModelResultEntry) {
					for (IModelResultEntry e2 : ((ICompositeModelResultEntry)e).getChildren()) {
						accept2(visitor, e2);
					}
				} else {
					accept2(visitor, (IModelResultEntry)e);
				}
			}
		}
    }
    
    /**
     * Specialized visitor which role is to walk result hierarchy subtrees and
     * to return a list of result hierarchy tree matched items only 
     */
    private class DefaultFlattenerModelResultEntryVisitor extends AbstractModelResultEntryVisitor<IModelResultEntry> {}
    
    /**
     * Specialized visitor which role is to walk result hierarchy subtrees and
     * to return a list of result hierarchy tree matched items only 
     */
    private class FileSpecifiedFlattenerModelResultEntryVisitor extends AbstractModelResultEntryVisitor<IModelResultEntry> {
    	private Object target;
    	public FileSpecifiedFlattenerModelResultEntryVisitor(Object file) {
    		target = file;
		}
    	
        /**
         * {@inheritDoc}
         */
    	@Override
    	protected boolean isValid(IModelResultEntry entry) {
    		return super.isValid(entry) && entry.getTarget().equals(target);
    	}
    }
    
    /**
     * Get result hierarchy tree in a flattened form (i.e a list of all (and only)
     * matched items).
     * 
     * @return A list of result hierarchy tree matched items only
     */
    public Collection<IModelResultEntry> getResultsFlatenned() {
    	return accept(new DefaultFlattenerModelResultEntryVisitor());
    }
    
    /**
     * Get result hierarchy tree in a flattened form (i.e a list of all (and only)
     * matched items).
     * 
     * @return A list of result hierarchy tree matched items only
     */
    public Collection<IModelResultEntry> getResultsFlatennedForFile(Object file) {
    	return accept(new FileSpecifiedFlattenerModelResultEntryVisitor(file));
    }
    
    /**
     * {@inheritDoc}
     */
    public void addListener(ISearchResultListener l) {
        if (!searchResultListeners.contains(l)) {
            searchResultListeners.add(l);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeListener(ISearchResultListener l) {
        searchResultListeners.remove(l);
    }
    
    /**
     * {@inheritDoc}
     */
   public void clean() {
        searchEntries = new HashMap <Object, Collection <Object>>();
    }
    
   /**
    * {@inheritDoc}
    */
	public ImageDescriptor getImageDescriptor() {
		Bundle bundle = Platform.getBundle(searchQuery.getBundleSymbolicName());
		return ModelSearchImagesUtil.getImageDescriptor(bundle, searchQuery.getResultImagePath());
	}
	
    /**
     * {@inheritDoc}
     */
	public Image getImage() {
		Bundle bundle = Platform.getBundle(searchQuery.getBundleSymbolicName());
		return ModelSearchImagesUtil.getImage(bundle, searchQuery.getResultImagePath());
	}
	
    /**
     * {@inheritDoc}
     */
	public ISearchQuery getQuery() {
		return searchQuery;
	}
	
    /**
     * {@inheritDoc}
     */
	public String getLabel() {
		return getQuery().getLabel(); //$NON-NLS-1$
	}
	
    /**
     * {@inheritDoc}
     */
	public String getTooltip() {
		return Messages.getString("ModelExtensibleSearchResult.queryResultMessage3"); //$NON-NLS-1$
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

    /**
     * {@inheritDoc}
     */
	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		Object adapted = editorInput.getAdapter(IFile.class);
		if (adapted instanceof IFile) {
			return computeContainedMatches(result, ((IFile)adapted));
		}
		return new Match[0];
	}

    /**
     * {@inheritDoc}
     */
	public boolean isShownInEditor(Match match, IEditorPart editor) {
		return false;
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public Match[] getMatches(Object element) {
		if (element instanceof IModelResultEntry) {
			IModelResultEntry entry = (IModelResultEntry) element;
			Collection<Object> results = entry.getResults();
			if (results.isEmpty()) {
				if (entry.getParent() != null && !entry.getParent().getResults().isEmpty()) {
					ArrayList<Object> siblingsWithoutItself = new ArrayList<Object>();
					siblingsWithoutItself.addAll(entry.getParent().getResults());
					siblingsWithoutItself.remove(entry);
					return siblingsWithoutItself.toArray(new Match[0]);
				}
			}
			
			return results.toArray(new Match[0]);
		}
		return new Match[0];
	}

    /**
     * {@inheritDoc}
     */
	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IFile file) {
		if (result instanceof ModelSearchResult) {
			return ((ModelSearchResult)result).getMatchesForFile(file);
		}
		return new Match[0];
	}

    /**
     * {@inheritDoc}
     */
	public IFile getFile(Object element) {
		if (element instanceof IModelResultEntry) {
			Object f = ((IModelResultEntry)element).getFile();
			return f instanceof IFile ? (IFile) f : null;
		}
		return null;
	}

    /**
     * {@inheritDoc}
     */
	public List<Object> getResultsObjectsAsList() {
		ArrayList<Object> lst = new ArrayList<Object>();
		for (IModelResultEntry e : getResultsFlatenned()) {
			lst.add(e.getSource());
		}
		return lst;
	}

    /**
     * {@inheritDoc}
     */
	public List<Object> getResultsObjectsForFileAsList(Object file) {
		ArrayList<Object> lst = new ArrayList<Object>();
		for (IModelResultEntry e :getResultsFlatennedForFile(file)) {
			lst.add(e.getSource());
		}
		return lst;
	}
}
