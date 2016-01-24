/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelElementEditorSelectionHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.papyrus.infra.emf.search.ui.services.OpenDiagramParticipantDescriptor;
import org.eclipse.papyrus.infra.emf.search.ui.services.OpenDiagramParticipantExtensionManager;
import org.eclipse.ui.IEditorPart;

/**
 * Abstract implmentation of a model search result selection handling.
 * 
 * It express how a meta elements selection is handlded in cases user want to these elemnts shown 
 * in the editor they orginnate from
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 *
 */
public abstract class AbstractModelElementEditorSelectionHandler implements IModelElementEditorSelectionHandler {
	/**
	 * {@inheritDoc}
	 */
	public IStatus handleModelSearchElementSelection(IEditorPart part, Object selection, ModelEditorOpenEnum mode) {
		switch(mode) {
			case TREE:
				return handleOpenTreeEditorWithSelection(part, selection);
			case DIAGRAM:
				return handleOpenDiagramEditorWithSelection(selection);
		}
		return Status.CANCEL_STATUS;
	}
	/**
	 * {@inheritDoc}
	 */
	public IStatus handleOpenDiagramEditorWithSelection(Object selection) {
		OpenDiagramParticipantDescriptor desc = OpenDiagramParticipantExtensionManager.getInstance().find(getNsURI());
		if (desc instanceof OpenDiagramParticipantDescriptor) {
			List<Object> selectionObjects = new ArrayList<Object>();
			selectionObjects.add(selection);
			desc.getOpenDiagramHandler().openDiagramEditor(getNsURI(), selectionObjects);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public abstract IStatus handleOpenTreeEditorWithSelection(IEditorPart part, Object selection);

	/**
	 * {@inheritDoc}
	 */
	public abstract boolean isCompatibleModelElementEditorSelectionHandler( IEditorPart part);
	/**
	 * Abstract contract for currently considered URI to be taken into account during selection handling
	 * 
	 * @return URI to be taken into account during selection handling
	 */
	protected abstract String getNsURI();
}
