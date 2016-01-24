/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * MenuContributionDescriptor.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public class MenuContributionDescriptor {
	public final static String MENU_CONTRIBUTION_EXT_ID = "id"; //$NON-NLS-1$
	public final static String MENU_ID = "id"; //$NON-NLS-1$
	public final static String MENU_LABEL_ID = "label"; //$NON-NLS-1$
	public final static String MENU_ORDER_ID = "order"; //$NON-NLS-1$
	public final static String MENU_IMAGE_ID = "image"; //$NON-NLS-1$
	public final static String TARGET_META_MODEL_ID = "targetMetaModelID"; //$NON-NLS-1$
	
	public String ID;
	public String label;
	public String order;
	public ImageDescriptor imageDescriptor; 
	public String targetMetaModelID;
	
	private IConfigurationElement configElement;
	
	public MenuContributionDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		Bundle bundle = Platform.getBundle(configElement.getNamespaceIdentifier());

		ID = configElement.getAttribute(MENU_ID);
		label = configElement.getAttribute(MENU_LABEL_ID);
		order = configElement.getAttribute(MENU_ORDER_ID);
		
		String imagePath = configElement.getAttribute(MENU_IMAGE_ID);
		
		imageDescriptor = ModelSearchImagesUtil.getImageDescriptor(bundle, imagePath);

		targetMetaModelID = configElement.getAttribute(TARGET_META_MODEL_ID);
		
        // Sanity check.
        if (ID == null || ID.equals("") //$NON-NLS-1$
        		|| label == null || label.equals("") //$NON-NLS-1$
        		|| order == null || order.equals("") //$NON-NLS-1$
        		|| imageDescriptor == null
        		|| targetMetaModelID == null || targetMetaModelID.equals("") //$NON-NLS-1$
                ) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("MenuContributionDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	
	public String getID() {
		return ID;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getOrder() {
		return order;
	}
	
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
	
	public String getTargetMetaModelID() {
		return targetMetaModelID;
	}
}
