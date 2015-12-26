/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * 
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITargetMetaElementSelectionListener.java
 * 
 * Contributors: Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.engine;

/**
 * <p>
 * Defines target meta-element selection listener APIS contract. 
 * </p>
 * <p>
 * Entities interested in reacting on a meta-element selection add 
 * themselves as ITargetMetaElementSelectionListener. 
 * </p>
 * <p>
 * Doing so, these entities are able to handle target meta-element selection 
 * events
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 *
 */
public interface ITargetMetaElementSelectionListener {
    /**
     * User wanting to customly handle a {@link TargetMetaElementSelectionEvent}
     * must implement this interface
     * 
     * @param event {@link TargetMetaElementSelectionEvent} event having been
     * fired
     */
	void handle(TargetMetaElementSelectionEvent event);
}
