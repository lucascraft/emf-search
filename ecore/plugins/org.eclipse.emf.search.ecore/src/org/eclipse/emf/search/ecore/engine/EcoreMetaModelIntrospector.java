/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreMetaModelIntrospector.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.ecore.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.conditions.eobjects.TypeRelation;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

/**
 * Ecore convenience class allowing to collect all instances of supported meta elements from
 * a given metamodel & its supported meta elements.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreMetaModelIntrospector {
	/**
	 * Ecore convenience method allowing to collect all instances of supported meta elements from
	 * a given meta model reference & its supported meta elements EClass list.
	 * 
	 * @param model An Ecore meta model reference 
	 * @param supportedElementsList Supported meta element EClass list 
	 * @return All instances of supported meta elements, a void list otherwise
	 */
	@SuppressWarnings("unchecked")
	public static List<EClassifier> discriminateValidMetaElements(EObject model, List<EClassifier> supportedElementsList) {
		ArrayList validMetaElementsList = new ArrayList();
		for (EClassifier supportedElement : supportedElementsList) {
	        if (supportedElement instanceof EClass) {
    	        EObjectCondition typeCondition = new EObjectTypeRelationCondition(
    	                (EClass) supportedElement,
    	                TypeRelation.RELATED_TYPE_LITERAL
    	        );
    
    	        SELECT statement = new SELECT(
    	                new FROM(model), 
    	                new WHERE(typeCondition)
    	        );
    	        
    	        Set<? extends EObject> instances = statement.execute().getEObjects();
    	            
    			for (Object instance : instances) {
    				if (!validMetaElementsList.contains(instance)) {
    					validMetaElementsList.add(instance);
    				}
    			}
	        }
		}
		return validMetaElementsList;
	}
}
