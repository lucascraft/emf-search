/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResultCompositeChange.jva
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelSearchResult;
import org.eclipse.papyrus.infra.emf.search.l10n.Messages;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * <p>
 * Represents a composite change. Composite changes can be marked
 * as synthetic
 * </p>
 * <p>
 * A synthetic composite changes might not be rendered
 * in the refactoring preview tree to save display real-estate
 * </p>
 * <p>
 * Clients may subclass this class.
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.70
 *
 */
public class ModelSearchResultCompositeChange extends CompositeChange {
	/**
	 * EMF Resource target of the currently considered replace transformation
	 */ 
	private Resource resource;
	
	/**
	 * Currently considered {@link IModelSearchQuery} model search query
	 */ 
	IModelSearchQuery modelSearchQuery;
	
	/**
	 * Model search result which match occurences could be considered by the change
	 */
	private IModelSearchResult modelSearchResult;
	
	/**
	 * occurrence hving been to considered by the current change
	 */
	private Collection<IModelResultEntry> occurences;
	
	/**
	 * OLD/NEW textual values
	 */
	private String oldValueReplaced, newReplaceValue;
	
	/**
	 * Currently considered {@link IModelSearchReplaceHandler} handler
	 */
	private IModelSearchReplaceHandler replaceHandler;
	
	/**
	 * Constructor
	 */
	public ModelSearchResultCompositeChange(IModelSearchQuery query, Resource target, IModelSearchReplaceHandler handler, String stringReplace) {
		super(Messages.getString("ModelSearchResultCompositeChange.ModelSearchChangeLabel.0")); //$NON-NLS-1$
		resource = target;
		modelSearchQuery = query;
		modelSearchResult = query.getModelSearchResult();
		replaceHandler = handler;
		occurences = modelSearchResult.getResultsFlatennedForFile(target);
		newReplaceValue = stringReplace;
		for (IModelResultEntry occurence : occurences) {
			add(new ModelSearchResultOccurenceChange(occurence, handler));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getModifiedElement() {
		return resource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		String targetName = Messages.getString("ModelSearchResultCompositeChange.ModelSearchChangeLabel.1") +  //$NON-NLS-1$
								resource.getURI().toString() +  
								Messages.getString("ModelSearchResultCompositeChange.ModelSearchChangeLabel.2") +  //$NON-NLS-1$
								modelSearchResult.getMatchesNumberForFile(resource) + 
								Messages.getString("ModelSearchResultCompositeChange.ModelSearchChangeLabel.3");  //$NON-NLS-1$
		return targetName;
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
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		TransactionalEditingDomain domain = TransactionalEditingDomainImpl.FactoryImpl.INSTANCE.getEditingDomain(resource.getResourceSet());
		if (!(domain instanceof EditingDomain)) {
			domain = TransactionalEditingDomainImpl.FactoryImpl.INSTANCE.createEditingDomain(resource.getResourceSet());
		}
			
		domain.getCommandStack().execute(
			new RecordingCommand(domain) {
				protected void doExecute() {
					for (IModelResultEntry occurence : occurences) {
						if (occurence.getSource() instanceof EObject) {
							replaceHandler.handleReplace((EObject)occurence.getSource(), modelSearchQuery, newReplaceValue);
							Map<String, Object> options = new HashMap<String, Object>();
							options.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_FILE_BUFFER);
							try {
								resource.save(options);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		);
		return new ModelSearchResultCompositeChange(modelSearchQuery, resource, replaceHandler, oldValueReplaced);
	}
}
