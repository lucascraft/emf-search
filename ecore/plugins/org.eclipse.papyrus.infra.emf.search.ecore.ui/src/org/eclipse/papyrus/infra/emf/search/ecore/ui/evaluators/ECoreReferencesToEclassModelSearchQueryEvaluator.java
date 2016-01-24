/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ECoreReferencesToEclassModelSearchQueryEvaluator.java
 *
 * Contributors:
 *    Lucas Bigeardel (Anyware Technologies) - EMFT Search integrations
 *    Lucas Bigeardel - move evaluator to lower level package
 *******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.evaluators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.l10n.Messages;

public final class ECoreReferencesToEclassModelSearchQueryEvaluator<Q extends IModelSearchQuery, T extends Resource> implements
		IModelSearchQueryEvaluator<Q, T> {
	private EClassifier eClass;
	public ECoreReferencesToEclassModelSearchQueryEvaluator(EClassifier clazz) {
		eClass = clazz;
	}
	public EClassifier getEClass() {
		return eClass;
	}
	public List<?> eval(Q query,
			T resource, boolean notify) {
	       List<Object> result = new ArrayList<Object>();
	       if (eClass != null) {
			   for (EStructuralFeature.Setting setting : (Collection<EStructuralFeature.Setting>) EcoreUtil.UsageCrossReferencer.find(eClass, eClass.eResource())) {
			       if (setting.getEObject() instanceof EReference) {
			    	   URI eClassURI = setting.getEObject().eResource().getURI();
			    	   if (resource.getURI().equals(eClassURI)) { 
			    		   if (!result.contains(setting.getEObject())) {
			        		   result.add(query.processSearchResultMatching(resource, setting.getEObject(), notify));
			        	   }
			           }
			       }
		       }
		   }
	       return result;
	}
	public String getLabel() {
		return Messages.getString("ECoreReferencesToEclassModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
}
