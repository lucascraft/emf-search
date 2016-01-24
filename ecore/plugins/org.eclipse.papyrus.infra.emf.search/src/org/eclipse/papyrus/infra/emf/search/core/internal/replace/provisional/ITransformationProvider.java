/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ITransformationProvider.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional;

/**
 * API contracts for a meta element transformation provider
 * 
 * @param <E> Modified meta element
 * @param <Q> Currently considered query matching the <E&gt; element to be modified
 * @param <V> Valuation to be applied by &lt;T&gt; transformation in order &lt;E&gt; element to be transformed
 * @param <R> Result of transformation
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.7.0
 */
public interface ITransformationProvider<E, Q, V, R> {
	/**
	 * Getter for {@link ITransformation} model search transformation
	 * 
	 * @param element E meta element the T tranformation will aplly on
	 * @param query Q query which R result will possibly get a T tranformation applied to
	 * @param valuation V valuation that can drive the T transformation processing
	 * 
	 * @return likely a non null currently considered T transformation
	 */
	ITransformation<E, Q, V, R> getTransformation(E element, Q query, V valuation);
}
