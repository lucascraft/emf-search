/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITransformation.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.core.internal.replace.provisional;

import org.eclipse.core.runtime.IStatus;

/**
 * API contract for a meta element valuation transformation
 * 
 * @param <E> Modified meta element
 * @param <Q> Currently considered query matching the <E&gt; element to be modified
 * @param <V> Valuation to be applied by &lt;T&gt; transformation in order &lt;E&gt; element to be transformed
 * @param <R> Result of transformation
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public interface ITransformation<E, Q, V, R> {
	/**
	 * Check whether a transformation is valid or not for a given &lt;T&gt; target, &lt;E&gt; element
	 * in context of &lt;Q&gt; target
	 * 
	 * @return true if transformation is valid, false otherwise
	 */
	boolean isValid();
	
	/**
	 * Actual transformation operation handler
	 * 
	 * @return {@link IStatus} OK if success, CANCEL otherwise
	 */
	IStatus perform();
	
	/**
	 * Getter for currently modified &lt;E&gt; meta element
	 * 
	 * @return currently modified meta element
	 */
	public E getModifiedElement();
	
	/**
	 * Getter for currently evaluated &lt;Q&gt; query
	 * 
	 * @return currently evaluated &lt;Q&gt; query
	 */
	public Q getQuery();
	
	/**
	 * Getter for actual &lt;V&gt; valuation applied to matched &lt;E&gt; meta element during &lt;Q&gt;
	 * query evaluation.
	 * 
	 * @return &lt;V&gt; valuation applied to matched &lt;E&gt; meta element
	 */
	public V getValuation();
	
	/**
	 * &lt;R&gt; result of the computed from booth &lt;Q&gt; query match evaluation on &lt;E&gt; meta element
	 * 
	 * @return &lt;R&gt; non null result for given &lt;E&gt; transformed element
	 */
	public R getResult();
}
