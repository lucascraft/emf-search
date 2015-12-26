/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchScopeFileSystemeVisitor.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - UML2 resources http visitor
 * 
 ******************************************************************************/

package org.eclipse.uml2.search.util;

import java.io.File;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.scope.file.EcoreModelSearchScopeFileSystemVisitor;
import org.eclipse.uml2.uml.resource.CMOF2UMLResource;
import org.eclipse.uml2.uml.resource.UML22UMLResource;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;

/**
 * Recursively visit ModelResourceScope IResource tree adding each potential 
 * participant according to modelSearchEngine extension point resource
 * validator for UML2.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2ModelSearchScopeFileSystemeVisitor extends
		EcoreModelSearchScopeFileSystemVisitor {

	public UML2ModelSearchScopeFileSystemeVisitor(
			IModelSearchScope<Object, Resource> scope, String engineID) {
		super(scope, engineID);
	}
	
	@Override
	protected boolean isParticipantCurrentSearchEngineValid(File f) {
		if (f instanceof File && f.canRead() && f.exists() && ! f.isHidden()) {
			return f.getName().endsWith(".uml2"); //$NON-NLS-1$
		} 
		return false;
	}
	protected void initResourceSet(ResourceSet resourceSet, URI fileURI) {
			Map<String, Object> extensionToFactoryMap = resourceSet
				.getResourceFactoryRegistry().getExtensionToFactoryMap();

			extensionToFactoryMap.put(UML22UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
			extensionToFactoryMap.put(XMI2UMLResource.FILE_EXTENSION,
				XMI2UMLResource.Factory.INSTANCE);
			extensionToFactoryMap.put(CMOF2UMLResource.FILE_EXTENSION,
				CMOF2UMLResource.Factory.INSTANCE);

		}
}
