/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelResultOccurence.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.swt.graphics.Image;

/**
 * API contract for result occurence
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public interface IModelResultOccurence extends IModelResultEntry {
	/**
	 * Getter image for occurence
	 * @return image of occurence
	 */
	Image getImage();
	
	/**
	 * Getter for current occurence textual valuation
	 * @return current occurence textual valuation
	 */
	String getValuation();
	
	/**
	 * Getter for current {@link ETypedElement} element
	 * @return currently considered {@link ETypedElement} element
	 */
	ETypedElement getTypedElement();
}
