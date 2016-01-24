/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchPage.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.ui.pages;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.ITransformation;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.NullModelSearchTransformation;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;

public class GenModelSearchPage extends AbstractModelSearchPage {
	@Override
	public String getModelSearchPageID() {
		return "org.eclipse.papyrus.infra.emf.search.ui.pages.GenModelSearchPageID"; //$NON-NLS-1$
	}
	public String getOccurenceLabel(IModelResultEntry entry) {
		Object obejct = entry.getSource();
		if (obejct instanceof GenBase) {
			EObject elem = ((GenBase)obejct).getEcoreModelElement();
			if (elem instanceof ENamedElement) {
				((ENamedElement)elem).getName(); 
			}
		}
		return "ERROR"; //$NON-NLS-1$
	}
	@Override
	public ITransformation<EObject, IModelSearchQuery, String, String> getTransformation(
			EObject element, IModelSearchQuery query, String value) {
		return /* element instanceof GenBase ? new GenModelRegexReplaceTransformation((GenBase)element, value) : */ new NullModelSearchTransformation();
	}
}
