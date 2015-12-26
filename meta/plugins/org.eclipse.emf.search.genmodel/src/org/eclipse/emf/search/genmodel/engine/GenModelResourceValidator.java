/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelResourceValidator.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.engine;

import org.eclipse.emf.search.core.resource.AbstractModelResourceValidator;

public class GenModelResourceValidator extends AbstractModelResourceValidator {
	public GenModelResourceValidator() {
		addModelFileExtension("genmodel"); //$NON-NLS-1$
	}
}
