/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITargetMetaModelProvider.java
 * 
 * Contributors: 
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - improve APIs
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.engine;

import java.util.Collection;

/**
 * <p>
 * Entity managing meta-model target API contracts
 * </p>
 * <p>
 * For instance users wanting to query models should need such informations
 * while wanting to contextually edit a query expression.
 * </p>
 * <p>
 * For example, OCL Engines need to switch between Ecore or UML2 contexts
 * </p>
 * <p>
 * This is required for UI when a same edition area is reused for several
 * different meta-models contexts
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.4.0
 */
public interface ITargetMetaModelProvider {
	Collection<String> getTargetMetaModelIDs();
}
