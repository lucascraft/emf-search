/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelResourceValidator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.engine;

import java.net.URL;

import org.eclipse.papyrus.infra.emf.search.core.resource.AbstractModelResourceValidator;

/**
 * Convenience class allowing to check whether a resource is valid or not according 
 * to some basic file extension for example.
 * 
 * Users can extend this class and override the check method in order to add additional, 
 * exhaustive & pertinent resource checkings.
 *   
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreModelResourceValidator extends AbstractModelResourceValidator {
	public EcoreModelResourceValidator() {
		addModelFileExtension("ecore"); //$NON-NLS-1$
	}
}
