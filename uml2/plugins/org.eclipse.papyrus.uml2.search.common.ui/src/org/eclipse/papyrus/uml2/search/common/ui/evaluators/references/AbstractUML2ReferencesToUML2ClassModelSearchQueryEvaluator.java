/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. 
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator.java
 *
 * Contributors:
 *    Lucas Bigeardel (Anyware Technologies) - EMFT Search integrations
 *******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.evaluators.references;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.papyrus.uml2.search.common.ui.l10n.Messages;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;

public abstract class AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator<Q extends IModelSearchQuery, T extends Resource> implements
		IModelSearchQueryEvaluator<Q, T> {
	protected NamedElement namedElement;
	public AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator(NamedElement clazz) {
		namedElement = clazz;
	}
	
	public String getLabel() {
		return Messages.getString("UML2ReferencesToUML2ClassModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
	
	protected Property getMemberEnd(Association association, boolean sourceNotTarget){
		return (Property)association.getMemberEnds().get(sourceNotTarget ? 0 : 1);
	}
	
	protected Property getSourceEnd(Association association){
		return getMemberEnd(association, true);
	}

	protected Property getTargetEnd(Association association){
		return getMemberEnd(association, false);
	}

	protected void _computeAssociationForGivenClassifier(
			Classifier element,
			Q query, 
			T resource,
			List<Object> matches,
			AggregationKind k,
			boolean notify
		) {
			for (EStructuralFeature.Setting nonNavigableInverseReference : UML2Util.getNonNavigableInverseReferences(element)) {
				if (nonNavigableInverseReference.getEStructuralFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
					EObject eObject = nonNavigableInverseReference.getEObject();
					if (eObject instanceof Property) {
						Association association = ((Property) eObject).getAssociation();
						AggregationKind kind = ((Property) eObject).getAggregation();
						if (kind == k) {
							matches.add(processMatch(association, query, resource, notify));
						}
					}
				}
			}
	}
	
	private List<Object> _computeAssociations(Q query, T resource,  AggregationKind k, boolean notify) {
		List<Object> matches = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			_computeAssociationForGivenClassifier((Classifier)namedElement, query, resource, matches, k, notify);
		}
		return matches;
	}
	
	protected List<Object> computeAssociations(Q query, T resource, boolean notify) {
		return _computeAssociations(query, resource, AggregationKind.NONE_LITERAL, notify);
	}

	protected List<Object> computeAggregations(Q query, T resource, boolean notify) {
		return _computeAssociations(query, resource, AggregationKind.SHARED_LITERAL, notify);
	}
	
	protected List<Object> computeCompositions(Q query, T resource,  boolean notify) {
		return _computeAssociations(query, resource, AggregationKind.COMPOSITE_LITERAL, notify);
	}
	
	protected List<Object> computeGeneralizations(Q query, T resource,  boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Generalization generalization : ((Classifier)namedElement).getGeneralizations()) {
				elements.add(generalization);
				elements.add(processMatch(generalization, query, resource, notify));
			}
		}
		return elements;
	}
	
	protected List<Object> computeRealizations(Q query, T resource, boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Dependency dependency : namedElement.getClientDependencies()) {
				if (dependency instanceof Realization && ! (dependency instanceof Substitution)) {
					elements.add(processMatch(dependency, query, resource, notify));
				}
			}
		}
		return elements;
	}
	
	protected List<Object> computeAbstractions(Q query, T resource, boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Dependency dependency : namedElement.getClientDependencies()) {
				if (	!(dependency instanceof Abstraction)
						&& !(dependency instanceof Usage)
						&& !(dependency instanceof Realization)
						&& !(dependency instanceof Substitution)) 
				{
					elements.add(processMatch(dependency, query, resource, notify));
				}
			}
		}
		return elements;
	}
	
	protected List<Object> computeDependencies(Q query, T resource, boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Dependency dependency : namedElement.getClientDependencies()) {
				elements.add(processMatch(dependency, query, resource, notify));
			}
		}
		return elements;
	}
	
	protected List<Object> computeSubstitutions(Q query, T resource, boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Dependency dependency : namedElement.getClientDependencies()) {
				if (dependency instanceof Substitution) {
					elements.add(processMatch(dependency, query, resource, notify));
				}
			}
		}
		return elements;
	}
	
	protected List<Object> computeUsages(Q query, T resource, boolean notify) {
		List<Object> elements = new ArrayList<Object>();
		if (namedElement instanceof Classifier) {
			for (Dependency dependency : namedElement.getClientDependencies()) {
				if (dependency instanceof Usage) {
					elements.add(processMatch(dependency, query, resource, notify));
				}
			}
		}
		return elements;
	}
	
	protected Object processMatch(EObject o, Q query, T resource, boolean notify) {
		URI eClassURI = o.eResource().getURI();
		if (resource.getURI().equals(eClassURI)) {
			query.processSearchResultMatching(resource, o, notify);
		}
		return o;
	}

	public NamedElement getNamedElement() {
		return namedElement;
	}

	public void setNamedElement(NamedElement e) {
		namedElement = e;
	}
}
