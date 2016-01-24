/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchPage.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add replace meta element handling
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.ui.pages;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.ITransformation;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.NullModelSearchTransformation;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.uml2.search.internal.replace.provisional.UML2RegexReplaceTransformation;
import org.eclipse.uml2.uml.NamedElement;

public final class UML2ModelSearchPage extends AbstractModelSearchPage {
	@Override
	protected String getModelSearchPageID() {
		return "org.eclipse.papyrus.uml2.search.ui.pages.UML2ModelSearchPageID"; //$NON-NLS-1$
	}
	public String getOccurenceLabel(IModelResultEntry entry) {
		return entry.getSource() instanceof NamedElement ? ((NamedElement)entry.getSource()).getName() : "ERROR"; //$NON-NLS-1$
	}
	@Override
	public ITransformation<EObject, IModelSearchQuery, String, String> getTransformation(
			EObject element, IModelSearchQuery query, String value) {
		return element instanceof NamedElement ? new UML2RegexReplaceTransformation((NamedElement)element, query, value) : new NullModelSearchTransformation();
	}
}
