/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryExpressionProvider.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.engine;

/**
 * Entity describing query provider API contract.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * 
 * @since 0.2.0
 */
public interface IModelSearchQueryExpressionProvider {
    /**
     * <p>
     * User wanting to access current Model Search Query Expression
     * should implement this method
     * </p>
     * <p>
     * Although this doesn't seem very clean conceptually speaking ^^
     * </p>
     * <p>
     * But sometimes, in some special situations,
     * technical needs create some coincidental solutions and may also lead to
     * innovation. (Isn't it ?)
     * </p>
     */
	String getQueryExpression();
}
