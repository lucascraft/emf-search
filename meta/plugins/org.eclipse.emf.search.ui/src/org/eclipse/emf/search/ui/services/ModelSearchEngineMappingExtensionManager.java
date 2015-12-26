/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchEngineMappingExtensionManager.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.services;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.core.services.AbstractExtensionManager;
import org.eclipse.emf.search.ui.Activator;
import org.eclipse.emf.search.ui.l10n.Messages;

public final class ModelSearchEngineMappingExtensionManager extends AbstractExtensionManager {
	private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
	public final static String ECORE_UI_MODEL_SEARCH_ENGINE_MAPPING_EXT_POINT_ID = bundleId + ".modelSearchEngineMapping"; //$NON-NLS-1$
	
	/** the shared instance */
	private static ModelSearchEngineMappingExtensionManager manager;

	/**
	 * A set that will only ever contain AbstractModelSearchParticipantArea.
	 */
	private SortedSet<EngineMappingDescriptor> engineMappingContributions = new TreeSet<EngineMappingDescriptor>(new Comparator<EngineMappingDescriptor>() {
		public int compare(EngineMappingDescriptor o1, EngineMappingDescriptor o2) {
			return o1.getID().compareTo(o2.getID());
		}
	});

	
	protected ModelSearchEngineMappingExtensionManager() {
		super(ECORE_UI_MODEL_SEARCH_ENGINE_MAPPING_EXT_POINT_ID);
		readRegistry();
	}

	/**
	 * Get the shared instance.
	 * 
	 * @return the mapping manager
	 */
	public static ModelSearchEngineMappingExtensionManager getInstance() {
		return (manager == null) ? manager = new ModelSearchEngineMappingExtensionManager() : manager;
	}

	/**
	 * Find a descriptor in the registry.
	 * @param id the searched mapping id
	 * @return the mapping or <code>null</code> if not found
	 */
	public EngineMappingDescriptor find(String id) {
		for (EngineMappingDescriptor desc : engineMappingContributions) {
			if (id.equals(desc.getID())) {
				return desc;
			}
		}
		return null;
	}

	/**
	 * Get an enumeration of mapping descriptors.
	 * @return The registered mapping
	 */
	public EngineMappingDescriptor[] getModelSearchEngineMappingDescriptors() {
		return (EngineMappingDescriptor[]) engineMappingContributions.toArray(new EngineMappingDescriptor[engineMappingContributions.size()]);
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void addExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			try {
				engineMappingContributions.add(new EngineMappingDescriptor(confElt));
			} catch (CoreException ce) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, bundleId, 0, Messages.getString("ModelSearchEngigneMappingTabExtensionManager.1"), ce)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#removeExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void removeExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			String id = confElt.getAttribute(EngineMappingDescriptor.ENGINE_MAPPING_ID);
			EngineMappingDescriptor descriptor = find(id);
			engineMappingContributions.remove(descriptor);
		}
	}
}
