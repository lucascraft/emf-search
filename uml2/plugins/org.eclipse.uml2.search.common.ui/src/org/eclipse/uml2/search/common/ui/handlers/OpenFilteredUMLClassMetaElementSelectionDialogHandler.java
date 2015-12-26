/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenFilteredUMLClassMetaElementSelectionDialogHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.common.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.search.ecore.common.ui.dialogs.AbstractModelSearchFilteredMetaElementsSelectionDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.search.common.ui.dialogs.ModelSearchFilteredUMLClassSelectionDialog;

/**
 * Handles commands raised by M1+M2+U (eg: Open UML2 Class Dialog)
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas Bigeardel</a>
 *
 */
public final class OpenFilteredUMLClassMetaElementSelectionDialogHandler extends AbstractHandler {
	public Object execute(ExecutionEvent evt) throws ExecutionException {
		AbstractModelSearchFilteredMetaElementsSelectionDialog d = new ModelSearchFilteredUMLClassSelectionDialog(Display.getDefault().getActiveShell(), false);
		d.open();
		return d.getResult();
	}
}
