/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelResourceValidator.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - Javadoc
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.resource;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;

/**
 * Entity responsible of model resource validity checking.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractModelResourceValidator implements IModelResourceValidator {
	List<String> modelFileExtensions;
	
	/**
	 * Constructor
	 */
	public AbstractModelResourceValidator() {
		modelFileExtensions = new ArrayList<String>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean check(URI uri) {
		return hasSupportedModelFileExtension(uri);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean check(URL url) {
		return hasSupportedModelFileExtension(url);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean check(File file) {
		return hasSupportedModelFileExtension(file);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addModelFileExtension(String extension) {
		modelFileExtensions.add(extension);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<String> getModelFileExtensions() {
		return modelFileExtensions;		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeModelFileExtension(String extension) {
		modelFileExtensions.remove(extension);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasSupportedModelFileExtension(URL url) {
		if (url != null) {
			try {
				return modelFileExtensions.contains(URI.createURI(url.toURI().toString()).fileExtension());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean hasSupportedModelFileExtension(URI uri) {
		if (uri != null && uri.fileExtension() != null) {
			return modelFileExtensions.contains(uri.fileExtension());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasSupportedModelFileExtension(File file) {
		if (file != null) {
			return modelFileExtensions.contains(URI.createFileURI(file.getPath()).fileExtension());
		}
		return false;
	}
}
