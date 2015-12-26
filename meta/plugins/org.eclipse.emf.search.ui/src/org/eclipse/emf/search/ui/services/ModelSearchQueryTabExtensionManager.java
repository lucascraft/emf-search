/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchQueryTabExtensionManager.java
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
 * Listen to extension point registry to handle modelSearchQueryTab contribution lifcycle.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchQueryTabExtensionManager extends AbstractExtensionManager {
	private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
	public final static String EMF_UI_MODEL_SEARCH_QUERY_TABS_EXT_POINT_ID = bundleId + ".modelSearchQueryTab"; //$NON-NLS-1$

	/** the shared instance */
	private static ModelSearchQueryTabExtensionManager manager;

	/**
	 * A set that will only ever contain AbstractModelSearchQueryArea.
	 */
	private SortedSet<QueryTabDescriptor> queryContributions = new TreeSet<QueryTabDescriptor>(new Comparator<QueryTabDescriptor>() {
		public int compare(QueryTabDescriptor o1, QueryTabDescriptor o2) {
			int c = o2.getIndex() - o1.getIndex();
			return c == 0 ? o2.getID().compareTo(o1.getID()) : c;
		}
	});

	
	protected ModelSearchQueryTabExtensionManager() {
		super(EMF_UI_MODEL_SEARCH_QUERY_TABS_EXT_POINT_ID);
		readRegistry();
	}

	/**
	 * Get the shared instance.
	 * 
	 * @return the validators manager
	 */
	public static ModelSearchQueryTabExtensionManager getInstance() {
		return (manager == null) ? manager = new ModelSearchQueryTabExtensionManager() : manager;
	}

	/**
	 * Find a descriptor in the registry.
	 * @param id the searched validator id
	 * @return the validator or <code>null</code> if not found
	 */
	public QueryTabDescriptor find(String id) {
		for (QueryTabDescriptor desc : queryContributions) {
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
	public QueryTabDescriptor[] getModelSearchQueryAreas() {
		return (QueryTabDescriptor[]) queryContributions.toArray(new QueryTabDescriptor[queryContributions.size()]);
	}

	/**
	 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
	 */
	protected void addExtension(IExtension extension) {
		for (IConfigurationElement confElt : extension.getConfigurationElements()) {
			try {
				queryContributions.add(new QueryTabDescriptor(confElt));
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
			String id = confElt.getAttribute(QueryTabDescriptor.QUERY_AREA_ID);
			QueryTabDescriptor descriptor = find(id);
			queryContributions.remove(descriptor);
		}
	}
}
