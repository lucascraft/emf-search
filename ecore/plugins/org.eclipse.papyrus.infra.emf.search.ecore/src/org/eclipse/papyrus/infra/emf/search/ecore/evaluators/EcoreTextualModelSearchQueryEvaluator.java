/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreTextualModelSearchQueryEvaluator.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - javadoc + EPL
 * 		Lucas Bigeardel - fix EAnnotation & name EAttribute matching bug
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.evaluators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.ecore.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionMatchingHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.utils.TextualFeaturesUtils;

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
public class EcoreTextualModelSearchQueryEvaluator<Q extends IModelSearchQuery, T> implements IModelSearchQueryEvaluator<Q, T> {
	/**
	 * Basically tries to match query condition (here a textual or regex one), for every search meta element participant.
	 * 
	 * @param <Q> Query
	 * @param <T> Target resource
	 * @param notification true if match has to be notified, false otherwise
	 * 
	 * @return list of matched instances of corresponding meta element, void list otherwise
	 */
	public List<?> eval(Q query, T target, boolean notification) {
		List<Object> results = new ArrayList<Object>();
		if (query instanceof EcoreModelSearchQuery) {
			ModelSearchQueryTextualExpressionEnum kind = ((EcoreModelSearchQuery) query)
					.getKind();
			String text = query.getQueryExpression();
			text = (text == "" && kind == ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT) ? "*" : text; //$NON-NLS-1$ //$NON-NLS-2$

			// discriminating according to participant meta elements selection
			for (Object o : query.getValidParticipantMetaElements()) {
				// In order to avoid duplicate results, the current element should be contained 
				// by the current resource
				if (target instanceof Resource) {
					if (o instanceof EObject) {
						Resource r = ((EObject) o).eResource();
						if (r instanceof Resource && r.getURI().equals(((Resource) target).getURI())) {
							EObject eObj = (EObject) o;
							if (TextualFeaturesUtils.instance().getTextFromEStructuralFeatureIfAny(eObj) != null) {
								for (ETypedElement elem : TextualFeaturesUtils.instance().getOwnedETypedElementsFromEObject(eObj)) {
									String elementName = TextualFeaturesUtils.instance().getTextFromETypedElement(eObj, elem);
									if (elementName != null
											&& ModelSearchQueryTextualExpressionMatchingHelper
													.getInstance().lookAt(
															elementName, text,
															kind)
													) {
										results.add(query.processSearchResultMatching(target, eObj, notification));
									}
								}
							}
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return Messages.getString("EcoreTextualModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
}
