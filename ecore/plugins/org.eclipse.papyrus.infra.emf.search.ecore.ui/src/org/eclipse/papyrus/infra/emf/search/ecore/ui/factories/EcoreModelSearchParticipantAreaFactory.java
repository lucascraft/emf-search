/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchParticipantAreaFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.factories;

import org.eclipse.papyrus.infra.emf.search.ecore.ui.areas.EcoreModelSearchParticipantArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Factory responsible in creating ecore model search area to be 
 * contributed in a dedicated tab of the model search page.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreModelSearchParticipantAreaFactory implements IModelSearchAreaFactory {
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		return new EcoreModelSearchParticipantArea(parent, searchPage, SWT.NONE);
	}
	public IModelSearchArea createArea(Composite parent,
			AbstractModelSearchPage searchPageContainer, String nsURI) {
		return new EcoreModelSearchParticipantArea(parent, searchPageContainer, SWT.NONE);
	}
}
