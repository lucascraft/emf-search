/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchArea.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.areas;

import org.eclipse.swt.widgets.Control;

/** 
 * Defines Model Search Area API contract user must implement in order having fully integrated area.
 * 
 * A Model Search area is a kind of Dialog needing dialog settings and validation.
 * 
 * Some additional capabilities such as query preparation are needed as well, this allows to
 * enable/disable search button depending on overall areas status.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 */  
public interface IModelSearchArea {
	/** Get current dialog area control */
	Control getControl();
	
	/** Load dialog settings */
	void loadDialogSettings();
	
	/** Save dialog settings */
	void storeDialogSettings();
	
	/**
	 * Called after validation to prepare search query.
	 * Typically disable search button if area is not valid.
	 */
	void prepare();
	
	/** Convenience fields validation status method.
	 * Returning false usually makes corresponding area icon to be changed into an error one.*/
	boolean validateStatus();
}
