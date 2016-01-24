/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreCompoundModelSearchResultEntry.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.results;

import org.eclipse.papyrus.infra.emf.search.core.results.AbstractCompositeModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author lucas
 *
 */
public class EcoreCompoundModelSearchResultEntry extends AbstractCompositeModelResultEntry {
	/**
	 * 
	 * @param parent
	 * @param target
	 * @param matched
	 * @param label
	 * @param image
	 */
	public EcoreCompoundModelSearchResultEntry(IModelResultEntry parent, Object target, boolean matched, String label, Image image) {
		super(parent, target, matched, label, image);
	}
	
	/**
	 * 
	 * @param parent
	 * @param target
	 * @param matched
	 * @param label
	 */
	public EcoreCompoundModelSearchResultEntry(IModelResultEntry parent, Object target, boolean matched, String label) {
		super(parent, target, matched, label);
	}
	
	/**
	 * 
	 * @param parent
	 * @param target
	 * @param matched
	 */
	public EcoreCompoundModelSearchResultEntry(IModelResultEntry parent, Object target, boolean matched) {
		super(parent, target, matched);
	}
}
