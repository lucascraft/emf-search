/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2CompositePatternDefaultModelSearchQueryEvaluator.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.evaluators.patterns;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.uml2.search.common.ui.l10n.Messages;
import org.eclipse.uml2.uml.Package;

public final class UML2CompositePatternDefaultModelSearchQueryEvaluator<Q extends IModelSearchQuery, T extends Resource>  extends
		AbstractUML2GOFPatternsModelSearchQueryEvaluator<Q, T>  {

	public UML2CompositePatternDefaultModelSearchQueryEvaluator() {
		super();
	}

	@Override
	public String getLabel() {
		return Messages.getString("UML2CompositePatternReferencesToUML2ClassAction.Label");
	}
	
	public List<?> eval(Q query, T target, boolean notify) {
		List<?> lst = new ArrayList<EObject>();
		for (Object o : query.getValidParticipantMetaElements()) {
			if (o instanceof Package) {
				setNamedElement((Package)o);
				computeCompositePattern(query, target);
			}
		}
		return lst;
	}
}
