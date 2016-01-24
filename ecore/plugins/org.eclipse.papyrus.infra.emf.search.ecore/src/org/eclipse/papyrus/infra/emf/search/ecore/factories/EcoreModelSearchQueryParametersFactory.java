/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchQueryParametersFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.factories;

import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQueryParameters;

/**
 * Client entities wanting to create EcoreModelSearchParameters instances should implement this interface.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreModelSearchQueryParametersFactory implements IModelSearchQueryParametersFactory {
	private static EcoreModelSearchQueryParametersFactory instance;
	public EcoreModelSearchQueryParametersFactory() {}
	public static EcoreModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new EcoreModelSearchQueryParametersFactory() : instance;
	}
	public IModelSearchQueryParameters createSearchQueryParameters() {
		return new EcoreModelSearchQueryParameters();
	}
}
