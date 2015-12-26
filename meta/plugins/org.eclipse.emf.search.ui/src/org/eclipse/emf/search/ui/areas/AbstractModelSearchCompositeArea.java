/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchCompositeArea.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.areas;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameterProvider;

/**
 * Abstract description of a mode search composite area which role is to allow
 * UI areas to be able to store/retrieve key/value pairs.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 *
 */
public abstract class AbstractModelSearchCompositeArea implements
		IModelSearchQueryParameterProvider,
		IModelSearchArea
{
	private Map<String, Object> dataMap;
	public AbstractModelSearchCompositeArea() {
		dataMap = new HashMap<String, Object>();
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
