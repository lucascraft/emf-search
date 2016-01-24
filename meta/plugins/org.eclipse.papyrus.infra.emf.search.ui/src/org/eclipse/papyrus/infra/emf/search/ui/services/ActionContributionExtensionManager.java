/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ActionContributionExtensionManager.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.services;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.core.services.AbstractExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;

public class ActionContributionExtensionManager extends AbstractExtensionManager {
	private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
	public final static String MODEL_SEARCH_PAGE_ACTION_CONTRIBUTION_EXT_POINT_ID = bundleId + ".modelSearchPageActionContribution"; //$NON-NLS-1$

	/** the shared instance */
	private static ActionContributionExtensionManager manager;

	/**
	 * A set that will only ever contain ActionContributionDescriptor.
	 */
	private SortedSet<ActionContributionDescriptor> actionContributions = new TreeSet<ActionContributionDescriptor>(new Comparator<ActionContributionDescriptor>() {
		public int compare(ActionContributionDescriptor o1, ActionContributionDescriptor o2) {
			return o2.getID().compareTo(o1.getID());
		}
	});

	
	protected ActionContributionExtensionManager() {
		super(MODEL_SEARCH_PAGE_ACTION_CONTRIBUTION_EXT_POINT_ID);
		readRegistry();
	}

	/**
	 * Get the shared instance.
	 * 
	 * @return the validators manager
	 */
	public static ActionContributionExtensionManager getInstance() {
		return (manager == null) ? manager = new ActionContributionExtensionManager() : manager;
	}

	/**
	 * Find a descriptor in the registry.
	 * @param id the searched validator id
	 * @return the validator or <code>null</code> if not found
	 */
	public ActionContributionDescriptor find(String id) {
		for (ActionContributionDescriptor desc : actionContributions) {
			if (id.equals(desc.getID())) {
				return desc;
			}
		}
		return null;
	}

	/**
	 * Get an enumeration of validator descriptors.
	 * @return The registered validators
	 */
	public ActionContributionDescriptor[] getActionContributions() {
		return (ActionContributionDescriptor[]) actionContributions.toArray(new ActionContributionDescriptor[actionContributions.size()]);
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void addExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			try {
				actionContributions.add(new ActionContributionDescriptor(confElt));
			} catch (CoreException ce) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, bundleId, 0, Messages.getString("ActionContributionDescriptorExtensionManager.addExtensionErrorMessage"), ce)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#removeExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void removeExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			String id = confElt.getAttribute(ActionContributionDescriptor.ACTION_ID);
			ActionContributionDescriptor descriptor = find(id);
			actionContributions.remove(descriptor);
		}
	}
}
