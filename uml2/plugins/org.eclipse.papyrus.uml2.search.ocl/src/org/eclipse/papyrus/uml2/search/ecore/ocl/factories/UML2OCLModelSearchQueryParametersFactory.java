/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLModelSearchQueryParametersFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ecore.ocl.factories;

import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.ecore.factories.EcoreModelSearchQueryParametersFactory;
import org.eclipse.papyrus.uml2.search.ecore.ocl.engine.UML2OCLModelSearchQueryParameters;

public final class UML2OCLModelSearchQueryParametersFactory extends EcoreModelSearchQueryParametersFactory {
	private static UML2OCLModelSearchQueryParametersFactory instance;
	public UML2OCLModelSearchQueryParametersFactory() {}
	public static UML2OCLModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new UML2OCLModelSearchQueryParametersFactory() : instance;
	}
	public AbstractModelSearchQueryParameters createSearchQueryParameters() {
		return new UML2OCLModelSearchQueryParameters();
	}
}
