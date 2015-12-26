/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchParticipantAreaFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.ui.factories;

import org.eclipse.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.search.ui.areas.UML2ModelSearchParticipantArea;

/**
 * Wrapper class which create a UML2 participant area into the model search page.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class UML2ModelSearchParticipantAreaFactory implements IModelSearchAreaFactory {
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		return new UML2ModelSearchParticipantArea(parent, searchPage, SWT.NONE);
	}
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage, String nsURI) {
		return new UML2ModelSearchParticipantArea(parent, searchPage, SWT.NONE);
	}
}
