/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelResourceValidator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.resource;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.URI;

/**
 * <p>
 * This interface defines API contracts to be implemented in order to validate 
 * potential search participant resources
 * </p>
 * <p>
 * <b>This interface is subject to modifications as more complex cheking rules
 * will be introduced in newer versions.</b>
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 *
 */
public interface IModelResourceValidator {
	//FIXME: LB to handle graphical editor extensions here (example: .ecoredi, .umldi)
	
	/**
	 * <p>
	 * Check whether the given resource is valid or not according to user defined rules
	 * </p>
	 * <p>
	 * Rules have to be handled in an implementaion of {@link IModelResourceValidator}
	 * </p>
	 * 
	 * @param resource The currently explored {@link URI}
	 * 
	 * @return true if the evaluated resource is valid, false otherwise
	 */
	boolean check(URI uri);
	
	/**
	 * <p>
	 * Check whether the given resource is valid or not according to user defined rules
	 * </p>
	 * <p>
	 * Rules have to be handled in an implementaion of {@link IModelResourceValidator}
	 * </p>
	 * 
	 * @param resource The currently explored {@link URI}
	 * 
	 * @return true if the evaluated resource is valid, false otherwise
	 */
	boolean check(URL url);
	
	/**
	 * <p>
	 * Check whether the given resource is valid or not according to user defined rules
	 * </p>
	 * <p>
	 * Rules have to be handled in an implementaion of {@link IModelResourceValidator}
	 * </p>
	 * 
	 * @param file The currently explored file
	 * 
	 * @return true if the evaluated resource is valid, false otherwise
	 */
	boolean check(File file);
	
	/**
	 * Add model file extension used to discriminate as search participate
	 * 
	 * @param extension model file extension
	 */
	void addModelFileExtension(String extension);

	/**
	 * Remove model file extension used to discriminate as search participate
	 * 
	 * @param extension model file extension
	 */
	void removeModelFileExtension(String extension);
	
	/**
	 * Get all registred model file extensions.
	 * 
	 * @return All registred model file extensions
	 */
	List<String> getModelFileExtensions();
	
	/**
	 * @return true if a resource has a valid Model file extension, false otherwise.
	 */
	boolean hasSupportedModelFileExtension(URI uri);
	
	/**
	 * @return true if a file has a valid extension, false otherwise.
	 */
	boolean hasSupportedModelFileExtension(File file);
}
