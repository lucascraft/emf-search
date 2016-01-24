/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractTextualModelSearchReplace.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

import java.util.Collection;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.l10n.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * <p>
 * Abstract implementation of LTK's {@link Refactoring}
 * </p>
 * <p>
 * This aims to implement the LTK refactoring behavior for model search query matches
 * </p>
 * <p>
 * As a result matches coming from model search queroes results are proposed to user thanks
 * to the LTK's interactive refactoring mechanism
 * </p> 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @since 0.7.0
 * 
 */
public abstract class AbstractTextualModelSearchReplace extends Refactoring {
	/**
	 * model search query to consider results from
	 */
	private IModelSearchQuery modelSearchQuery;
	
	/**
	 * String to replace user selected match occurence with
	 */
	private String replaceString;
	
	/**
	 * LTK Change applied to model search refactoring particular case
	 */
	private Change modelSearchChange;
	
	/**
	 * model search replace handler to be specialized for typical meta models
	 */
	private IModelSearchReplaceHandler replaceHandler;
	
	
	/**
	 * Constructor
	 * 
	 * @param query model search query the refactor will take results and data from
	 * @param handler entity owning model specific replace implementation
	 */
	public AbstractTextualModelSearchReplace(IModelSearchQuery query, IModelSearchReplaceHandler handler) {
		modelSearchQuery = query;
		replaceHandler = handler;
	}
		
	/**
	 * @return likely a not null handler entity owning model specific replace implementation
	 */
	protected IModelSearchReplaceHandler getReplaceHandler() {
		return replaceHandler;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (replaceString == null || replaceString.equals("")) { //$NON-NLS-1$
			return RefactoringStatus.createFatalErrorStatus(Messages.getString("AbstractTextualModelSearchReplace.VoidExpressionError")); //$NON-NLS-1$
		}
		
		RefactoringStatus resultingStatus= new RefactoringStatus();
		
		CompositeChange compositeChange= new CompositeChange(Messages.getString("AbstractTextualModelSearchReplace.ModelSearchChangeQueryLabel")); //$NON-NLS-1$
		compositeChange.markAsSynthetic();
		
		boolean hasChanges= false;
		try {
			for (Object o : modelSearchQuery.getModelSearchResult().getRootResultHierarchies().keySet()) {
				if (o instanceof Resource) {
					Collection<?> matches = modelSearchQuery.getModelSearchResult().getRootResultHierarchies().get(o);
					if (!matches.isEmpty()) {
						for (Object m : matches) {
							ModelSearchResultCompositeChange change = createModelSearchChange((Resource)o, modelSearchQuery, m, resultingStatus);
							if (change != null) {
								compositeChange.add(change);
								hasChanges= true;
							}
						}
					}
				}
			}
		} catch (PatternSyntaxException e) {
			return RefactoringStatus.createFatalErrorStatus(Messages.getString("AbstractTextualModelSearchReplace.DataError")); //$NON-NLS-1$
		}
		if (!hasChanges && resultingStatus.isOK()) {
			return RefactoringStatus.createFatalErrorStatus(Messages.getString("AbstractTextualModelSearchReplace.NoChangesError")); //$NON-NLS-1$
		}

		modelSearchChange = compositeChange;
		
		return resultingStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		String searchString= modelSearchQuery.getQueryExpression();
		if (searchString.length() == 0) {
			return RefactoringStatus.createFatalErrorStatus(Messages.getString("AbstractTextualModelSearchReplace.BlockingVoidExprError")); //$NON-NLS-1$
		}
		
        modelSearchQuery.run(pm);
		
		if (modelSearchQuery.getModelSearchResult().getTotalMatches() <= 0) {
			return RefactoringStatus.createFatalErrorStatus(Messages.getString("AbstractTextualModelSearchReplace.NoMatchError")); //$NON-NLS-1$
		}
		return new RefactoringStatus();
	}
	
	private ModelSearchResultCompositeChange createModelSearchChange(Resource resource, IModelSearchQuery query, Object match, RefactoringStatus resultingStatus) throws PatternSyntaxException, CoreException {
		return new ModelSearchResultCompositeChange(query, resource, getReplaceHandler(), replaceString);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return modelSearchChange;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return Messages.getString("AbstractTextualModelSearchReplace.ModelSearchTextReplaceLabel"); //$NON-NLS-1$
	}

	/**
	 * Getter for {@link IModelSearchQuery}
	 * @return the currently considered {@link IModelSearchQuery}
	 */
	public IModelSearchQuery getModelSearchQuery() {
		return modelSearchQuery;
	}

	/**
	 * Getter for replace String valuation
	 * @return the currently considered replace String valuation
	 */
	public String getReplaceString() {
		return replaceString;
	}

	/**
	 * Setter for replace String valuation
	 * @param string the currently considered replace String valuation
	 */
	public void setReplaceString(String string) {
		replaceString = string;
	}

	/**
	 * Getter for the currently considered model specific LTK {@link Change} 
	 * @return the currently considered model specific LTK {@link Change} 
	 */
	public Change getChange() {
		return modelSearchChange;
	}
}
