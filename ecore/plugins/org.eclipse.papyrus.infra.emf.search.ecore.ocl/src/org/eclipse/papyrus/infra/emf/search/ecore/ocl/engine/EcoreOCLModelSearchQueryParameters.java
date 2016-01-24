/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreOCLModelSearchQueryParameters.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ocl.engine;

import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQueryParameters;

public final class EcoreOCLModelSearchQueryParameters extends
		EcoreModelSearchQueryParameters {
	@Override
	public String getModelSearchEngineID() {
		return "org.eclipse.papyrus.infra.emf.search.ecoreOCLSearchEngine";
	}
}
