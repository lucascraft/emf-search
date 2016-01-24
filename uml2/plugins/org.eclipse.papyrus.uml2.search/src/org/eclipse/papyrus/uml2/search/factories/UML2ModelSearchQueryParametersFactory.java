/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchQueryParametersFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.factories;

import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.uml2.search.engine.UML2TextualEngine;

/**
 * Wraps UML2ModelSearchQueryParameters creation.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class UML2ModelSearchQueryParametersFactory implements IModelSearchQueryParametersFactory {
	private static UML2ModelSearchQueryParametersFactory instance;
	public UML2ModelSearchQueryParametersFactory() {}
	public static UML2ModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new UML2ModelSearchQueryParametersFactory() : instance;
	}
	protected final class UML2ModelSearchQueryParameters extends AbstractModelSearchQueryParameters {
		@Override
		public String getModelSearchEngineID() {
			return UML2TextualEngine.ID; //$NON-NLS-1$
		}	
	}
	public IModelSearchQueryParameters createSearchQueryParameters() {
		return new UML2ModelSearchQueryParameters();
	}
}
