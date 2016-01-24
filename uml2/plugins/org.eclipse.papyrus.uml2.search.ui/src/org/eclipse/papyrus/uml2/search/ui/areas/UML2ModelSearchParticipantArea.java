/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - Add image support for target model
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ui.areas;

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
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchParticipantArea;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.papyrus.uml2.search.ui.Activator;
import org.eclipse.papyrus.uml2.search.ui.providers.UML2MetaModelParticipantsItemProvider;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

public final class UML2ModelSearchParticipantArea extends AbstractModelSearchParticipantArea {
	public UML2ModelSearchParticipantArea(Composite parent, AbstractModelSearchPage page, int style) {
		super(parent, page, style);
		createContent();
	}
	
	@Override
	protected boolean getDefaultParticpantsControlEnabling() {
		return false;
	}
	public List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();

		composeableAdapterFactories.add(new EcoreItemProviderAdapterFactory());
		composeableAdapterFactories.add(new UMLItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());
		
		return composeableAdapterFactories;
	}
	@Override
	public AbstractMetaModelParticipantsItemProvider getMetaModelParticipantsItemProvider() {
		return new UML2MetaModelParticipantsItemProvider();
	}
	public Collection<String> getTargetMetaModelIDs() {
		return Arrays.asList(new String[] { 
			UMLPackage.eINSTANCE.getNsURI() 
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
				String imagePath = "/icons/full/obj16/" + computeUML2ElementImageName(((ENamedElement)object).getName()) + ".gif"; //$NON-NLS-1$  //$NON-NLS-2$
				URL url = FileLocator.find(UMLEditPlugin.getPlugin().getBundle(), new Path(imagePath), null);
				if (url != null) {
					return ModelSearchImagesUtil.getImage(url);
				}
			}
		} catch (Throwable t) {
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Error while attempmting to retrieve image from edit" + UMLEditPlugin.getPlugin().getBundle() + " bundle")); //$NON-NLS-1$  //$NON-NLS-2$
		} 
		return null;
	}
	private String computeUML2ElementImageName(String name) {
		return name; //TOGO: LB to find an elegant way to compute right name for all icons
	}
}
