/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IOpenDiagramHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.handlers;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;

/**
 * API contracts for diagram opening on slection handling
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 *
 */
public interface IOpenDiagramHandler {
	/**
	 * Open a digram given its editor ID as contributed to Eclipse UI editor ext point
	 * and a list of element having to be shown after editor to be opened
	 *  
	 * @param editorID editor ID as contributed to Eclipse UI editor ext point
	 * @param list element having to be shown after editor to be opened
	 * 
	 * @return OK if open succeeded, CANCEL otherwise
	 */
	IStatus openDiagramEditor(String editorID, List<Object> list);
	
	
	/**
	 * Getter for diagram files having to be opened
	 * 
	 * @return a list of diagram to be opened typed as {@link IResource}
	 */
	List<IResource> getDiagramFiles();
}
