/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ActionContributionDescriptor.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.ui.l10n.Messages;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public class ActionContributionDescriptor {
	public final static String ACTION_CONTRIBUTION_EXT_ID = "id"; //$NON-NLS-1$
	public final static String ACTION_ID = "action"; //$NON-NLS-1$
	public final static String TARGET_MENU_ID = "targetMenuID"; //$NON-NLS-1$
	public final static String TARGET_META_MODEL_ID = "targetMetaModelID"; //$NON-NLS-1$
	public final static String ACTION_ICON_ID = "icon"; //$NON-NLS-1$

	public String ID;
	public Action action;
	public String targetMenuID;
	public String targetMetaModelID;
	public ImageDescriptor icon;
	
	private IConfigurationElement configElement;
	
	public ActionContributionDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		Bundle bundle = Platform.getBundle(configElement.getNamespaceIdentifier());

		ID = configElement.getAttribute(ACTION_CONTRIBUTION_EXT_ID);
		action = (Action) configElement.createExecutableExtension(ACTION_ID);
		action.setId(ID);
		targetMenuID = configElement.getAttribute(TARGET_MENU_ID);
		targetMetaModelID = configElement.getAttribute(TARGET_META_MODEL_ID);
		
		String imagePath = configElement.getAttribute(ACTION_ICON_ID);
		if (imagePath != null && !imagePath.equals("")) { //$NON-NLS-1$
			icon = ModelSearchImagesUtil.getImageDescriptor(bundle, imagePath);
		} else {
			icon = ModelSearchImagesUtil.MISSING_IMG_DESC;
		}
		
        // Sanity check.
        if (ID == null || ID.equals("") //$NON-NLS-1$
        		|| action == null 
        		|| targetMenuID == null || targetMenuID.equals("") //$NON-NLS-1$
        		|| targetMetaModelID == null || targetMetaModelID.equals("") //$NON-NLS-1$
                ) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("ActionContributionDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	
	public String getID() {
		return ID;
	}
	public Action getAction() {
		return action;
	}
	public String getTargetMenuID() {
		return targetMenuID;
	}
	public String getTargetMetaModelID() {
		return targetMetaModelID;
	}
	public ImageDescriptor getIcon() {
		return icon;
	}
}
