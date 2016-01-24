/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EStringContainerComposedAdapterFactory.java
 * 
 * Contributors: 
 * 			Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.wizard.textual.providers;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.common.factories.EcoreCommonUtils;

/**
 * 
 * @author lucas
 *
 */
public class EStringContainerComposedAdapterFactory extends
		ComposedAdapterFactory {
	/**
	 * Constructor
	 */
	public EStringContainerComposedAdapterFactory() {
		super(EcoreCommonUtils.getMetaElementComposeableAdapterFactories());
	}
}
