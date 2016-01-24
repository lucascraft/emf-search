/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractUML2MetaElementReferenceSearchAction.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - move AbstractModelSearchPopupActionDelegate refactor
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.actions.references;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchEngineDescriptor;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.infra.emf.search.ui.actions.AbstractModelSearchPopupActionDelegate;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.papyrus.uml2.search.engine.UML2TextualEngine;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

public abstract class AbstractUML2MetaElementReferenceSearchAction extends
		AbstractModelSearchPopupActionDelegate {

	/**
	 * Constructor
	 */
	public AbstractUML2MetaElementReferenceSearchAction(String label, String ID, ImageDescriptor desc) {
		super(label, desc);
		setId(ID);
	}

	
	protected NamedElement getNamedElement() {
		if (eObjectSelection!=null && !eObjectSelection.isEmpty() && eObjectSelection.getFirstElement() instanceof NamedElement) {
			return ((NamedElement) eObjectSelection.getFirstElement());
		}
		return null;
	}
	
	protected List<EClassifier> getMetaElementParticipants() {
		ArrayList<EClassifier> participantList = new ArrayList<EClassifier>();
		if (getNamedElement()!=null) {
			participantList.add(UMLPackage.Literals.CLASS);
		}
		return participantList;
	}

	/**
	 * Creates a new Model Search Query from a given filter.
	 * 
	 * This query will apply on any Ecore resource from the current workspace.
	 * 
	 * @param filter A String describing a non regex and non case sensitive matching pattern expression
	 * 
	 * @return Corresponding Model Search result populated with pattern matches if any
	 */
     protected IModelSearchQuery newQuery() {
    	
            ModelSearchEngineDescriptor searchEngine = ModelExtensibleSearchEngineExtensionManager.getInstance().find(UML2TextualEngine.ID); //$NON-NLS-1$
        	
            IModelSearchQueryParameters parameters = searchEngine.getModelSearchParametersFactory().createSearchQueryParameters();
            
            IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(searchEngine.getID());
//            if (SELECTED PROJECT)
//            	scope = ModelSearchScopeFactory.getInstance().createModelProjectSearchScope(parameters, projectNames);
//            if (SELECTED OBJECT)
//            	scope = ModelSearchScopeFactory.getInstance().createModelSearchScope(parameters, selection);

            parameters.setScope(scope);
            parameters.setEvaluator(getEvaluator());
            parameters.setParticipantElements(getMetaElementParticipants());          
             
            return searchEngine.getModelSearchQueryFactory().createModelSearchQuery("*", parameters);
    }
     
     protected abstract IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getEvaluator();
     
 	/**
 	 * @see IActionDelegate#run(IAction)
 	 */
 	public void run(IAction action) {
 		 // Process the search operation in background
         NewSearchUI.runQueryInBackground(newQuery());
 	}
}
