/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2ModelSearchQuery.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.parameters.IModelSearchQueryParameters;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.ecore.engine.EcoreMetaModelIntrospector;
import org.eclipse.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.emf.search.ecore.results.EcoreModelSearchResultEntry;
import org.eclipse.emf.search.ecore.utils.EcoreModelLoaderUtil;
import org.eclipse.uml2.search.Activator;
import org.eclipse.uml2.search.evaluators.UML2TextualModelSearchQueryEvaluator;
import org.eclipse.uml2.search.l10n.Messages;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Gather all model search settings to run an UML2 query.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2ModelSearchQuery extends EcoreModelSearchQuery implements IModelSearchQuery {
	public final static String UML2_MODEL_SEARCH_IMAGE_PATH = "icons/uml2.gif"; //$NON-NLS-1$
	public final static String UML2_MODEL_SEARCH_RESULT_IMAGE_PATH = "icons/usearch.gif"; //$NON-NLS-1$

	final static class UML2SupportedElements {
		private static List<EClassifier> getUML2EClassifiersLiterals() {
			List<EClassifier> eclassifiersLiteralsList = new ArrayList<EClassifier>();
			try {
				for (Object o : UMLPackage.eINSTANCE.getEClassifiers()) {
					if (o instanceof ENamedElement ) {
						eclassifiersLiteralsList.add((EClassifier)o);
					}
				}
			} catch (Throwable t) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("UML2ModelSearchQuery.uml2ClassifiersWalkErrorMessage"), t)); //$NON-NLS-1$
			}
			return eclassifiersLiteralsList;
		}

		public static List<EClassifier> getSupportedElements(List<? extends Object> participantEClassList) {
			ArrayList<EClassifier> definitiveMetaElementParticipantList = new ArrayList<EClassifier>();
			for (EClassifier eClass : getUML2EClassifiersLiterals()) {
				if (participantEClassList.contains(eClass)) {
					definitiveMetaElementParticipantList.add(eClass);
				}
			}
			return definitiveMetaElementParticipantList;
		}
	}
	
	@Override
	public String getQueryImagePath() {
		return UML2_MODEL_SEARCH_IMAGE_PATH;
	}
	
	@Override
	public String getResultImagePath() {
		return UML2_MODEL_SEARCH_RESULT_IMAGE_PATH;
	}

    public Collection<String> getTargetMetaModelIDs() {
    	return Arrays.asList(new String[] { UMLPackage.eNS_URI });
    }

	@Override
    public String getBundleSymbolicName() {
    	return Activator.getDefault().getBundle().getSymbolicName();
    }

	public UML2ModelSearchQuery(String expr, IModelSearchQueryParameters parameters) {
		super(expr, parameters);
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
			
			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, UML2SupportedElements.getSupportedElements(participantElements));
			
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
	public IStatus search(Object resource, boolean notify) {
		try {
			EObject root = EcoreModelLoaderUtil.openFile(resource, false);
			
			validParticipantMetaElements = EcoreMetaModelIntrospector.discriminateValidMetaElements((EObject)root, UML2SupportedElements.getSupportedElements(participantElements));
			
			((IModelSearchQueryEvaluator<IModelSearchQuery, Object>)getEvaluator()).eval(this, resource, notify);
		} catch (Exception e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}
	
	@Override
	public String getName() {
		switch(getKind()) {
			case NORMAL_TEXT:
				return Messages.getString("UML2ModelSearchQuery.UML2NormalTextMessage"); //$NON-NLS-1$
			case CASE_SENSITIVE:
				return Messages.getString("UML2ModelSearchQuery.UML2CaseSensitiveMessage"); //$NON-NLS-1$
			case REGULAR_EXPRESSION:
				return Messages.getString("UML2ModelSearchQuery.UML2RegularExpressionMessage"); //$NON-NLS-1$
			default:
				return Messages.getString("UML2ModelSearchQuery.UML2Message"); //$NON-NLS-1$
		}
	}
	
	
	
	// Recursively build EObject containment hierarchy
	private IModelResultEntry buildSearchResultEntryHierarchy(IModelResultEntry intermediate, Object resource, Element current, Element leaf) {
		if (current instanceof Element) {
			IModelResultEntry entryContainer = new EcoreModelSearchResultEntry(null, resource, current, false);
			entryContainer.addChildren(intermediate);
			intermediate.setParent(entryContainer);
			return buildSearchResultEntryHierarchy(entryContainer, resource, ((Element)current).getOwner(), leaf);
		} else {
			return intermediate;
		}
	}
	
	@Override
	public IModelSearchQueryEvaluator<IModelSearchQuery, ?> getEvaluator() {
		evaluator = getModelSearchParameters().getEvaluator();
		return evaluator!=null?evaluator:(evaluator=new UML2TextualModelSearchQueryEvaluator<IModelSearchQuery, Object>());
	}
	
	
	@Override
	public IModelResultEntry buildSearchResultEntryHierarchy(Object resource, Object o) {
		EcoreModelSearchResultEntry mesre = new EcoreModelSearchResultEntry(null, resource, o, true);
		if (o instanceof Element) {
			Element c = (Element) o;
		    return buildSearchResultEntryHierarchy(mesre, resource, c.getOwner(), c);
		}
		// Just in case we could deal with some nested exotic objects without containment notions ^^
		return mesre;
	}
}
