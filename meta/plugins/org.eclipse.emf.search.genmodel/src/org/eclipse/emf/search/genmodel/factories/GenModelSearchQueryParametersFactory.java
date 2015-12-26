/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchQueryParametersFactory.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.factories;

import org.eclipse.emf.search.core.factories.IModelSearchQueryParametersFactory;
import org.eclipse.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.genmodel.engine.GenModelTextualEngine;

public class GenModelSearchQueryParametersFactory implements IModelSearchQueryParametersFactory {
	private static GenModelSearchQueryParametersFactory instance;
	public GenModelSearchQueryParametersFactory() {}
	public static GenModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new GenModelSearchQueryParametersFactory() : instance;
	}
	protected final class GenModelModelSearchQueryParameters extends AbstractModelSearchQueryParameters {
		@Override
		public String getModelSearchEngineID() {
			return GenModelTextualEngine.ID; //$NON-NLS-1$
		}	
	}
	public IModelSearchQueryParameters createSearchQueryParameters() {
		return new GenModelModelSearchQueryParameters();
	}
}
