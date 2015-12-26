/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2TextualModelSearchQueryEvaluator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.emf.search.ecore.evaluators.EcoreTextualModelSearchQueryEvaluator;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionMatchingHelper;
import org.eclipse.uml2.search.l10n.Messages;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Evaluator Entity has its eval method call every time a Q query needs to be evaluated against a T target from a search scope.
 * 
 * It basically tries to match query condition (here a textual or regex one), for every search meta element participant.
 * 
 * Matches are processed accordingly to user parmetrization.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @param <Q> Query
 * @param <T> Target resource
 */
public final class UML2TextualModelSearchQueryEvaluator<Q extends IModelSearchQuery, T> extends
EcoreTextualModelSearchQueryEvaluator<Q, T> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> eval(Q query, T target, boolean notifiy) {
		List<Object> results = new ArrayList<Object>();
		ModelSearchQueryTextualExpressionEnum kind = ((EcoreModelSearchQuery)query).getKind();
			for (Object o : query.getValidParticipantMetaElements()) {
				if (o instanceof NamedElement) {
					String text = query.getQueryExpression();
					text = (text==""&& kind==ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT)?"*":text; //$NON-NLS-1$ //$NON-NLS-2$

					String umlElementName = ((NamedElement)o).getName();
					umlElementName = umlElementName!=null?umlElementName:""; //$NON-NLS-1$
					if (ModelSearchQueryTextualExpressionMatchingHelper.getInstance().lookAt(umlElementName, text, kind)) {
						results.add(query.processSearchResultMatching(target, o, notifiy));				
					}
				}
			}
		return results;
	}	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return Messages.getString("UML2TextualModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
}
