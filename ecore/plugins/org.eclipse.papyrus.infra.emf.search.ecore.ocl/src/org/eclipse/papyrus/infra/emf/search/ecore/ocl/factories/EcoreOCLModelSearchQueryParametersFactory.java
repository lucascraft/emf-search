/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreOCLModelSearchQueryParametersFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ocl.factories;

import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.ecore.factories.EcoreModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ocl.engine.EcoreOCLModelSearchQueryParameters;

public class EcoreOCLModelSearchQueryParametersFactory extends EcoreModelSearchQueryParametersFactory {
	private static EcoreOCLModelSearchQueryParametersFactory instance;
	public EcoreOCLModelSearchQueryParametersFactory() {}
	public static EcoreOCLModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new EcoreOCLModelSearchQueryParametersFactory() : instance;
	}
	public AbstractModelSearchQueryParameters createSearchQueryParameters() {
		return new EcoreOCLModelSearchQueryParameters();
	}
}
