/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GOFPatternsModelSearchQueryParametersFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.factories;

import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryParametersFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.AbstractModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;

public class UML2GOFPatternsModelSearchQueryParametersFactory  implements IModelSearchQueryParametersFactory {
	private static UML2GOFPatternsModelSearchQueryParametersFactory instance;
	public UML2GOFPatternsModelSearchQueryParametersFactory() {}
	public static UML2GOFPatternsModelSearchQueryParametersFactory getInstance() {
		return instance == null ? instance = new UML2GOFPatternsModelSearchQueryParametersFactory() : instance;
	}
	protected final class UML2GOFPatternsModelSearchQueryParameters extends AbstractModelSearchQueryParameters {
		@Override
		public String getModelSearchEngineID() {
			return "org.eclipse.papyrus.uml2.search.uml2GOFPatternsSearchEngine"; //$NON-NLS-1$
		}	
	}
	public IModelSearchQueryParameters createSearchQueryParameters() {
		return new UML2GOFPatternsModelSearchQueryParameters();
	}
}
