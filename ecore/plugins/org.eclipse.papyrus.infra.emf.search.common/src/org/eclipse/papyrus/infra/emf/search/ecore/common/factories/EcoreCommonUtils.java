/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms 
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreCommonUtils.java
 * 
 * Contributors:
 * 			Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 			Lucas Bigeardel - clean code
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.common.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

/**
 * Defines a basic useful set of Ecore related item provider adapter factories.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreCommonUtils {
	/**
	 * Implement few Ecore basic composeable Adapter Factories.
	 * This will be overidden by user. This would allow to avoid void lists ... ^^
	 */
	public static List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

		composeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());

		return composeableAdapterFactories;
	}
}
