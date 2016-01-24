/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelEditorSelectionHandler.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.ui.handlers;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.presentation.GenModelEditor;
import org.eclipse.papyrus.infra.emf.search.ui.handlers.AbstractModelElementEditorSelectionHandler;
import org.eclipse.ui.IEditorPart;

public class GenModelEditorSelectionHandler extends AbstractModelElementEditorSelectionHandler {

	public boolean isCompatibleModelElementEditorSelectionHandler(IEditorPart part) {
		return part instanceof GenModelEditor;
	}

	public IStatus handleOpenTreeEditorWithSelection(IEditorPart part,
			Object selection) {
		if (part instanceof GenModelEditor) {
			((GenModelEditor)part).setSelectionToViewer(Arrays.asList(new Object[]{selection}));
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	protected String getNsURI() {
		return GenModelPackage.eNS_URI;
	}
}