/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchScope.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scope is a valid Model participant set against which model search queries will be evaluated
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchScope<P, O> implements IModelSearchScope<P, O> {
	/**
	 * Model Search scope label
	 */
	private String kindLabel;

	/**
	 * Model Search element participants
	 */
	private List<P> particpants;
	
	/**
	 * Constructor
	 * @param label textual representation for model search scope
	 */
	public ModelSearchScope(String label) {
		kindLabel = label;
		particpants = new ArrayList<P>();
	}
	
	/**
	 * Add a Model Search participant
	 */
	public void addParticipant(P participant) {
		particpants.add(participant);		
	}

	/**
	 * Remove a Model Search participant
	 */
	public void removeParticipant(P participant) {
		particpants.remove(participant);		
	}

	/** 
	 * Find a Model Search participant according to its Class
	 * 
	 * @return the list of matching P participants, void list otherwise
	 */
	public List<P> findPartcipant(Class<O> clazz) {
		ArrayList<P> subList = new ArrayList<P>();
		for (P participant : particpants) {
			if (participant.getClass().equals(clazz)) {
				subList.add(participant);
			}
		}
		return subList;
	}

	/**
	 * Get Model Search participants
	 * 
	 * @return the list of P participants, void list otherwise
	 */
	public List<P> getParticipants() {
		return particpants;
	}
	
	/**
	 * Add a Model Search participant participants
	 */
	public void addParticipants(P[] participants) {
		particpants.addAll(Arrays.asList(participants))	;	
	}

	/**
	 * Remove a Model Search participant participants
	 */
	public void removeParticipants(P[] participants) {
		particpants.removeAll(Arrays.asList(participants))	;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return kindLabel;
	}
}
