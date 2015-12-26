/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EngineMappingDescriptor.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.ui.l10n.Messages;

public final class EngineMappingDescriptor {
	public final static String ENGINE_MAPPING_ID = "id"; //$NON-NLS-1$
	public final static String QUERY_TAB_ID = "queryTabID"; //$NON-NLS-1$
	public final static String PARTICIPANTS_TAB_ID = "participantsTabID"; //$NON-NLS-1$
	public final static String SEARCH_ENGINE_ID = "engineID"; //$NON-NLS-1$
	
	public String ID;
	public String label;
	public String queryTabID;
	public String paticipantsTabID;
	public String searchEngineID;
	
	private IConfigurationElement configElement;
	
	public EngineMappingDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		ID = configElement.getAttribute(ENGINE_MAPPING_ID);
		queryTabID = configElement.getAttribute(QUERY_TAB_ID);
		paticipantsTabID = configElement.getAttribute(PARTICIPANTS_TAB_ID);
		searchEngineID = configElement.getAttribute(SEARCH_ENGINE_ID);
		
        // Sanity check.
        if (ID == null
        		|| queryTabID == null
        		|| paticipantsTabID == null
        		|| searchEngineID == null
                ) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("ParticipantTabDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	public String getID() {
		return ID;
	}
	public String getQueryTabID() {
		return queryTabID;
	}
	public String getPaticipantsTabID() {
		return paticipantsTabID;
	}
	public String getSearchEngineID() {
		return searchEngineID;
	}
}
