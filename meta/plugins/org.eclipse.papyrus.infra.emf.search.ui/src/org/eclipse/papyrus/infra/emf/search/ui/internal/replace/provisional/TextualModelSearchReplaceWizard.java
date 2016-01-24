/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TextualModelSearchReplaceWizard.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional;

import org.eclipse.ltk.core.refactoring.Refactoring;

/**
 * Concrete implementation for Model Search Replace Wizard
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 *
 */
public class TextualModelSearchReplaceWizard extends AbstractModelSearchReplaceWizard {
	public TextualModelSearchReplaceWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
	}
}
