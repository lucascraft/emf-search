/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreMetaModelParticipantsViewerSorter.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.providers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public final class EcoreMetaModelParticipantsViewerSorter extends ViewerSorter {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		EClassifier eClass1 = (EClassifier) e1; 
		EClassifier eClass2 = (EClassifier) e2; 
		
		String eClass1Name = "";
		if (e1 instanceof EClass) {
			eClass1Name += "ETypeKind_1_"; //$NON-NLS-1$
		} else if (e1 instanceof EDataType) {
			eClass1Name += "ETypeKind_2_"; //$NON-NLS-1$
		}
		eClass1Name += eClass1.getName()==null?"":eClass1.getName();
		
		String eClass2Name = "";
		if (e2 instanceof EClass) {
			eClass2Name += "ETypeKind_1_"; //$NON-NLS-1$
		} else if (e2 instanceof EDataType) {
			eClass2Name += "ETypeKind_2_"; //$NON-NLS-1$
		}
		eClass2Name += eClass2.getName()==null?"":eClass2.getName(); //$NON-NLS-1$
		
		return eClass1Name.compareTo(eClass2Name);
	}
}
