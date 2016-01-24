/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchParticipantArea.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - UI refactor, Target Meta Elements listener support
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.areas;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.providers.EcoreMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchParticipantArea;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * Provide a list of all Ecore meta elements particpating to the search.
 * </p>
 * <p>
 * Allows user to select into which meta-element the search query will look into.
 * </p>
 * 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public final class EcoreModelSearchParticipantArea extends AbstractModelSearchParticipantArea {
	public EcoreModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style) {
		super(parent, page, style);
		createContent();
	}
	public List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

		composeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());

		return composeableAdapterFactories;
	}
	@Override
	public AbstractMetaModelParticipantsItemProvider getMetaModelParticipantsItemProvider() {
		return new EcoreMetaModelParticipantsItemProvider();
	}
	public Collection<String> getTargetMetaModelIDs() {
		return Arrays.asList(new String[] { 
			EcorePackage.eINSTANCE.getNsURI() 
		});
	}
	@Override
	protected String getTargetModelElementText(Object object) {
		return object instanceof ENamedElement ? ((ENamedElement)object).getName() : null;
	}
	@Override
	protected Image getTargetModelElementImage(Object object) {
		try {
			if (object instanceof ENamedElement) {
				String imagePath = "/icons/full/obj16/" + ((ENamedElement)object).getName() + ".gif"; //$NON-NLS-1$  //$NON-NLS-2$
				URL url = FileLocator.find(EcoreEditPlugin.getPlugin().getBundle(), new Path(imagePath), null);
				if (url != null) {
					return ModelSearchImagesUtil.getImage(url);
				}
			}
		} catch (Throwable t) {
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, Messages.getString("EcoreModelSearchParticipantArea.ImageRetrievalError1") + EcoreEditPlugin.getPlugin().getBundle() + Messages.getString("EcoreModelSearchParticipantArea.ImageRetrievalError1"))); //$NON-NLS-1$ //$NON-NLS-2$
		} 
		return null;
	}
}
