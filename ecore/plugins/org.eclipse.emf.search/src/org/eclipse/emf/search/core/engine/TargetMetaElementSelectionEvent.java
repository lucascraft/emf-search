/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * 
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TargetMetaElementSelectionEvent.java
 * 
 * Contributors: Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.engine;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * 
 * Event embedding information for a meta element selection
 *
 */
public class TargetMetaElementSelectionEvent extends SelectionChangedEvent {
	/**
	 * Model element unique identifier from which the event have been issued
	 */
	private String targetModelNsURI; 
	
	/**
	 * Express whether a model element selection is blocking
	 */
	private TargetSelectionEnum participantSelectionBlocking;
	
	/**
	 * Generated YES
	 */
	private static final long serialVersionUID = -5454092109816892259L;

	/**
	 * Constructor
	 * 
	 * @param selectionProvider selection provider that fired the event notification 
	 * @param selection the target meta element selection to be notified 
	 * @param nsURI associated package URI id
	 */
	public TargetMetaElementSelectionEvent(ISelectionProvider selectionProvider, ISelection selection, String nsURI) {
		this(selectionProvider, selection, nsURI, TargetSelectionEnum.BLOCKING);
	}
	
	/**
	 * Constructor
	 * 
	 * @param selectionProvider selection provider that fired the event notification 
	 * @param selection the target meta element selection to be notified 
	 * @param nsURI associated package URI id
	 * @param mode {@link TargetSelectionEnum} among BLOCKING and NONE, if selection is blocking other parameter selection 
	 */
	public TargetMetaElementSelectionEvent(ISelectionProvider selectionProvider, ISelection selection, String nsURI, TargetSelectionEnum mode) {
		super(selectionProvider, selection);
		targetModelNsURI = nsURI;
		participantSelectionBlocking = mode;
	}

	/**
	 * Getter for currently considered selection target model package URI id
	 * 
	 * @return highly likely non null currently considered selection target model package URI id
	 */
	public String getTargetModelNsURI() {
		return targetModelNsURI;
	}
	public TargetSelectionEnum isParticipantSelectionBlocking() {
		return participantSelectionBlocking;
	}
}
