/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResultOccurenceChange.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;


/**
 * A change implementation applying to a single model search result entry occurence
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 *
 */
public class ModelSearchResultOccurenceChange extends Change {
	
	/**
	 * Single occurence a change has to be applied on
	 */
	private IModelResultEntry occurence;
	
	/**
	 * Currently considered {@link IModelSearchReplaceHandler} handler
	 */
	private IModelSearchReplaceHandler replaceHandler;
	
	/**
	 * Constructor
	 * 
	 * @param entry {@link IModelResultEntry} entry 
	 * @param handler {@link IModelSearchReplaceHandler} handler
	 */
	public ModelSearchResultOccurenceChange(IModelResultEntry entry, IModelSearchReplaceHandler handler) {
		occurence = entry;
		replaceHandler = handler;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getModifiedElement() {
		return occurence.getSource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return replaceHandler.getOccurenceLabel(occurence);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initializeValidationData(IProgressMonitor pm) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		return new NullChange();
	}
}
