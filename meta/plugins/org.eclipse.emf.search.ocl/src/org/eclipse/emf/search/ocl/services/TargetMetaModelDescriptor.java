/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TargetMetaModelDescriptor.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ocl.services;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.ocl.l10n.Messages;

/**
 * Data coming from each targetMetaModel extension point contribution is stored in this class.
 * 
 * It allows user to cache data coming from extension point registry once and keep it at runtime time in a single place.
 *  
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class TargetMetaModelDescriptor {
	public final static String TARGET_METAMODEL_ID = "nsURI"; //$NON-NLS-1$
	public final static String TARGET_METAMODEL_LEVEL_ID = "level"; //$NON-NLS-1$
	public final static String TARGET_METAMODEL_TARGET_ID = "target"; //$NON-NLS-1$
	
	private String nsURI;
	private String level;
	private String target;

	private IConfigurationElement configElement;

	public TargetMetaModelDescriptor(IConfigurationElement element) throws CoreException {
		super();
		configElement = element;
		load();
	}
	private void load()throws CoreException {
		nsURI = configElement.getAttribute(TARGET_METAMODEL_ID);
		level = configElement.getAttribute(TARGET_METAMODEL_LEVEL_ID);
		target = configElement.getAttribute(TARGET_METAMODEL_TARGET_ID);
		
        // Sanity check.
        if (nsURI == null 
        		|| level == null
        		|| target == null
                ) 
        {
            throw new CoreException(new Status(IStatus.ERROR, configElement
                    .getContributor().getName(), IStatus.OK,
                    Messages.getString(Messages.getString("TargetMetaModelDescriptor.invalidExtensionErrorMessage")) + nsURI, //$NON-NLS-1$
                    null));
        }
	}
	public String getNsURI() {
		return nsURI;
	}
	public String getLevel() {
		return level;
	}
	public String getTarget() {
		return target;
	}
}
