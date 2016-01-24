/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2OCLInvariantModelSearchQueryEvaluator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ecore.ocl.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.ocl.conditions.BooleanOCLCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ocl.engine.OCLModelSearchQueryEnum;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.papyrus.uml2.search.ecore.ocl.engine.UML2OCLModelSearchQuery;
import org.eclipse.papyrus.uml2.search.ecore.ocl.l10n.Messages;
import org.eclipse.uml2.uml.Element;

public final class UML2OCLInvariantModelSearchQueryEvaluator<Q extends IModelSearchQuery, T> implements IModelSearchQueryEvaluator<Q, T> {
	private BooleanOCLCondition<EClassifier, EClass, EObject> condition;
	
	public List<?> eval(Q query, T target, boolean notify) {
		if (query instanceof UML2OCLModelSearchQuery) {
			return _eval(query, target, getContext(query), notify);
		}
		return new ArrayList<Object>();
	}

	@SuppressWarnings("restriction") //$NON-NLS-1$ //$NON-NLS-2$
	private List<?> _eval(Q query, T target, Object ctx, boolean notify) {
		// discriminating according to participant meta elements selection
		ArrayList<Object> lst = new ArrayList<Object>();
		
		OCL ocl = OCL.newInstance();
		for (Object o : query.getValidParticipantMetaElements()) {
			if (o instanceof Element && ctx instanceof EClassifier) {
		        try {
					condition = new BooleanOCLCondition<EClassifier, EClass, EObject>(
						ocl.getEnvironment(),
						query.getQueryExpression(),
						(EClassifier)ctx);
					
					// Build the select query statement
					SELECT statement = new SELECT(SELECT.WORK_UNIT, false,
							new FROM((Element)o), new WHERE(condition), new NullProgressMonitor());

					// Execute query
					for (Object r : statement.execute().getEObjects()) {
						lst.add(query.processSearchResultMatching(target, r, notify));
					}
		        } catch (ParserException e) {
		        	e.printStackTrace();
				}
			}
		}
		return lst;
	}
	
	protected Object getContext(Q query) {
		return query.getModelSearchParameters().getData(OCLModelSearchQueryEnum.OCL_MODEL_SEARCH_CONTEXT.name());
	}
	public String getLabel() {
		return Messages.getString("UML2OCLInvariantModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
}
