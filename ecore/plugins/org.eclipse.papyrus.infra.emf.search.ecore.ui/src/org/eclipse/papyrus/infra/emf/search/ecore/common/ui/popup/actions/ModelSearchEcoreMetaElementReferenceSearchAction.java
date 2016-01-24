/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchEcoreMetaElementReferenceSearchAction.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - refactor
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.common.ui.popup.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.evaluators.ECoreReferencesToEclassModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ui.actions.AbstractModelSearchPopupActionDelegate;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IActionDelegate;

public class ModelSearchEcoreMetaElementReferenceSearchAction extends AbstractModelSearchPopupActionDelegate {

	public final static String ID = "ereferencesAction";

	/**
	 * Constructor for ModelSearchEcoreMetaElementReferenceSearchAction.
	 */
	public ModelSearchEcoreMetaElementReferenceSearchAction() {
		super("EReferences", ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/EReference.gif"));
		setId(ID);
	}

	
	private EClassifier getEClassifier() {
		if (eObjectSelection!=null && !eObjectSelection.isEmpty() && eObjectSelection.getFirstElement() instanceof EClassifier) {
			return ((EClassifier) eObjectSelection.getFirstElement());
		}
		return null;
	}
	
	/**
	 * Creates a new Model Search Query from a given filter.
	 * 
	 * This query will apply on any Ecore resource of the current workspace.
	 * 
	 * @return Corresponding Model Search result populated with pattern matches if any.
	 */
     protected IModelSearchQuery newQuery() {
         IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(EcoreTextualEngine.ID);
         IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery("*", EcorePackage.Literals.ECLASS, scope, EcorePackage.eNS_URI);
         query.getModelSearchParameters().setEvaluator(new ECoreReferencesToEclassModelSearchQueryEvaluator<IModelSearchQuery, Resource>(getEClassifier()));
         return query;
     }
     
 	/**
 	 * @see IActionDelegate#run(IAction)
 	 */
 	public void run(IAction action) {
 		 // Process the search operation in background
         NewSearchUI.runQueryInBackground(newQuery());
 	}
}
