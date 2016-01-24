/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TextualModelSearchReplaceRefactoring.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.ltk.core.refactoring.Refactoring;

/**
 * Actual implementation of LTK's {@link Refactoring}
 * <p>
 * This aims to implement the LTK refactoring behavior for model search query matches
 * </p>
 * <p>
 * As a result matches coming from model search queries results are proposed to user thanks
 * to the LTK's interactive refactoring mechanism
 * </p>
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @since 0.7.0
 * 
 */
public class TextualModelSearchReplaceRefactoring extends AbstractTextualModelSearchReplace {
	/**
	 * Constructor
	 * 
	 * @param query currently considered {@link IModelSearchQuery} query 
	 * @param replaceHandler currently considered {@link IModelSearchReplaceHandler} handler
	 */
	public TextualModelSearchReplaceRefactoring(IModelSearchQuery query, IModelSearchReplaceHandler replaceHandler) {
		super(query, replaceHandler);
	}
}
