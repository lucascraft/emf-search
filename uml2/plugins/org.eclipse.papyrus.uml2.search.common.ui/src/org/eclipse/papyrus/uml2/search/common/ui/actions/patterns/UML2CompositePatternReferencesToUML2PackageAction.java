/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2CompositePatternReferencesToUML2ClassAction.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.actions.patterns;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.common.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.papyrus.uml2.search.common.ui.actions.references.AbstractUML2MetaElementReferenceSearchAction;
import org.eclipse.papyrus.uml2.search.common.ui.evaluators.patterns.UML2CompositePatternModelSearchQueryEvaluatorWithArgs;
import org.eclipse.papyrus.uml2.search.common.ui.l10n.Messages;
import org.eclipse.uml2.uml.UMLPackage;

public final class UML2CompositePatternReferencesToUML2PackageAction extends
		AbstractUML2MetaElementReferenceSearchAction {

	public final static String ID = "uml2CompositePatternReferencesSearchAction"; //$NON-NLS-1$

	public UML2CompositePatternReferencesToUML2PackageAction() {
		super(Messages.getString("UML2CompositePatternReferencesToUML2ClassAction.Label"), ID, ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/EReference.gif")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	protected IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getEvaluator() {
		return new UML2CompositePatternModelSearchQueryEvaluatorWithArgs<IModelSearchQuery, Resource>(getNamedElement());
	}
	
	protected List<EClassifier> getMetaElementParticipants() {
		ArrayList<EClassifier> participantList = new ArrayList<EClassifier>();
		if (getNamedElement()!=null) {
			participantList.add(UMLPackage.Literals.PACKAGE);
		}
		return participantList;
	}

}
