/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLModelSearchQueryParameters.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.ecore.ocl.engine;

import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQueryParameters;

public final class UML2OCLModelSearchQueryParameters extends
		EcoreModelSearchQueryParameters {
	@Override
	public String getModelSearchEngineID() {
		return UML2OCLEngine.ID; //$NON-NLS-1$
	}
}
