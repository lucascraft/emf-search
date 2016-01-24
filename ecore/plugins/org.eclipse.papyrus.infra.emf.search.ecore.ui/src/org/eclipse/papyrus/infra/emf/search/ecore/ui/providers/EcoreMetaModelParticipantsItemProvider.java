/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreMetaModelParticipantsItemProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.providers;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;

public final class EcoreMetaModelParticipantsItemProvider extends AbstractMetaModelParticipantsItemProvider {
	public Object[] getElements(Object inputElement) {
		return EcorePackage.eINSTANCE.getEClassifiers().toArray();	
	}
}
