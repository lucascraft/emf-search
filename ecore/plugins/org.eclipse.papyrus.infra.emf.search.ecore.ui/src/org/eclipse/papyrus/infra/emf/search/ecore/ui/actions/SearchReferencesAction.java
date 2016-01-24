/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * SearchReferencesAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.evaluators.ECoreReferencesToEclassModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.actions.AbstractModelSearchPageAction;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.search.ui.NewSearchUI;

public class SearchReferencesAction extends AbstractModelSearchPageAction {

	public SearchReferencesAction() {
		super(
				Messages.getString("ECoreReferencesToEclassModelSearchQueryEvaluator.Label"),  //$NON-NLS-1$ 
				ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/EReference.gif") //$NON-NLS-1$ 
			);
	}
	@Override
	public void run() {
		Object o = getModelSearchResultPageSelection();
		if (o instanceof IModelResultEntry) {
			Object source = ((IModelResultEntry)o).getSource();
			if (source instanceof EClassifier) {
				handleENamedElement((EClassifier)source);
			}
		}
	}
	
	private void handleENamedElement(EClassifier element) {
		NewSearchUI.runQueryInBackground(newQuery(element));
	}
	
	/**
	 * Creates a new Model Search Query from a given filter.
	 * 
	 * This query will apply on any Ecore resource of the current workspace.
	 * 
	 * @return Corresponding Model Search result populated with pattern matches if any.
	 */
     protected IModelSearchQuery newQuery(EClassifier element) {
         IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
         IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery("*", EcorePackage.Literals.ECLASS, scope, EcorePackage.eNS_URI);
         query.getModelSearchParameters().setEvaluator(new ECoreReferencesToEclassModelSearchQueryEvaluator<IModelSearchQuery, Resource>(element));
         return query;
     }
}
