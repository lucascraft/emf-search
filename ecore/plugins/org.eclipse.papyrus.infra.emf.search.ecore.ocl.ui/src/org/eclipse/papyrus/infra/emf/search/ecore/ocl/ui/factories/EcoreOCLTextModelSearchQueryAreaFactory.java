/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreOCLTextModelSearchQueryAreaFactory.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ocl.ui.factories;

import org.eclipse.papyrus.infra.emf.search.ecore.ocl.ui.areas.EcoreOCLModelSearchQueryArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public final class EcoreOCLTextModelSearchQueryAreaFactory implements
		IModelSearchAreaFactory {

	public IModelSearchArea createArea(Composite parent,
			AbstractModelSearchPage searchPageContainer) {
		return new EcoreOCLModelSearchQueryArea(parent, searchPageContainer, SWT.NONE);
	}
	public IModelSearchArea createArea(Composite parent,
			AbstractModelSearchPage searchPageContainer, String nsURI) {
		return new EcoreOCLModelSearchQueryArea(parent, searchPageContainer, SWT.NONE);
	}
}
