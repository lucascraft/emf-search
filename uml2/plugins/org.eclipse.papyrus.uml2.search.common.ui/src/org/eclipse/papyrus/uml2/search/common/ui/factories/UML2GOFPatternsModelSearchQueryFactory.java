/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GOFPatternsModelSearchQueryFactory.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.factories;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.factories.IModelSearchQueryFactory;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.uml2.search.common.ui.helpers.UML2GOFPatternsModelSearchQueryBuilderHelper;

/**
 * 
 * @author lucas
 *
 */
public class UML2GOFPatternsModelSearchQueryFactory implements IModelSearchQueryFactory {
		private static UML2GOFPatternsModelSearchQueryFactory instance;
		public UML2GOFPatternsModelSearchQueryFactory() {}
		public static UML2GOFPatternsModelSearchQueryFactory getInstance() {
			return instance == null ? instance = new UML2GOFPatternsModelSearchQueryFactory() : instance;
		}
		public IModelSearchQuery createModelSearchQuery(String expr,
				IModelSearchQueryParameters p) {
			return UML2GOFPatternsModelSearchQueryBuilderHelper.instance().createUML2GlobalCompositePatternModelSearchQuery(
					p.getScope()
			);
		}
}
