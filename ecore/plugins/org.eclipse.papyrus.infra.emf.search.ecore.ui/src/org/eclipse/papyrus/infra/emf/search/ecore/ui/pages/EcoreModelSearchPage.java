/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreModelSearchPage.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add replace meta element handling
 * 		Lucas Bigeardel - introduce advanced regex replace
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.pages;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.ITransformation;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.NullModelSearchTransformation;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.ecore.internal.replace.provisional.EcoreRegexReplaceTransformation;
import org.eclipse.papyrus.infra.emf.search.ecore.utils.TextualFeaturesUtils;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;

public final class EcoreModelSearchPage extends AbstractModelSearchPage {
	@Override
	public String getModelSearchPageID() {
		return "org.eclipse.papyrus.infra.emf.search.ecore.ui.pages.EcoreModelSearchPageID"; //$NON-NLS-1$
	}

	public String getOccurenceLabel(IModelResultEntry entry) {
		return TextualFeaturesUtils.instance().getTextFromEStructuralFeatureIfAny((EObject)entry.getSource());
	}

	@Override
	public ITransformation<EObject, IModelSearchQuery, String, String> getTransformation(EObject element, IModelSearchQuery query,  String value) {
		return element instanceof ENamedElement ? new EcoreRegexReplaceTransformation((ENamedElement)element, query, value) : new NullModelSearchTransformation();
	}
}
