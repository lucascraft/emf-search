/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractMetaModelParticipantsItemProvider.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add non null retrun ref to getParent
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Meta Elements participants item provider.
 * 
 * Has responsability to provide elements Model Search Participant area
 * selection tree.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 */
public abstract class AbstractMetaModelParticipantsItemProvider implements ITreeContentProvider {
	/**
	 * {@inheritDoc}
	 */
    public Object[] getChildren(Object parentElement) {
    	return new Object[0];
    }
	/**
	 * {@inheritDoc}
	 */
    public Object getParent(Object element) {
    	return new Object[0];
    }
	/**
	 * {@inheritDoc}
	 */
    public boolean hasChildren(Object element) {
    	return false;
    }
	/**
	 * {@inheritDoc}
	 */
    public Object[] getElements(Object inputElement) {
    	return new Object[0];
    }
	/**
	 * {@inheritDoc}
	 */
    public void dispose() {}
	/**
	 * {@inheritDoc}
	 */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
}
