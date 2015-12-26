/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * 
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITargetMetaElementSelectionProvider.java
 * 
 * Contributors: Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.engine;

import java.util.List;

/**
 * <p>
 * Defines target meta-element selection provider APIS contract
 * </p> 
 * <p>
 * Entities interested in reacting on a meta-element selection handled by this 
 * provider should add themselves as ITargetMetaElementSelectionListener 
 * </p>
 * <p>
 * Doing so, these entities will be able to handle target meta-element selection 
 * events
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 *
 */
public interface ITargetMetaElementSelectionProvider {
    /**
     * Adds a new {@link ITargetMetaElementSelectionListener} listener
     * 
     * @param listener a given {@link ITargetMetaElementSelectionListener} listener
     */
	void addTargetMetaElementSelectionListener(ITargetMetaElementSelectionListener listener);

	/**
     * Removes a new {@link ITargetMetaElementSelectionListener} listener
     * 
     * @param listener a given {@link ITargetMetaElementSelectionListener} listener
     */
	void removeTargetMetaElementSelectionListener(ITargetMetaElementSelectionListener listener);
	
	/**
	 * Get all registered {@link ITargetMetaElementSelectionListener} listeners
	 * 
	 * @return a list of {@link ITargetMetaElementSelectionListener} listeners
	 */
    List<ITargetMetaElementSelectionListener> getTargetMetaElementSelectionListeners();
    
    /**
     * Notify given {@link TargetMetaElementSelectionEvent} event to a given
     * {@link ITargetMetaElementSelectionListener} listener.
     * 
     * @param listener a given {@link ITargetMetaElementSelectionListener} listener to be notified
     * @param event {@link TargetMetaElementSelectionEvent} event to be fired
     */
    void notifyListener(ITargetMetaElementSelectionListener listener, TargetMetaElementSelectionEvent event);

    /**
     * Notify given {@link TargetMetaElementSelectionEvent} event to all registered
     * {@link ITargetMetaElementSelectionListener} listeners.
     * 
     * @param event {@link TargetMetaElementSelectionEvent} event to be fired
     */
    void notifyListeners(TargetMetaElementSelectionEvent event);
}
