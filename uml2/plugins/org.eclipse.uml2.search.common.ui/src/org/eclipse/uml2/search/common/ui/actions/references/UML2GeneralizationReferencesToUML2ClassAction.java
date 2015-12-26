/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GeneralizationReferencesToUML2ClassAction.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.common.ui.actions.references;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.ecore.common.ui.Activator;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.uml2.search.common.ui.evaluators.references.UML2GeneralizationsToUML2ClassModelSearchQueryEvaluator;
import org.eclipse.uml2.search.common.ui.l10n.Messages;

public final class UML2GeneralizationReferencesToUML2ClassAction extends
		AbstractUML2MetaElementReferenceSearchAction {

	public final static String ID = "uml2GeneralizationReferencesSearchAction"; //$NON-NLS-1$

	public UML2GeneralizationReferencesToUML2ClassAction() {
		super(Messages.getString("UML2GeneralizationReferencesToUML2ClassAction.Label"), ID, ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/EReference.gif")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Override
	protected IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getEvaluator() {
		return new UML2GeneralizationsToUML2ClassModelSearchQueryEvaluator<IModelSearchQuery, Resource>(getNamedElement());
	}
}
