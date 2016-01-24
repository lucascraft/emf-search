/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResultItemProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.utils;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultOccurence;
import org.eclipse.papyrus.infra.emf.search.core.results.ModelSearchResult;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Manages a result entry hierarchy from a search result input content.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchResultItemProvider implements ITreeContentProvider {
    private ModelSearchResult input;
    
    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof Resource) {
        	return input.getRootResultHierarchies().get((Resource) parentElement).toArray();
        } else if (parentElement instanceof ICompositeModelResultEntry) {
        	return ((ICompositeModelResultEntry)parentElement).getChildren().toArray();
        } else if (parentElement instanceof IModelResultEntry) {
        	return ((IModelResultEntry)parentElement).getResults().toArray();
        } else if (parentElement instanceof IModelResultOccurence) {
        	return ((IModelResultOccurence)parentElement).getResults().toArray();
        }
        return new Object[0];
    }
    
    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof Resource) {
            return input;
        } else if (element instanceof IModelResultEntry) {
            return ((IModelResultEntry) element).getParent();
        } else if (element instanceof IModelResultOccurence) {
            return ((IModelResultOccurence) element).getParent();
        }
        return null;
    }
    
    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
    	if (element instanceof Resource) {
    		return true;
    	} else if (element instanceof ICompositeModelResultEntry) {
    		return !((ICompositeModelResultEntry)element).getChildren().isEmpty();
    	} else if (element instanceof IModelResultEntry) {
    		return !((IModelResultEntry)element).getResults().isEmpty();
    	} else if (element instanceof IModelResultOccurence) {
    		return !((IModelResultOccurence)element).getResults().isEmpty();
    	}
    	return false;
    }
    
    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
	public Object[] getElements(Object inputElement) {
        if (inputElement instanceof ModelSearchResult) {
        	ModelSearchResult input = (ModelSearchResult) inputElement;
            Set <Object> files = input.getRootResultHierarchies().keySet();
            return files.toArray();
        } else if (inputElement instanceof Collection) {
        	return ((Collection<?>) inputElement).toArray();
        }
        return new Object[0];
    }
    
    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput instanceof ModelSearchResult) {
            input = (ModelSearchResult) newInput;
        }
    }
    
	public void dispose() {}
}
