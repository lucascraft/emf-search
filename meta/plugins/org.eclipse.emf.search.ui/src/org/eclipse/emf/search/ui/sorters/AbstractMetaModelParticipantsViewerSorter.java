/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractMetaModelParticipantsViewerSorter.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ui.sorters;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Arrange viewer elements according to a lexicographic order sort algorithm on
 * element names.
 * 
 * EClass types are displayed first then, EDataType ones.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 */
public class AbstractMetaModelParticipantsViewerSorter extends ViewerSorter
{
    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
	ENamedElement eClass1 = (ENamedElement) e1;
	ENamedElement eClass2 = (ENamedElement) e2;

	String eClass1Name = "";
	if (e1 instanceof EClass)
	{
	    eClass1Name += "ETypeKind_1_";
	} else if (e1 instanceof EDataType)
	{
	    eClass1Name += "ETypeKind_2_";
	}
	eClass1Name += eClass1.getName() == null ? "" : eClass1.getName();

	String eClass2Name = "";
	if (e2 instanceof EClass)
	{
	    eClass2Name += "ETypeKind_1_";
	} else if (e2 instanceof EDataType)
	{
	    eClass2Name += "ETypeKind_2_";
	}
	eClass2Name += eClass2.getName() == null ? "" : eClass2.getName();

	return eClass1Name.compareTo(eClass2Name);
    }
}
