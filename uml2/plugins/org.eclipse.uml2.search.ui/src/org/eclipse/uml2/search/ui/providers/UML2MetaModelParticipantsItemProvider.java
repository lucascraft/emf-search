/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;
import org.eclipse.uml2.uml.UMLPackage;

public final class UML2MetaModelParticipantsItemProvider extends
AbstractMetaModelParticipantsItemProvider {
	public Object[] getElements(Object inputElement) {
		List<EPackage> particpantPackagesList = new ArrayList<EPackage>();
		particpantPackagesList.add(UMLPackage.eINSTANCE);
		
		List<Object> eClassifiersList = new ArrayList<Object>();
		for (EPackage partipantPackage : particpantPackagesList) {
			eClassifiersList.addAll(partipantPackage.getEClassifiers());
		}
		return eClassifiersList.toArray();
	}
}
