/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelResourceValidator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.engine;

import org.eclipse.emf.search.core.resource.AbstractModelResourceValidator;

/**
 * Allows users to describe extensible all the uml query search supported model's extensions.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2ModelResourceValidator extends AbstractModelResourceValidator {
	public UML2ModelResourceValidator() {
		addModelFileExtension("uml"); //$NON-NLS-1$
		addModelFileExtension("uml2"); //$NON-NLS-1$
	}
}

