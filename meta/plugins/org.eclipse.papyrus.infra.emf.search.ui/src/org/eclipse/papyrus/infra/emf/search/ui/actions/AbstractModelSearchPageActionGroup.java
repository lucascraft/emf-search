/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchPageActionGroup.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add NLS tag
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.Page;

public class AbstractModelSearchPageActionGroup extends ActionGroup {

	public final static String ID = "modelSearchActionGroup"; //$NON-NLS-1$
	
	
	public AbstractModelSearchPageActionGroup(IViewPart part) {
		this(part.getViewSite());
	}
	
	public AbstractModelSearchPageActionGroup(Page page) {
		this(page.getSite());
	}

	private AbstractModelSearchPageActionGroup(IWorkbenchSite site) {
	}

	public void setContext(ActionContext context) {
	}

	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
	}
	
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
	}	

	public void dispose() {
		super.dispose();
	}
}
