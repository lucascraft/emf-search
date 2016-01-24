/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractUML2GOFPatternsModelSearchQueryEvaluator.java
 *
 * Contributors:
 *    Lucas Bigeardel (Anyware Technologies) - EMFT Search integrations
 *******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.evaluators.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.query.conditions.booleans.BooleanCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.conditions.eobjects.TypeRelation;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.papyrus.uml2.search.common.ui.evaluators.references.AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator;
import org.eclipse.papyrus.uml2.search.common.ui.l10n.Messages;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * @author lucas
 *
 * @param <Q> Query
 * @param <T> Target
 */
public abstract class AbstractUML2GOFPatternsModelSearchQueryEvaluator<Q extends IModelSearchQuery, T extends Resource> extends
		AbstractUML2ReferencesToUML2ClassModelSearchQueryEvaluator<Q , T> {
	/**
	 * Constructor
	 */
	public AbstractUML2GOFPatternsModelSearchQueryEvaluator() {
		super(null);
	}
	
	/**
	 * Constructor
	 * @param pkg given UML Package
	 */
	public AbstractUML2GOFPatternsModelSearchQueryEvaluator(NamedElement pkg) {
		super(pkg);
		namedElement = pkg;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return Messages.getString("UML2ReferencesToUML2ClassModelSearchQueryEvaluator.Label"); //$NON-NLS-1$
	}
	
	protected List<Object> computeCompositePattern(Q query, T resource) {
		Collection<Collection<Object>> aggregatedCompositePatternResults = computeCompositePatterns(getPackage(), query, resource);
		for (Collection<Object> col : aggregatedCompositePatternResults) {
			for (Object obj : col) {
				if (obj instanceof ICompositeModelResultEntry) {
					ICompositeModelResultEntry compoundModelResultEntry = (ICompositeModelResultEntry) obj;
					for (IModelResultEntry entry : query.getModelSearchResult().getResultsFlatennedForFile(compoundModelResultEntry.getFile())) {
						System.out.println(entry.toString());
					}
				}
			}
		}
		return new ArrayList<Object>();
	}
	
	
	/**
	 * 
	 * @param pkg
	 * @param query
	 * @param resource
	 * @return
	 */
	protected Collection<Collection<Object>> computeCompositePatterns(Package pkg, Q query, T resource) {
		EObjectCondition classifierTypeCondition = new EObjectTypeRelationCondition(
			    UMLPackage.Literals.CLASSIFIER,
			    TypeRelation.SAMETYPE_OR_SUBTYPE_LITERAL
		);
		
		EObjectAttributeValueCondition abstractAttributeIsTrueAttributeValueCondition = 
			new EObjectAttributeValueCondition(
					UMLPackage.Literals.CLASSIFIER__IS_ABSTRACT, 
					new BooleanCondition(true)
			);
		
		Classifier[] classes = new SELECT(
				new FROM(pkg.getOwnedElements()),
				new WHERE(classifierTypeCondition.AND(abstractAttributeIsTrueAttributeValueCondition))
		).execute().toArray(new Classifier[0]);
		
		return computeCompositePatternFromClasses(Arrays.asList(classes), query);
	}
	
	/**
	 * 
	 * @param classifiers
	 * @param query
	 * @return
	 */
	protected Collection<Collection<Object>> computeCompositePatternFromClasses(Collection<Classifier> classifiers, Q query) {
		for (Classifier classifier : classifiers) {
			if (hasTwoPlusGeneralizationAndOnlyOneAggregationToItself(classifier)) {
				ICompositeModelResultEntry compoundModelResultEntry = query.buildCompositeSearchResultEntryHierarchy(
						getPackage().eResource(), 
						"Composite Pattern"
				);
				query.getModelSearchResult().insert(
						getPackage().eResource(),
						compoundModelResultEntry,
						query.buildSearchResultEntryHierarchy(
								getPackage().eResource(),
								classifier),
						true);
				for (Generalization generalization : getGeneralizationToItself(classifier)) {
					query.getModelSearchResult().insert(
							getPackage().eResource(), 
							compoundModelResultEntry, 
							query.buildSearchResultEntryHierarchy(
									getPackage().eResource(), 
									generalization), 
							true);
				}
				for (Association association : getAggregations(classifier)) {
					query.getModelSearchResult().insert(
							getPackage().eResource(), 
							compoundModelResultEntry, 
							query.buildSearchResultEntryHierarchy(
									getPackage().eResource(), 
									association), 
							true);
				}
			}
		}
		return query.getModelSearchResult().getRootResultHierarchies().values();
	}
	
	/**
	 * Computes {@link Generalization} references to given classifier
	 * 
	 * @param classifier a given {@link Classifier} which user want to know {@link Generalization} pointing to
	 * 
	 * @return Collection of {@link Generalization} pointing to the given classifier
	 */
	private Collection<Association> getAggregations(Classifier classifier) {
		List<Association> associations = new ArrayList<Association>();
		for (EStructuralFeature.Setting nonNavigableInverseReference : UML2Util.getNonNavigableInverseReferences(classifier)) {
			if (nonNavigableInverseReference.getEStructuralFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
				if (nonNavigableInverseReference.getEObject() instanceof Property) {
					Property property = (Property) nonNavigableInverseReference.getEObject();
					switch (property.getAggregation()) {
						case SHARED_LITERAL:
							associations.add(property.getAssociation());
							break;
						default:
							break;
					}
				}		
			}
		}
		return associations;
	}
	
	/**
	 * Computes {@link Generalization} references to given classifier
	 * 
	 * @param classifier a given {@link Classifier} which user want to know {@link Generalization} pointing to
	 * 
	 * @return Collection of {@link Generalization} pointing to the given classifier
	 */
	private Collection<Generalization> getGeneralizationToItself(Classifier classifier) {
		List<Generalization> generalizations = new ArrayList<Generalization>();
		for (EStructuralFeature.Setting nonNavigableInverseReference : UML2Util.getNonNavigableInverseReferences(classifier)) {
			if (nonNavigableInverseReference.getEStructuralFeature() == UMLPackage.Literals.GENERALIZATION__GENERAL) {
				if (nonNavigableInverseReference.getEObject() instanceof Generalization) {
					generalizations.add((Generalization)nonNavigableInverseReference.getEObject());
				}		
			}
		}
		return generalizations;
	}
	
	/**
	 * Evaluates whether or not a {@link Classifier} has 2+ generalization and 1+ aggregation pointing to itself
	 * 
	 * @param classifier {@link Classifier} to be considered
	 * 
	 * @return true if currently considered classifier has 2+ generalization and 1+ aggregation pointing to itself, false otherwise
	 */
	private boolean hasTwoPlusGeneralizationAndOnlyOneAggregationToItself(Classifier classifier) {
		return 
			classifier != null &&
			getAggregations(classifier) != null &&
			getAggregations(classifier).size() == 1 && 
			getGeneralizationToItself(classifier).size() > 1;
	}
	
	/**
	 * Getter for currently considered {@link Package}
	 * 
	 * @return currently considered {@link Package}, null otherwise
	 */
	public Package getPackage() {
		return (Package)getNamedElement();
	}
}
