/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchPageAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.actions;

import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelSearchResultPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

public abstract class AbstractModelSearchPageAction extends AbstractModelSearchPopupActionDelegate {
	private ModelSearchResultPage resultPage;
	public AbstractModelSearchPageAction(String text, ImageDescriptor imgDesc) {
		super(text, imgDesc);
	}
	public void setSearchPage(ModelSearchResultPage page) {
		resultPage = page;
	}
	public ModelSearchResultPage getResultPage() {
		return resultPage;
	}
	protected Object getModelSearchResultPageSelection() {
		if (getResultPage() instanceof ModelSearchResultPage) {
			ISelection selection = getResultPage().getViewer().getSelection();
			if (selection instanceof IStructuredSelection) {
				return ((StructuredSelection)selection).getFirstElement();
			}
		}
		return null;
	}
}
