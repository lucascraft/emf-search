/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelSearchQuery.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.papyrus.infra.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreMetaModelIntrospector;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.ecore.results.EcoreModelSearchResultEntry;
import org.eclipse.papyrus.infra.emf.search.ecore.utils.EcoreModelLoaderUtil;
import org.eclipse.papyrus.infra.emf.search.genmodel.Activator;
import org.eclipse.papyrus.infra.emf.search.genmodel.evaluators.GenModelTextualModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.genmodel.l10n.Messages;


public class GenModelSearchQuery extends EcoreModelSearchQuery {
	public final static String GENMODEL_MODEL_SEARCH_IMAGE_PATH = "icons/full/obj16/gsearch.gif"; //$NON-NLS-1$
	public final static String GENMODEL_MODEL_SEARCH_RESULT_IMAGE_PATH = "icons/full/obj16/gsearch.gif"; //$NON-NLS-1$

	final static class GenModelSupportedElements {
		private static List<EClassifier> getGenModelEClassifiersLiterals() {
			List<EClassifier> eclassifiersLiteralsList = new ArrayList<EClassifier>();
			try {
				for (Object o : GenModelPackage.eINSTANCE.getEClassifiers()) {
					if (o instanceof ENamedElement ) {
						eclassifiersLiteralsList.add((EClassifier)o);
					}
				}
			} catch (Throwable t) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("GenModelSearchQuery.GenModelClassifiersWalkErrorMessage"), t)); //$NON-NLS-1$
			}
			return eclassifiersLiteralsList;
		}

		public static List<EClassifier> getSupportedElements(List<? extends Object> participantEClassList) {
			ArrayList<EClassifier> definitiveMetaElementParticipantList = new ArrayList<EClassifier>();
			for (EClassifier eClass : getGenModelEClassifiersLiterals()) {
				if (participantEClassList.contains(eClass)) {
					definitiveMetaElementParticipantList.add(eClass);
				}
			}
			return definitiveMetaElementParticipantList;
		}
	}
	
	public GenModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {
		super(expr, parameters);
	}
	
	@Override
	public String getQueryImagePath() {
		return GENMODEL_MODEL_SEARCH_IMAGE_PATH;
	}
	
	@Override
	public String getResultImagePath() {
		return GENMODEL_MODEL_SEARCH_RESULT_IMAGE_PATH;
	}
	
    public Collection<String> getTargetMetaModelIDs() {
    	return Arrays.asList(new String[] { GenModelPackage.eNS_URI });
    }

	@Override
    public String getBundleSymbolicName() {
    	return Activator.getDefault().getBundle().getSymbolicName();
    }
	
	@Override
	public IStatus search(Object resource, boolean notify) {
		try {
			EObject root = EcoreModelLoaderUtil.openFile(resource, false);
			
			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements(
					(EObject) root, 
					GenModelSupportedElements.getSupportedElements(participantElements)
			);
			
			((IModelSearchQueryEvaluator<IModelSearchQuery, Object>)getEvaluator()).eval(this, resource, notify);
		} catch (Exception e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}


	public IStatus search(Object resource, boolean notify, IProgressMonitor monitor) {
		try {
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			
			EObject root = EcoreModelLoaderUtil.openFile(resource, false);
			
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			
			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, GenModelSupportedElements.getSupportedElements(participantElements));
			
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			
			((IModelSearchQueryEvaluator<IModelSearchQuery, Object>)getEvaluator()).eval(this, resource, notify);
			
			monitor.setTaskName(getLabel());
			
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
		} catch (Exception e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}
	
	@Override
	public String getName() {
		switch(getKind()) {
			case NORMAL_TEXT:
				return Messages.getString("GenModelSearchQuery.GenModelNormalTextMessage"); //$NON-NLS-1$
			case CASE_SENSITIVE:
				return Messages.getString("GenModelSearchQuery.GenModelCaseSensitiveMessage"); //$NON-NLS-1$
			case REGULAR_EXPRESSION:
				return Messages.getString("GenModelSearchQuery.GenModelRegularExpressionMessage"); //$NON-NLS-1$
			default:
				return Messages.getString("GenModelSearchQuery.GenModelMessage"); //$NON-NLS-1$
		}
	}
	
	// Recursively build EObject containment hierarchy
	private IModelResultEntry buildSearchResultEntryHierarchy(IModelResultEntry intermediate, Object resource, EObject current, GenBase leaf) {
		if (current instanceof GenBase) {
			IModelResultEntry entryContainer = new EcoreModelSearchResultEntry(null, resource, current, false);
			entryContainer.addChildren(intermediate);
			intermediate.setParent(entryContainer);
			return buildSearchResultEntryHierarchy(entryContainer, resource, ((GenBase)current).eContainer(), leaf);
		} else {
			return intermediate;
		}
	}
	
	@Override
	public IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {
		evaluator = getModelSearchParameters().getEvaluator();
		return evaluator != null ? evaluator : ( evaluator = new GenModelTextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());
	}
	
	@Override
	public ICompositeModelResultEntry buildCompositeSearchResultEntryHierarchy(
			Object resource) {
		return super.buildCompositeSearchResultEntryHierarchy(resource);
	}

	@Override
	public IModelResultEntry buildSearchResultEntryHierarchy(Object resource, Object o) {
		EcoreModelSearchResultEntry mesre = new EcoreModelSearchResultEntry(null, resource, o, true);
		if (o instanceof GenBase) {
			GenBase c = (GenBase) o;
		    return buildSearchResultEntryHierarchy(mesre, resource, c.eContainer(), c);
		}
		// Just in case we could deal with some nested exotic objects without containment notions ^^
		return mesre;
	}
}
