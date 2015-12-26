/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * DiagramResourceListSelectionDialog.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.dialogs;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

/**
 * A simple resource list selection dialog which aims to propose a list of potential 
 * diagram candidates to be opened as a result of user selection double-click action
 * from the model search page 
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @since 0.7.0
 */
public final class DiagramResourceListSelectionDialog extends
		ResourceListSelectionDialog {

	public DiagramResourceListSelectionDialog(Shell parentShell, IResource[] resources) {
		super(parentShell, resources);
	}
	
	@Override
	protected String adjustPattern() {
		String pattern = super.adjustPattern();
		return "".equals(pattern)?"*":pattern;  //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Control control = super.createDialogArea(parent);
		refresh(false);
		return control;
	}
}
