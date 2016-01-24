/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelEditorOpenHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.pages;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * API contract fo model editor opening handler
 * 
 * It defines that it is needed to provide element selection list before to open an editor
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 *
 */
public interface IModelEditorOpenHandler {
	void openEditor(List<EObject> list);
}
