/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreDiagramSearchContributionItemProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.ecoretools.diagram.search.providers;

import org.eclipse.emf.search.ecore.common.ui.popup.actions.ModelSearchEcoreMetaElementReferenceSearchAction;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;

/**
 * This entity provides items to Ecore diagram editor popup menus for model search purposes.
 * Everytime user right clicks on a selection having one of the following ation ID registred,
 * this provider return a new instance of the requested Action implementation.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreDiagramSearchContributionItemProvider extends
		AbstractContributionItemProvider {
	@Override
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		if (ModelSearchEcoreMetaElementReferenceSearchAction.ID.equals(actionId)) {
			return new ModelSearchEcoreMetaElementReferenceSearchAction();
		}
		return super.createAction(actionId, partDescriptor);
	}
}
