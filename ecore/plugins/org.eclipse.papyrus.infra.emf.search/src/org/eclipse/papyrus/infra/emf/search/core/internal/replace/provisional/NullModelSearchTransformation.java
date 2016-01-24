/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * NullModelSearchTransformation.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;

/**
 * A very stupid textual model search replace which purpose is to be used as a placeholder 
 * every time no transformation is necessary but a reference still required
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 */
public class NullModelSearchTransformation extends AbstractModelSearchTransformation<EObject, IModelSearchQuery, String, String> {
	/**
	 * Constructor
	 */
	public NullModelSearchTransformation() {
		super(null, null, "");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isValid() {
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public IStatus perform() {
		return Status.OK_STATUS;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResult() {
		return "";
	}
}
