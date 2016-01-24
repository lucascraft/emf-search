/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreSupportedElements.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.emf.search.ecore.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.l10n.Messages;

/**
 * 
 * Convenience entity offering services to discriminate model search valid ecore meta elements. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreSupportedElements {
	private static List<EClassifier> getEcoreEClassifiersLiterals() {
		List<EClassifier> eClassifiersLiteralsList = new ArrayList<EClassifier>();
		try {
			for (Object o : EcorePackage.eINSTANCE.getEClassifiers()) {
				if (o instanceof ENamedElement) {
					eClassifiersLiteralsList.add((EClassifier)o);
				}
			}
		} catch (Throwable t) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("EcoreSupportedElements.ecoreWalkingErrorMessage"), t)); //$NON-NLS-1$
		}
		return eClassifiersLiteralsList;
	}

	public static List<EClassifier> getSupportedElements(List<?> participantEClassList) {
		ArrayList<EClassifier> definitiveMetaElementParticipantList = new ArrayList<EClassifier>();
		for (EClassifier eClass : getEcoreEClassifiersLiterals()) {
			if (participantEClassList.contains(eClass)) {
				definitiveMetaElementParticipantList.add(eClass);
			}
		}
		return definitiveMetaElementParticipantList;
	}
}
