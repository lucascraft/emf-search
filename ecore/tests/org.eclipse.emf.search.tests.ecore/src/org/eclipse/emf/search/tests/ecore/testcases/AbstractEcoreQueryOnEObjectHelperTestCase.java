/*******************************************************************************
 * Copyright (c) 2008-2009 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractEcoreQueryOnEObjectHelperTestCase.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.search.tests.ecore.testcases;


import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.search.tests.utils.ModelSearchResourceUtils;
import org.eclipse.emf.test.common.EMFTestCommonPlugin;
import org.osgi.framework.Bundle;

public abstract class AbstractEcoreQueryOnEObjectHelperTestCase extends TestCase {
	protected Map<URL, EObject> roots;
	
	public AbstractEcoreQueryOnEObjectHelperTestCase(String testName) {
		super(testName);
	}

	protected void initResourceSet(ResourceSet resourceSet) {
		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}

	@Override
	protected void setUp() throws Exception {
		roots = new HashMap<URL, EObject>();
		
		ModelSearchResourceUtils.initEcoreModelSearchTestProject();
	
		Bundle bundle = EMFTestCommonPlugin.getPlugin().getBundle();

		// Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

		initResourceSet(resourceSet);
		
		Enumeration<URL> ecores = bundle.findEntries("models", "*.ecore", true);
		while (ecores.hasMoreElements()) {
			URL url = ecores.nextElement();
			
			URI result = URI.createFileURI(FileLocator.toFileURL(url).getFile());

			Resource resource = resourceSet.createResource(result);
			resource.load(Collections.EMPTY_MAP);
	
			roots.put(url, resource.getContents().get(0));
		}
	}
}
