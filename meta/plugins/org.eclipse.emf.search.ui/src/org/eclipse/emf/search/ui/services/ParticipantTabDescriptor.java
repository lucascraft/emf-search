/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ParticipantTabDescriptor.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.search.ui.areas.AbstractModelSearchCompositeArea;
import org.eclipse.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.emf.search.ui.handlers.IModelElementEditorSelectionHandler;
import org.eclipse.emf.search.ui.l10n.Messages;
import org.eclipse.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

/**
 * Encapsulates data comming from modelSearchParticipantTab an extension point contribution.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class ParticipantTabDescriptor {
	public final static String PARTICIPANT_AREA_ID = "id"; //$NON-NLS-1$
	public final static String PARTICIPANT_AREA_LABEL_ID = "label"; //$NON-NLS-1$
	public final static String PARTICIPANT_AREA_TOOLTIP_ID = "tooltip"; //$NON-NLS-1$
	public final static String PARTICIPANT_IMAGE_ID = "image"; //$NON-NLS-1$
	public final static String PARTICIPANT_COMPOSITE_AREA_CLASS = "participantModelSearchArea"; //$NON-NLS-1$
	public final static String PARTICIPANT_ELEMENT_EDITOR_SELECTION_HANDLER_CLASS = "elementModelEditorSelectionHandler"; //$NON-NLS-1$
	public final static String PARTICIPANT_ELEMENT_COMPOSEABLE_FACTORY_ADAPTER_CLASS = "elementComposeableAdapterFactory"; //$NON-NLS-1$
	public final static String PARTICIPANT_COMPOSITE_AREA_FACTORY_CLASS = "participantCompositeAreaFactory"; //$NON-NLS-1$
	public final static String PARTICIPANT_TARGET_SEARCH_PAGE_ID = "targetSearchPageID";  //$NON-NLS-1$
	public final static String PARTICIPANT_TARGET_NS_URI = "targetNsURI";  //$NON-NLS-1$
	
	public String ID;
	public String label;
	public String tooltip;
	public String targetSearchPageID;
	public IModelSearchArea participantModelSearchArea;
	public IModelSearchAreaFactory participantCompositeAreaFactory;
	public IModelElementEditorSelectionHandler modelElementEditorSelectionHandler;
	public AdapterFactory elementComposeableAdapterFactory;
	public Image image;
	public String targetNsURI;
	
	private IConfigurationElement configElement;
	
	public ParticipantTabDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		Bundle bundle = Platform.getBundle(configElement.getNamespaceIdentifier());
		
		ID = configElement.getAttribute(PARTICIPANT_AREA_ID);
		label = configElement.getAttribute(PARTICIPANT_AREA_LABEL_ID);
		tooltip = configElement.getAttribute(PARTICIPANT_AREA_TOOLTIP_ID);
		
		targetSearchPageID = configElement.getAttribute(PARTICIPANT_TARGET_SEARCH_PAGE_ID);
		targetNsURI = configElement.getAttribute(PARTICIPANT_TARGET_NS_URI);
		image = ModelSearchImagesUtil.getImageDescriptor(bundle, configElement.getAttribute(PARTICIPANT_IMAGE_ID)).createImage();
		participantCompositeAreaFactory = (IModelSearchAreaFactory) configElement.createExecutableExtension(PARTICIPANT_COMPOSITE_AREA_FACTORY_CLASS);
		elementComposeableAdapterFactory = (AdapterFactory) configElement.createExecutableExtension(PARTICIPANT_ELEMENT_COMPOSEABLE_FACTORY_ADAPTER_CLASS);
		modelElementEditorSelectionHandler = (IModelElementEditorSelectionHandler) configElement.createExecutableExtension(PARTICIPANT_ELEMENT_EDITOR_SELECTION_HANDLER_CLASS);
		
        // Sanity check.
        if (ID == null
                || participantCompositeAreaFactory == null
                || elementComposeableAdapterFactory == null
                || modelElementEditorSelectionHandler == null
                || targetSearchPageID == null
                || label == null
                || image == null
                // reminder : targetNsURI is optional !
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
	public IModelSearchAreaFactory getQueryCompositeAreaFactory() {
		return participantCompositeAreaFactory;
	}
	public String getLabel() {
		return label;
	}
	public String getTooltip() {
		return tooltip;
	}
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		if (targetNsURI != null && !"".equals(targetNsURI)) {
			participantModelSearchArea = participantCompositeAreaFactory.createArea(parent, searchPage, targetNsURI);
		} else {
			participantModelSearchArea = participantCompositeAreaFactory.createArea(parent, searchPage);
		}
		if (participantModelSearchArea instanceof AbstractModelSearchCompositeArea) {
			((AbstractModelSearchCompositeArea) participantModelSearchArea).getDataMap().put("SETTINGS_PREFIX", searchPage.getID());
		}
		return participantModelSearchArea;
	}
	public IModelSearchArea getParticipantModelSearchArea() {
		return participantModelSearchArea;
	}
	public AdapterFactory getElementComposeableAdapterFactory() {
		return elementComposeableAdapterFactory; 
	}
	public IModelElementEditorSelectionHandler getModelElementEditorSelectionHandler() {
		return modelElementEditorSelectionHandler;
	}
	public Image getImage() {
		return image;
	}
	public String getTargetSearchPageID() {
		return targetSearchPageID;
	}
}
