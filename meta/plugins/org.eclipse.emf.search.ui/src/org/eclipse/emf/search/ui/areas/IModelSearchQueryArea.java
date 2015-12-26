/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * IModelSearchQueryArea.java
 * 
 * Contributors: 
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - add target meta elements listening support
 ******************************************************************************/
package org.eclipse.emf.search.ui.areas;

import org.eclipse.emf.search.core.engine.IModelSearchQueryExpressionProvider;
import org.eclipse.emf.search.core.engine.ITargetMetaElementProvider;
import org.eclipse.emf.search.core.engine.ITargetMetaElementSelectionProvider;
import org.eclipse.emf.search.core.engine.ITargetMetaModelHandler;

/**
 * Defines specialized Model Search Area API contracts user must implement in order 
 * to graphically handle Model Search Query expression editing.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.4.0
 *
 */
public interface IModelSearchQueryArea extends
	IModelSearchQueryExpressionProvider,
	ITargetMetaModelHandler,
	ITargetMetaElementProvider,
	ITargetMetaElementSelectionProvider
{
	
}
