/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ToggleOpenButtonAction.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - fix l10n message error
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.actions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelSearchResultPage;
import org.eclipse.papyrus.infra.emf.search.ui.utils.ModelSearchImages;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.osgi.framework.Bundle;

public final class ToggleOpenButtonAction extends Action {
	private ModelSearchResultPage resultPage;
	
	public ToggleOpenButtonAction(ModelSearchResultPage page) {
		super(Messages.getString("ModelSearchResulPage.ToggleEditorOpenModeAction")); //$NON-NLS-1$
		resultPage= page;
		Bundle bundle = Platform.getBundle(org.eclipse.papyrus.infra.emf.search.ui.Activator.getDefault().getBundle().getSymbolicName());
		setImageDescriptor(ModelSearchImagesUtil.getImageDescriptor(bundle, ModelSearchImages.OPEN_DIAGRAM_IMAGE_PATH));
	}

	public void run() {
		BusyIndicator.showWhile(resultPage.getViewer().getControl().getDisplay(), new Runnable() {
			public void run() {
				resultPage.setOpenEditorMode(!isChecked()? ModelEditorOpenEnum.TREE: ModelEditorOpenEnum.DIAGRAM);
			}
		});
	}
}
