/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenFilteredEPackageMetaElementSelectionDialogHandler.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - clean code
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.common.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.infra.emf.search.ecore.common.ui.dialogs.AbstractModelSearchFilteredMetaElementsSelectionDialog;
import org.eclipse.papyrus.infra.emf.search.ecore.common.ui.dialogs.ModelSearchFilteredEPackageSelectionDialog;
import org.eclipse.swt.widgets.Display;

/**
 * Handles commands raised by M1+M2+P (eg: Open EPackage Dialog)
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas Bigeardel</a>
 *
 */
public final class OpenFilteredEPackageMetaElementSelectionDialogHandler extends AbstractHandler {
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractModelSearchFilteredMetaElementsSelectionDialog d = new ModelSearchFilteredEPackageSelectionDialog(Display.getDefault().getActiveShell(), false);
		d.open();
		return d.getResult();
	}
}