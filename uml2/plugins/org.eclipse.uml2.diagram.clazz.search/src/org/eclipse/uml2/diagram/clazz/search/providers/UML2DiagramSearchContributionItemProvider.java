/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2DiagramSearchContributionItemProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.diagram.clazz.search.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;
import org.eclipse.uml2.search.common.ui.actions.patterns.UML2CompositePatternReferencesToUML2PackageAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2AggregationReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2AssociationReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2CompositionReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2DependencyReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2GeneralizationReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2RealizationReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2SubstitutionReferencesToUML2ClassAction;
import org.eclipse.uml2.search.common.ui.actions.references.UML2UsageReferencesToUML2ClassAction;

/**
 * This entity provides items to UML2 diagram editor popup menus for model search purposes.
 * Everytime user right clicks on a selection having one of the following ation ID registred,
 * this provider return a new instance of the requested Action implementation.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2DiagramSearchContributionItemProvider extends
		AbstractContributionItemProvider {
	@Override
	protected IAction createAction(String actionId, IWorkbenchPartDescriptor partDescriptor) {
		if (UML2AggregationReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2AggregationReferencesToUML2ClassAction();
		} else if (UML2AssociationReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2AssociationReferencesToUML2ClassAction();
		} else if (UML2CompositionReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2CompositionReferencesToUML2ClassAction();
		} else if (UML2GeneralizationReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2GeneralizationReferencesToUML2ClassAction();
		} else if (UML2DependencyReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2DependencyReferencesToUML2ClassAction();
		} else if (UML2UsageReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2UsageReferencesToUML2ClassAction();
		} else if (UML2RealizationReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2RealizationReferencesToUML2ClassAction();
		} else if (UML2SubstitutionReferencesToUML2ClassAction.ID.equals(actionId)) {
			return new UML2SubstitutionReferencesToUML2ClassAction();
		} else if (UML2CompositePatternReferencesToUML2PackageAction.ID.equals(actionId)) {
			return new UML2CompositePatternReferencesToUML2PackageAction();
		}
		return super.createAction(actionId, partDescriptor);
	}
}