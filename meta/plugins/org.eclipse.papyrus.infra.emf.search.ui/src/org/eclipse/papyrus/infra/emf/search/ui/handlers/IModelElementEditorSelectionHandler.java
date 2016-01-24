/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelElementEditorSelectionHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.handlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.ui.IEditorPart;

/**
 * Defines Model Search Area API contracts user must implement in order 
 * to handle graphical editors opening.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 *
 */
public interface IModelElementEditorSelectionHandler {

	/**
	 * Handles opening of a given edit part with given unique object selection
	 * 
	 * @param part editor part for tree or diagram editor
	 * @param selection a given unique object selection
	 * 
	 * @return OK if editor is valid for current model element, CANCEL otherwise.
	 */
	IStatus handleOpenTreeEditorWithSelection(IEditorPart part, Object selection);
	
	/**
	 * Handles opening of a given unique object selection
	 * 
	 * @param selection a given unique object selection
	 * 
	 * @return true if editor is valid for current model element, false otherwise.
	 */
	IStatus handleOpenDiagramEditorWithSelection(Object selection);
	
	/**
	 * Handles opening of a given edit part with given unique edit part
	 * 
	 * @param part editor part for tree or diagram editor
	 * 
	 * @return true if editor is valid for current model element, false otherwise.
	 */
	boolean isCompatibleModelElementEditorSelectionHandler(IEditorPart part);
	
	/**
	 * Handles opening of a given edit part with given unique object selection
	 * 
	 * @param part editor part for tree or diagram editor
	 * @param selection a given unique object selection
	 * 
	 * @return ok if success CANCEL otherwise
	 */
	IStatus handleModelSearchElementSelection(IEditorPart part, Object selection, ModelEditorOpenEnum mode);
}
