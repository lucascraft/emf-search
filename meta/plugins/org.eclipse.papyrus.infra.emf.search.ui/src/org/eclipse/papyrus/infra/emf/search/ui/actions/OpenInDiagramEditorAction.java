/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenInDiagramEditorAction.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.actions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.emf.search.core.results.AbstractModelSearchResultEntry;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelSearchResultPage;
import org.eclipse.papyrus.infra.emf.search.ui.utils.ModelSearchImages;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeSelection;
import org.osgi.framework.Bundle;

public final class OpenInDiagramEditorAction extends Action {
	private ModelSearchResultPage resultPage;
	
	public OpenInDiagramEditorAction(ModelSearchResultPage page) {
		super(Messages.getString("ModelSearchResulPage.OpenInDiagramEditorAction")); //$NON-NLS-1$
		resultPage= page;
		Bundle bundle = Platform.getBundle(org.eclipse.papyrus.infra.emf.search.ui.Activator.getDefault().getBundle().getSymbolicName());
		setImageDescriptor(ModelSearchImagesUtil.getImageDescriptor(bundle, ModelSearchImages.OPEN_DIAGRAM_IMAGE_PATH));
	}

	public void run() {
		resultPage.setOpenEditorMode(ModelEditorOpenEnum.DIAGRAM);
		resultPage.getToggleOpenEditorButtonAction().setChecked(false);

		Object o = resultPage.getViewer().getSelection();
		if (o instanceof TreeSelection) {
			TreeSelection tSel = (TreeSelection) o;
			if (tSel.getFirstElement() instanceof AbstractModelSearchResultEntry) {
				resultPage.openEditor(tSel.getFirstElement());
			}
		}
	}
}
