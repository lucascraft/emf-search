/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2EditorSelectionHandler.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - code clean
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ui.handlers;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.handlers.AbstractModelElementEditorSelectionHandler;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.editor.presentation.UMLEditor;

/**
 * Defines entity responsible of editor selection handling.
 * 
 * In other words users defines here how the search double clicked result will be handled in 
 * terms of corresponding editor selection.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class UML2EditorSelectionHandler extends AbstractModelElementEditorSelectionHandler {

	public boolean isCompatibleModelElementEditorSelectionHandler(IEditorPart part) {
		return part instanceof UMLEditor | part instanceof PapyrusMultiDiagramEditor;
	}

	public IStatus handleOpenTreeEditorWithSelection(IEditorPart part,
			Object selection) {
		if (part instanceof UMLEditor) {
			((UMLEditor)part).setSelectionToViewer(Arrays.asList(new Object[]{selection}));
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	protected String getNsURI() {
		return UMLPackage.eNS_URI;
	}
}
