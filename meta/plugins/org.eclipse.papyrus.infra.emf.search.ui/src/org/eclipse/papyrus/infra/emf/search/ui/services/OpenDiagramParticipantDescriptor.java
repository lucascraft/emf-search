/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenDiagramParticipantDescriptor.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.handlers.IOpenDiagramHandler;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;

/**
 * Encapsulates data coming from modelSearchParticipantTab an extension point contribution.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class OpenDiagramParticipantDescriptor {
	public final static String OPEN_DIAGRAM_PARTICIPANT_ID = "id"; //$NON-NLS-1$
	public final static String OPEN_DIAGRAM_PARTICIPANT_HANDLER = "openDiagramHandler"; //$NON-NLS-1$
	
	private String ID;
	private IOpenDiagramHandler openDiagramHandler;
	
	private IConfigurationElement configElement;
	
	public OpenDiagramParticipantDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		ID = configElement.getAttribute(OPEN_DIAGRAM_PARTICIPANT_ID);
		openDiagramHandler = (IOpenDiagramHandler)configElement.createExecutableExtension(OPEN_DIAGRAM_PARTICIPANT_HANDLER);

        // Sanity check.
        if (ID == null
            && openDiagramHandler == null
        	) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("OpenDiagramParticipantTabDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	public String getID() {
		return ID;
	}
	public IOpenDiagramHandler getOpenDiagramHandler() {
		return openDiagramHandler;
	}
}
