/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelLoaderUtil.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Convenience Ecore based model loader class.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreModelLoaderUtil {
	 public final static EObject openFile(Object object, boolean resolve)  {
		 if (object instanceof Resource) {
			 Resource resource = (Resource) object;
			 if (resolve) {
				 EcoreUtil.resolveAll(resource.getResourceSet());
			 }
			 return resource.getContents().get(0);
		 }
		 return null;
	 }
}
