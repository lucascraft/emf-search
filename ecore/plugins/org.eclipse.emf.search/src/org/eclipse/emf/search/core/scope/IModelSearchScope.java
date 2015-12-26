/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchScope.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.core.scope;

import java.util.List;

/**
 * Model scope handling contract.
 * 
 * Scopes are updated by a visitor while potential model resource are identified/accepted.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.1.0
 *
 * @param <P> participant resource
 * @param <O> object type contained in resources
 */
public interface IModelSearchScope<P, O> {
	/**
	 * Getter for collected participant resources
	 * 
	 * @return collected participant resources
	 */
	List<P> getParticipants();
	
	/**
	 * Add collected participant resource
	 * 
	 * @param resource collected participant resource
	 */
	void addParticipant(P resource);
	
	/**
	 * Add collected participant resources
	 * 
	 * @param resource collected participant resources array
	 */
	void addParticipants(P[] resources);
	
	/**
	 * Remove collected participant resource
	 * 
	 * @param resource collected participant resource to remove
	 */
	void removeParticipant(P resource);
	
	
	/**
	 * Remove collected participant resources
	 * 
	 * @param resource collected participant resources to remove
	 */
	void removeParticipants(P[] resources);
	
	/**
	 * Find participant resource from given discriminating class
	 * 
	 * @param clazz given class participant resources to be collected from
	 * 
	 * @return collected participant resources list
	 */
	List<P> findPartcipant(Class<O> clazz);
	
	/**
	 * Getter for label to be display in UI for client model search query
	 * 
	 * @return scope label to be display in UI
	 */
	String getLabel();
}
