/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchParticipantArea.java
 * 
 * Contributors: 
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - add target meta elements listening support
 ******************************************************************************/

package org.eclipse.emf.search.ui.areas;

import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.search.core.engine.ITargetMetaElementHandler;
import org.eclipse.emf.search.core.engine.ITargetMetaElementSelectionListener;
import org.eclipse.emf.search.core.engine.ITargetMetaModelProvider;

/**
 * Defines specialized Model Search Area API contracts user must implement in order 
 * to graphically handle Model Search Participant selection.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.1.0
 *
 */
public interface IModelSearchParticipantArea extends
	IModelSearchArea,
	ITargetMetaModelProvider,
	ITargetMetaElementHandler,
	ITargetMetaElementSelectionListener
{
	/**
	 * Handles model search participants selection.
	 *  
	 * @return list of selected participants, void list otherwise.
	 */
    List<EClassifier> getSelectedParticipants();
    List<EStructuralFeature> getSelectedFeatures();
}
