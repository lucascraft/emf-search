/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchImages.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.utils;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * A class defining a bundle of all images used by the System Editor plugin.
 * 
 * @author Sébastien Moran : <a
 *         href="mailto:sebastien.moran@anyware-tech.com">sebastien.moran@anyware-tech.com
 *         </a>
 * @author Lucas Bigeardel : <a
 *         href="mailto:lucas.bigeardel@anyware-tech.com">lucas.bigeardel@gmail.com
 *         </a>
 *         
 */
public final class ModelSearchImages {
   public final static String ICONS_PATH = "icons/full/"; //$NON-NLS-1$

    public final static String ERROR_IMAGE_PATH = ICONS_PATH + "error_log.gif"; //$NON-NLS-1$
    static Image ERROR_IMAGE = null;
    
    public final static String FILE_IMAGE_PATH = ICONS_PATH + "esearch.gif"; //$NON-NLS-1$
    static Image FILE_IMAGE = null;
    
    public final static String OPEN_DIAGRAM_IMAGE_PATH = ICONS_PATH + "EcoreDiagramFile.gif"; //$NON-NLS-1$
    static Image OPEN_DIAGRAM_IMAGE = null;

    
    public static Image getErrorImage() {
    	Bundle bundle = Platform.getBundle(org.eclipse.papyrus.infra.emf.search.ui.Activator.getDefault().getBundle().getSymbolicName());
    	return ERROR_IMAGE==null?ERROR_IMAGE=ModelSearchImagesUtil.getImageDescriptor(bundle, ERROR_IMAGE_PATH).createImage():ERROR_IMAGE;
    }
    public static Image getFileImage() {
    	Bundle bundle = Platform.getBundle(org.eclipse.papyrus.infra.emf.search.ui.Activator.getDefault().getBundle().getSymbolicName());
    	return FILE_IMAGE==null?FILE_IMAGE=ModelSearchImagesUtil.getImageDescriptor(bundle, FILE_IMAGE_PATH).createImage():FILE_IMAGE;
    }
    public static Image getOpenDiagramImage() {
    	Bundle bundle = Platform.getBundle(org.eclipse.papyrus.infra.emf.search.ui.Activator.getDefault().getBundle().getSymbolicName());
    	return OPEN_DIAGRAM_IMAGE_PATH==null?OPEN_DIAGRAM_IMAGE=ModelSearchImagesUtil.getImageDescriptor(bundle, OPEN_DIAGRAM_IMAGE_PATH).createImage():OPEN_DIAGRAM_IMAGE;
    }
}