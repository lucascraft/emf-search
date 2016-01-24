/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResultOccurrence.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

public class ModelSearchResultOccurrence extends AbstractModelSearchResultOccurence {

	public ModelSearchResultOccurrence(IModelResultEntry p, Object o, Object t, String valuation, boolean matched) {
		super(p, o, t, matched, valuation);
	}
}
