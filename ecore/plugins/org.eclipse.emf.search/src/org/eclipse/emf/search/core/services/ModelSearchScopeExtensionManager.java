/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelExtensibleSearchEngineExtensionManager.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.services;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.Activator;
import org.eclipse.emf.search.l10n.Messages;

/**
 * This convenience class contains handles data coming from modelSearchEngine extension point contributions.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchScopeExtensionManager extends AbstractExtensionManager {
		private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
		
		public final static String MODELING_CORE_MODEL_SEARCH_SCOPE_EXT_POINT_ID = bundleId + ".modelScopeFactory"; //$NON-NLS-1$

		/** the shared instance */
		private static ModelSearchScopeExtensionManager manager;

		/**
		 * A set that will only ever contain AbstractModelSearchParticipantArea.
		 */
		private SortedSet<ModelSearchScopeDescriptor> searchScopeContributions = new TreeSet<ModelSearchScopeDescriptor>(new Comparator<ModelSearchScopeDescriptor>() {
			public int compare(ModelSearchScopeDescriptor o1, ModelSearchScopeDescriptor o2) {
				return o1.getID().compareTo(o2.getID());
			}
		});

		protected ModelSearchScopeExtensionManager() {
			super(MODELING_CORE_MODEL_SEARCH_SCOPE_EXT_POINT_ID);
			readRegistry();
		}

		/**
		 * Get the shared instance.
		 * 
		 * @return the validators manager
		 */
		public static ModelSearchScopeExtensionManager getInstance() {
			return (manager == null)?manager = new ModelSearchScopeExtensionManager():manager;
		}

		/**
		 * Find a descriptor in the registry.
		 * @param id the searched validator id
		 * @return the validator or <code>null</code> if not found
		 */
		public ModelSearchScopeDescriptor find(String id) {
			for (ModelSearchScopeDescriptor desc : searchScopeContributions) {
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
		public ModelSearchScopeDescriptor[] getModelSearchScopes() {
			return (ModelSearchScopeDescriptor[]) searchScopeContributions.toArray(new ModelSearchScopeDescriptor[searchScopeContributions.size()]);
		}

		/**
		 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
		 */
		protected void addExtension(IExtension extension) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement confElt = elements[i];
				try {
					searchScopeContributions.add(new ModelSearchScopeDescriptor(confElt));
				} catch (CoreException ce) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, bundleId, 0, Messages.getString("ModelExtensibleSearchEngineExtensionManager.addExtensionErrorMessage"), ce)); //$NON-NLS-1$
				}
			}
		}

		/**
		 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#removeExtension(org.eclipse.core.runtime.IExtension)
		 */
		protected void removeExtension(IExtension extension) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement confElt = elements[i];
				String id = confElt.getAttribute(ModelSearchScopeDescriptor.MODEL_SEARCH_ENGINE_ID);
				ModelSearchScopeDescriptor descriptor = find(id);
				searchScopeContributions.remove(descriptor);
			}
		}
}
