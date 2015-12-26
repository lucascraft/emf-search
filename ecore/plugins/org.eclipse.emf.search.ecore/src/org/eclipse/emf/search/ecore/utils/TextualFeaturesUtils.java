/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TextualFeaturesUtils.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.search.ecore.Activator;

/**
 * 
 * @author lucas
 *
 */
public class TextualFeaturesUtils{

    public TextualFeaturesUtils() {}

	private static TextualFeaturesUtils instance;

	//
	// 
	//
	public static TextualFeaturesUtils instance() {
		return instance == null ? instance = new TextualFeaturesUtils() : instance;
	}

	//
	// 
	//
	public List<ETypedElement> getParticipantTextualTypedElement() {
		List<ETypedElement> list = new ArrayList<ETypedElement>();
		list.addAll(getEcorePackageParticipantTextualTypedElement());
		return list;
	}

	//
	//
	//
	public List<ETypedElement> getEcorePackageParticipantTextualTypedElement() {
		return Arrays.asList(new ETypedElement[] { EcorePackage.Literals.ENAMED_ELEMENT__NAME });
	}

	//
	// 
	//
	public List<ETypedElement> getOwnedETypedElementsFromEObject(EObject obj) {
		List<ETypedElement> list = new ArrayList<ETypedElement>();
		for (ETypedElement e : getParticipantTextualTypedElement()) {
			if (e instanceof EStructuralFeature) {
				try {
					Object o = obj.eGet((EStructuralFeature) e);
					if (o instanceof String) {
						list.add(e);
					}
				} catch (Throwable t) {
				}
			}
		}
		return list;
	}

	//
	// 
	//
	public String getTextFromETypedElement(EObject obj, ETypedElement elem) {
		if (elem instanceof EStructuralFeature) {
			Object o = obj.eGet((EStructuralFeature) elem);
			if (o instanceof String) {
				return (String) o;
			}
		}
		return null;
	}

	//
	// 
	//
	public String getTextFromEStructuralFeatureIfAny(EObject obj) {
		List<ETypedElement> lst = getOwnedETypedElementsFromEObject(obj);
		if (!lst.isEmpty()) {
			ETypedElement elem = lst.get(0);
			if (elem instanceof EStructuralFeature) {
				Object o = obj.eGet((EStructuralFeature) elem);
				if (o instanceof String) {
					return (String) o;
				}
			}
		}
		return null;
	}

	//
	// 
	//
	public void setTextForEStructuralFeatureIfAny(EObject obj, Object val) {
		for (ETypedElement e : getParticipantTextualTypedElement()) {
			if (e instanceof EStructuralFeature) {
				try {
					if (val instanceof String) {
						obj.eSet((EStructuralFeature) e, val);
					}
				} catch (Throwable ex) {
					Activator.getDefault().getLog().log(new Status(
						IStatus.ERROR, Activator.PLUGIN_ID, ex.getLocalizedMessage()
					));
				}
			}
		}
	}

	//
	// 
	//
	public void setTextForETypedElement(EObject obj, ETypedElement elem,
			Object val) {
		if (elem instanceof EStructuralFeature) {
			try {
				if (val instanceof String) {
					obj.eSet((EStructuralFeature) elem, val);
				}
			} catch (Throwable ex) {
				Activator.getDefault().getLog().log(new Status(
						IStatus.ERROR, Activator.PLUGIN_ID, ex.getLocalizedMessage()
					));
			}
		}
	}
}