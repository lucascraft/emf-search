/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryParametersFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.core.factories;

import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;

/**
 * Client entities wanting to create AbstractModelSearchParameters instances should implement this interface
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 */
public interface IModelSearchQueryParametersFactory {
	/**
	 * Creates a new Model Search Parameters from given parameters
	 */
	IModelSearchQueryParameters createSearchQueryParameters();
}
