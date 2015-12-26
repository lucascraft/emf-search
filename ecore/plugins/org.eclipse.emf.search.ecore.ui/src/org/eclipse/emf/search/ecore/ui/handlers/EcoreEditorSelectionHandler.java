/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreEditorSelectionHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.ui.handlers;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.search.ui.handlers.AbstractModelElementEditorSelectionHandler;
import org.eclipse.ui.IEditorPart;

public final class EcoreEditorSelectionHandler extends AbstractModelElementEditorSelectionHandler {
	public boolean isCompatibleModelElementEditorSelectionHandler(IEditorPart part) {
		return part instanceof EcoreEditor;
	}
	
	public IStatus handleOpenTreeEditorWithSelection(IEditorPart part,
			Object selection) {
		if (part instanceof EcoreEditor) {
			((EcoreEditor)part).setSelectionToViewer(Arrays.asList(new Object[]{selection}));
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}
	
	@Override
	protected String getNsURI() {
		return EcorePackage.eNS_URI;
	}
}
