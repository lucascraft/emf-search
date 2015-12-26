/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchTransformation.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.internal.replace.provisional;

/**
 * Abstract implementation of accessors/contructors of the {@link ITransformation}
 * 
 * @see ITransformation
 * @see AbstractTextualModelSearchReplace
 * @see IModelSearchReplaceHandler
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 * @since 0.7.0
 * 
 * @param <E> Meta element the T transformation will apply on
 * @param <Q> Query which results will possibly get impacted with T transformation
 * @param <V> Valuation that drives the E element T transformation processing
 * @param <R> Result of a T transformation
 */
public abstract class AbstractModelSearchTransformation<E, Q, V, R> implements ITransformation<E, Q, V, R> {
	
	/**
	 * Meta element the T transformation will apply on
	 */
	private E element;
	
	/**
	 * Query which results will possibly get impacted with T transformation
	 */
	private Q query;
	
	/**
	 * V valuation that drives the E element T transformation processing
	 */
	private V valuation;

	/**
	 * Constructor
	 */
	public AbstractModelSearchTransformation(E element, Q query, V valuation) {
		this.element = element;
		this.query = query;
		this.valuation = valuation;
	}
	
	/**
	 * Getter for the E modified element
	 */
	public E getModifiedElement() {
		return element;
	}
	
	/**
	 * Getter for the Q query which results will possibly have the T transformation applied 
	 */
	public Q getQuery() {
		return query;
	}
	
	/**
	 * Getter for the R result of a T transformation
	 */
	public abstract R getResult();
	
	/**
	 * Getter for V valuation that drives the E element T transformation processing
	 */
	public V getValuation() {
		return valuation;
	}
}
