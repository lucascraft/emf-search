/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * 
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITargetMetaModelHandler.java
 * 
 * Contributors: Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.engine;

import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * Entity managing meta-element target API contracts
 * </p>
 *  <p>
 * For instance users wanting to query elements should need such informations
 * while wanting to contextually edit a query expression.
 * </p>
 * <p>
 * For example, OCL Engines need to switch between Ecore or UML2 contexts
 * </p>
 * <p>
 * This is required for UI when a same edition area is reused for different
 * meta-models contexts
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.4.0
 */
public interface ITargetMetaElementHandler {
    /**
     * Users wanting to react to a meta element participant selection must
     * implement this interface
     * 
     * @param element a given participant meta element having been fired
     */
	void handleTargetMetaElementParticipantSelection(EObject element);
}
