/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryParameterProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.parameters;

import java.util.Map;

/**
 * <p>
 * All classes wanting to provide parameters to model search queries should implement this interface
 * </p>
 * <p>
 * It allows users to set arbitrary parameters and retrieve it from specific queries implementations.
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 */
public interface IModelSearchQueryParameterProvider {
	/**
	 * Pair key/value map
	 * 
	 * @return key/value map
	 */
	Map<String, Object> getDataMap();
}
