/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchParticipantTabExtensionManager.java
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

/**
 * Listen to extension point registry to handle modelSearchParticipantTab contribution lifcycle.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchParticipantTabExtensionManager extends AbstractExtensionManager {
	private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
	public final static String EMF_UI_MODEL_SEARCH_PARTICIPANT_TABS_EXT_POINT_ID = bundleId + ".modelSearchParticipantTab"; //$NON-NLS-1$
	
	
	
	/** the shared instance */
	private static ModelSearchParticipantTabExtensionManager manager;

	/**
	 * A set that will only ever contain AbstractModelSearchParticipantArea.
	 */
	private SortedSet<ParticipantTabDescriptor> participantContributions = new TreeSet<ParticipantTabDescriptor>(new Comparator<ParticipantTabDescriptor>() {
		public int compare(ParticipantTabDescriptor o1, ParticipantTabDescriptor o2) {
			return o1.getID().compareTo(o2.getID());
		}
	});

	
	protected ModelSearchParticipantTabExtensionManager() {
		super(EMF_UI_MODEL_SEARCH_PARTICIPANT_TABS_EXT_POINT_ID);
		readRegistry();
	}

	/**
	 * Get the shared instance.
	 * 
	 * @return the validators manager
	 */
	public static ModelSearchParticipantTabExtensionManager getInstance() {
		return (manager == null)?manager = new ModelSearchParticipantTabExtensionManager():manager;
	}

	/**
	 * Find a descriptor in the registry.
	 * @param id the searched validator id
	 * @return the validator or <code>null</code> if not found
	 */
	public ParticipantTabDescriptor find(String id) {
		for (ParticipantTabDescriptor desc : participantContributions) {
			if (id.equals(desc.getID())) {
				return desc;
			}
		}
		return participantContributions.first();
	}

	/**
	 * Get an enumeration of validator descriptors.
	 * @return The registered validators
	 */
	public ParticipantTabDescriptor[] getModelSearchParticipantDescriptors() {
		return (ParticipantTabDescriptor[]) participantContributions.toArray(new ParticipantTabDescriptor[participantContributions.size()]);
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void addExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			try {
				participantContributions.add(new ParticipantTabDescriptor(confElt));
			} catch (CoreException ce) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, bundleId, 0, Messages.getString("ModelExtensibleSearchParticipantTabExtensionManager.1"), ce)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#removeExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void removeExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			String id = confElt.getAttribute(ParticipantTabDescriptor.PARTICIPANT_AREA_ID);
			ParticipantTabDescriptor descriptor = find(id);
			participantContributions.remove(descriptor);
		}
	}
}
