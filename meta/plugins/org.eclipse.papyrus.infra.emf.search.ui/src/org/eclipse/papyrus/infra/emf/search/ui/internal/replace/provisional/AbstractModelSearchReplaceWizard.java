/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchReplaceWizard.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional;

import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.AbstractTextualModelSearchReplace;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * User wanting to implement a custom model replace refactoring wizard must
 * extend AbstractModelSearchReplaceWizard. Doing so will allow to apply 
 * textual refactoring operations on ecore based user custom meta models.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 */
public abstract class AbstractModelSearchReplaceWizard extends RefactoringWizard {
    /**
     * Constructor
     * 
     * @param refactoring user defined refactoring operation
     * @param flags SWT style flags
     */
    public AbstractModelSearchReplaceWizard(Refactoring refactoring, int flags) {
		super(refactoring, flags);
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	protected void addUserInputPages() {
		addPage(new ModelSearchReplaceConfigurationPage((AbstractTextualModelSearchReplace) getRefactoring()));
	}
}
