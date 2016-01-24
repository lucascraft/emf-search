/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TargetMetaModelExtensionManager.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ocl.services;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.core.services.AbstractExtensionManager;
import org.eclipse.papyrus.infra.emf.search.ocl.Activator;
import org.eclipse.papyrus.infra.emf.search.ocl.l10n.Messages;

/**
 * This convenience class contains handles data coming from targetMetaModel extension point contributions.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class TargetMetaModelExtensionManager extends AbstractExtensionManager {
		private final static String bundleId = Activator.getDefault().getBundle().getSymbolicName();
		
		public final static String MODELING_TARGET_METAMODEL_EXT_POINT_ID = bundleId + ".targetMetaModel"; //$NON-NLS-1$

		/** the shared instance */
		private static TargetMetaModelExtensionManager manager;

		/**
		 * A set that will only ever contain AbstractModelSearchParticipantArea.
		 */
		private SortedSet<TargetMetaModelDescriptor> searchEngineContributions = new TreeSet<TargetMetaModelDescriptor>(new Comparator<TargetMetaModelDescriptor>() {
			public int compare(TargetMetaModelDescriptor o1, TargetMetaModelDescriptor o2) {
				return o1.getNsURI().compareTo(o2.getNsURI());
			}
		});

		protected TargetMetaModelExtensionManager() {
			super(MODELING_TARGET_METAMODEL_EXT_POINT_ID);
			readRegistry();
		}

		/**
		 * Get the shared instance.
		 * 
		 * @return the validators manager
		 */
		public static TargetMetaModelExtensionManager getInstance() {
			return (manager == null)?manager = new TargetMetaModelExtensionManager():manager;
		}

		/**
		 * Find a descriptor in the registry.
		 * @param id the searched validator id
		 * @return the validator or <code>null</code> if not found
		 */
		public TargetMetaModelDescriptor find(String id) {
			for (TargetMetaModelDescriptor desc : searchEngineContributions) {
				if (id.equals(desc.getNsURI())) {
					return desc;
				}
			}
			return null;
		}

		/**
		 * Get an enumeration of validator descriptors.
		 * @return The registered validators
		 */
		public TargetMetaModelDescriptor[] getModelSearchEngines() {
			return (TargetMetaModelDescriptor[]) searchEngineContributions.toArray(new TargetMetaModelDescriptor[searchEngineContributions.size()]);
		}

		/**
		 * @see org.eclipse.emf.facilities.extensions.AbstractExtensionManager#addExtension(org.eclipse.core.runtime.IExtension)
		 */
		protected void addExtension(IExtension extension) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement confElt = elements[i];
				try {
					searchEngineContributions.add(new TargetMetaModelDescriptor(confElt));
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
				String id = confElt.getAttribute(TargetMetaModelDescriptor.TARGET_METAMODEL_ID);
				TargetMetaModelDescriptor descriptor = find(id);
				searchEngineContributions.remove(descriptor);
			}
		}

}
