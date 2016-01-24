/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchParticipantArea.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.ui.areas;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelEditPlugin;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelItemProviderAdapterFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.emf.search.genmodel.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.genmodel.ui.providers.GenModelMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchParticipantArea;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public final class GenModelSearchParticipantArea extends AbstractModelSearchParticipantArea {
	public GenModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style) {
		super(parent, page, style);
		createContent();
	}
	
	@Override
	protected boolean getDefaultParticpantsControlEnabling() {
		return false;
	}
	public List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

		composeableAdapterFactories.add(new GenModelItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());
		
		return composeableAdapterFactories;
	}
	@Override
	public AbstractMetaModelParticipantsItemProvider getMetaModelParticipantsItemProvider() {
		return new GenModelMetaModelParticipantsItemProvider();
	}
	public Collection<String> getTargetMetaModelIDs() {
		return Arrays.asList(new String[] { GenModelPackage.eINSTANCE.getNsURI() });
	}
	public EPackage getEPackage() {
		return GenModelPackage.eINSTANCE;
	}
	@Override
	protected String getTargetModelElementText(Object object) {
		return object instanceof GenBase ? ((ENamedElement)((GenBase)object).getEcoreModelElement()).getName() : null;
	}
	@Override
	protected Image getTargetModelElementImage(Object object) {
		try {
			if (object instanceof ENamedElement) {
				String imagePath = "/icons/full/obj16/E" + ((ENamedElement)object).getName().substring(3) + ".gif"; //$NON-NLS-1$  //$NON-NLS-2$
				URL url = FileLocator.find(EcoreEditPlugin.getPlugin().getBundle(), new Path(imagePath), null);
				if (url != null) {
					return ModelSearchImagesUtil.getImage(url);
				}
			}
		} catch (Throwable t) {
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Error while attempmting to retrieve image from edit" + GenModelEditPlugin.getPlugin().getBundle() + " bundle")); //$NON-NLS-1$  //$NON-NLS-2$
		} 
		return null;
	}
}
