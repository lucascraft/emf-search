/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * QueryTabDescriptor.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchCompositeArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchArea;
import org.eclipse.papyrus.infra.emf.search.ui.areas.IModelSearchAreaFactory;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

/**
 * Encapsulates data comming from modelSearchQueryTab an extension point contribution.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class QueryTabDescriptor {
	public final static String QUERY_AREA_ID = "id"; //$NON-NLS-1$
	public final static String QUERY_AREA_LABEL_ID = "label"; //$NON-NLS-1$
	public final static String QUERY_AREA_TOOLTIP_ID = "tooltip"; //$NON-NLS-1$
	public final static String QUERY_IDX_ID = "index"; //$NON-NLS-1$
	public final static String QUERY_TARGET_SEARCH_PAGE_ID = "targetSearchPageID";  //$NON-NLS-1$
	public final static String QUERY_COMPOSITE_AREA_CLASS = "queryCompositeArea"; //$NON-NLS-1$
	public final static String QUERY_COMPOSITE_AREA_FACTORY_CLASS = "queryCompositeAreaFactory"; //$NON-NLS-1$
	public final static String QUERY_IMAGE_ID = "image"; //$NON-NLS-1$
	
	public String ID;
	public String label;
	public String tooltip;
	public int idx;
	public String targetSearchPageID;
	public IModelSearchArea queryModelSearchArea;
	public IModelSearchAreaFactory queryCompositeAreaFactory;
	public Image image;

	private IConfigurationElement configElement;
	
	public QueryTabDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}

	private void load()throws CoreException {
		Bundle bundle = Platform.getBundle(configElement.getNamespaceIdentifier());

		ID = configElement.getAttribute(QUERY_AREA_ID);
		label = configElement.getAttribute(QUERY_AREA_LABEL_ID);
		tooltip = configElement.getAttribute(QUERY_AREA_TOOLTIP_ID);
		targetSearchPageID = configElement.getAttribute(QUERY_TARGET_SEARCH_PAGE_ID);
		queryCompositeAreaFactory = (IModelSearchAreaFactory) configElement.createExecutableExtension(QUERY_COMPOSITE_AREA_FACTORY_CLASS);
		image = ModelSearchImagesUtil.getImageDescriptor(bundle, configElement.getAttribute(QUERY_IMAGE_ID)).createImage();
		
		String indexStringValue = configElement.getAttribute(QUERY_IDX_ID);
		
		try {
			idx = Integer.parseInt(indexStringValue);
		} catch (NumberFormatException e) {
			idx = 0;
		}
		
        // Sanity check.
        if (ID == null
                || queryCompositeAreaFactory == null
                || label == null
                || image == null
                || targetSearchPageID == null
        ) {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString("QueryTabDescriptor.invalidExtensionErrorMessage") + ID, //$NON-NLS-1$
                    null));
        }
	}
	public String getID() {
		return ID;
	}
	public int getIndex() {
		return idx;
	}
	public IModelSearchAreaFactory getQueryCompositeAreaFactory() {
		return queryCompositeAreaFactory;
	}
	public String getLabel() {
		return label;
	}
	public String getTooltip() {
		return tooltip;
	}
	public IModelSearchArea createArea(Composite parent, AbstractModelSearchPage searchPage) {
		queryModelSearchArea = queryCompositeAreaFactory.createArea(parent, searchPage);
		if (queryModelSearchArea instanceof AbstractModelSearchCompositeArea) {
			((AbstractModelSearchCompositeArea)queryModelSearchArea).getDataMap().put("SETTINGS_PREFIX", searchPage.getID());
		}
		return queryModelSearchArea;
	}
	public IModelSearchArea getQueryModelSearchArea() {
		return queryModelSearchArea;
	}
	public Image getImage() {
		return image;
	}
	public String getTargetSearchPageID() {
		return targetSearchPageID;
	}
}
