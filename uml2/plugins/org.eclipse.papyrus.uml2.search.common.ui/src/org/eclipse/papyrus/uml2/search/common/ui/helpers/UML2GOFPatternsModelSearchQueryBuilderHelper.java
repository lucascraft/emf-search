/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GOFPatternsModelSearchQueryBuilderHelper.java
 * 
 * Contributors: Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.helpers;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelExtensibleSearchEngineExtensionManager;
import org.eclipse.papyrus.infra.emf.search.core.services.ModelSearchEngineDescriptor;
import org.eclipse.papyrus.infra.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.papyrus.uml2.search.common.ui.evaluators.patterns.UML2CompositePatternDefaultModelSearchQueryEvaluator;
import org.eclipse.papyrus.uml2.search.engine.UML2TextualEngine;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * @author lucas
 *
 */
public class UML2GOFPatternsModelSearchQueryBuilderHelper implements IUML2GOFPatternsModelSearchQueryBuilderHelper {
    ModelSearchEngineDescriptor searchEngine = ModelExtensibleSearchEngineExtensionManager.getInstance().find(UML2TextualEngine.ID); //$NON-NLS-1$
    IModelSearchQueryParameters parameters = searchEngine.getModelSearchParametersFactory().createSearchQueryParameters();
    IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchWorkspaceScope(searchEngine.getID());

    private static UML2GOFPatternsModelSearchQueryBuilderHelper instance;

    /**
     * Singleton
     * @return a unique {@link UML2GOFPatternsModelSearchQueryBuilderHelper} instance
     */
    public static UML2GOFPatternsModelSearchQueryBuilderHelper instance() {
    	return instance == null ? instance = new UML2GOFPatternsModelSearchQueryBuilderHelper() : instance;
    }

    /**
     * Getter for all available GOF pattern model search queries 
     * @return all available GOF pattern model search queries
     */
    public List<IModelSearchQuery> getGOFPatternModelSearchQueries() {
    	return Arrays.asList(
    			new IModelSearchQuery[] {
    					createUML2GlobalCompositePatternModelSearchQuery()
    			}
    	);
    }
    
    public UML2GOFPatternsModelSearchQueryBuilderHelper() {
	}
    
	/**
	 * Creates a new Model Search Query from a given filter.
	 * 
	 * This query will apply on any Ecore resource from the current workspace.
	 * 
	 * @param participants meta element participants list
	 * @param evaluator model search query evaluator
	 * 
	 * @return Corresponding Model Search result populated with pattern matches if any
	 */
     protected IModelSearchQuery createQuery(List<EObject> participants, IModelSearchQueryEvaluator<IModelSearchQuery, Resource> evaluator, IModelSearchScope<?, Resource> scp) {
            parameters.setScope(scp);
            parameters.setEvaluator(evaluator);
            parameters.setParticipantElements(participants);          
            return searchEngine.getModelSearchQueryFactory().createModelSearchQuery("*", parameters);
    }

 	/**
 	 * Creates a new Model Search Query from a given filter.
 	 * 
 	 * This query will apply on any Ecore resource from the current workspace.
 	 * 
 	 * @param participants meta element participants list
 	 * @param evaluator model search query evaluator
 	 * 
 	 * @return Corresponding Model Search result populated with pattern matches if any
 	 */
      protected IModelSearchQuery createQuery(List<EObject> participants, IModelSearchQueryEvaluator<IModelSearchQuery, Resource> evaluator) {
             parameters.setScope(scope);
             parameters.setEvaluator(evaluator);
             parameters.setParticipantElements(participants);          
             return searchEngine.getModelSearchQueryFactory().createModelSearchQuery("*", parameters);
     }

      /**
       * 
       * @return a newly created UML2 composite pattern model search query
       */
      public IModelSearchQuery createUML2GlobalCompositePatternModelSearchQuery(IModelSearchScope<?, Resource> scp) {
      	return createQuery(
      			Arrays.asList(new EObject[] { UMLPackage.Literals.PACKAGE }), 
      			new UML2CompositePatternDefaultModelSearchQueryEvaluator<IModelSearchQuery, Resource>(),
      			scp
      	);
      }
       
      /**
       * 
       * @return a newly created UML2 composite pattern model search query
       */
      public IModelSearchQuery createUML2GlobalCompositePatternModelSearchQuery() {
      	return createQuery(
      			Arrays.asList(new EObject[] { UMLPackage.Literals.PACKAGE }), 
      			new UML2CompositePatternDefaultModelSearchQueryEvaluator<IModelSearchQuery, Resource>()
      	);
      }
       
     

}
