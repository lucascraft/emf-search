/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchAreaFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.areas;

import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.swt.widgets.Composite;

/**
 * Basic Model Search Area Factory API contract.
 * 
 * @see IModelSearchArea
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 *
 */
public interface IModelSearchAreaFactory {
	/**
	 * Creates an area from both {@link Composite} parent area and abstract notion of Model Search Page
	 * (Eg. A specialized Eclipse UI search page for meta models).
	 *  
	 * @param parent {@link Composite} parent area
	 * @param searchPageContainer  {@link AbstractModelSearchPage} implementing {@link ISearchPage} page APIs
	 * 
	 * @return a newly created area
	 */
	IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPageContainer);
	/**
	 * Creates an area from both {@link Composite} parent area and abstract notion of Model Search Page
	 * (Eg. A specialized Eclipse UI search page for meta models).
	 *  
	 * @param parent {@link Composite} parent area
	 * @param searchPageContainer  {@link AbstractModelSearchPage} implementing {@link ISearchPage} page APIs
	 * @param nsURI URI of the meta model package which area edition context will be associated
	 * 
	 * @return a newly created area
	 */
	IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPageContainer, String nsURI);
}
