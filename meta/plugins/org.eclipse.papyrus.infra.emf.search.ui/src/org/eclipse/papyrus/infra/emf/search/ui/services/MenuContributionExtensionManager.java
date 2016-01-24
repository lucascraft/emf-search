/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * MenuContributionExtensionManager.java
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

public class MenuContributionExtensionManager extends AbstractExtensionManager {
	private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
	public final static String MODEL_SEARCH_PAGE_MENU_CONTRIBUTION_EXT_POINT_ID = bundleId + ".modelSearchPageMenuContribution"; //$NON-NLS-1$

	/** the shared instance */
	private static MenuContributionExtensionManager manager;

	/**
	 * A set that will only ever contain AbstractModelSearchQueryArea.
	 */
	private SortedSet<MenuContributionDescriptor> menuContributions = new TreeSet<MenuContributionDescriptor>(new Comparator<MenuContributionDescriptor>() {
		public int compare(MenuContributionDescriptor o1, MenuContributionDescriptor o2) {
			return o2.getID().compareTo(o1.getID());
		}
	});

	
	protected MenuContributionExtensionManager() {
		super(MODEL_SEARCH_PAGE_MENU_CONTRIBUTION_EXT_POINT_ID);
		readRegistry();
	}

	/**
	 * Get the shared instance.
	 * 
	 * @return the validators manager
	 */
	public static MenuContributionExtensionManager getInstance() {
		return (manager == null) ? manager = new MenuContributionExtensionManager() : manager;
	}

	/**
	 * Find a descriptor in the registry.
	 * @param id the searched validator id
	 * @return the validator or <code>null</code> if not found
	 */
	public MenuContributionDescriptor find(String id) {
		for (MenuContributionDescriptor desc : menuContributions) {
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
	public MenuContributionDescriptor[] getMenuContributions() {
		return (MenuContributionDescriptor[]) menuContributions.toArray(new MenuContributionDescriptor[menuContributions.size()]);
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void addExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			try {
				menuContributions.add(new MenuContributionDescriptor(confElt));
			} catch (CoreException ce) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, bundleId, 0, Messages.getString("ModelExtensibleSearchQueryTabExtensionManager.addExtensionErrorMessage"), ce)); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#removeExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void removeExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			String id = confElt.getAttribute(MenuContributionDescriptor.MENU_CONTRIBUTION_EXT_ID);
			MenuContributionDescriptor descriptor = find(id);
			menuContributions.remove(descriptor);
		}
	}

}
