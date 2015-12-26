/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreTextModelSearchQueryAreaFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.ui.factories;

import org.eclipse.emf.search.ecore.ui.areas.EcoreTextualModelSearchQueryArea;
import org.eclipse.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Factory responsible in creating ecore model search area to be 
 * contributed in a dedicated tab of the model search page.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreTextModelSearchQueryAreaFactory implements IModelSearchAreaFactory {
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		return new EcoreTextualModelSearchQueryArea(parent, searchPage, SWT.NONE);
	}
	public IModelSearchArea createArea(Composite parent,
			AbstractModelSearchPage searchPageContainer, String nsURI) {
		return new EcoreTextualModelSearchQueryArea(parent, searchPageContainer, SWT.NONE);
	}
}
