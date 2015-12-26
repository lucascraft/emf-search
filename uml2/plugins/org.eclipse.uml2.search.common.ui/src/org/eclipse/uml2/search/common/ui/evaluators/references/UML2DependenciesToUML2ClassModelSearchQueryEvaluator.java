/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2DependenciesToUML2ClassModelSearchQueryEvaluator.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.common.ui.evaluators.references;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.uml2.uml.NamedElement;

public final class UML2DependenciesToUML2ClassModelSearchQueryEvaluator<Q extends IModelSearchQuery, T extends Resource>  extends
		AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator<Q, T>  {

	public UML2DependenciesToUML2ClassModelSearchQueryEvaluator(NamedElement clazz) {
		super(clazz);
	}

	public List<?> eval(Q query, T target, boolean notify) {
		return computeDependencies(query, target, notify);		
	}
}
