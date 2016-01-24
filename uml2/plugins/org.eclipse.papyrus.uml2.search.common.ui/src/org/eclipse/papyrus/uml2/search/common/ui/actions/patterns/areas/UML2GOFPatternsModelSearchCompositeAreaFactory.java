/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GOFPatternsModelSearchCompositeAreaFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.actions.patterns.areas;

import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2GOFPatternsModelSearchCompositeAreaFactory implements IModelSearchAreaFactory {
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		return new UML2GOFPatternsModelSearchQueryArea(parent, searchPage, SWT.NONE);
	}
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPageContainer, String nsURI) {
		return new UML2GOFPatternsModelSearchQueryArea(parent, searchPageContainer, SWT.NONE);
	}
}
