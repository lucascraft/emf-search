/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2RegexReplaceTransformation.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.internal.replace.provisional;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.AbstractModelSearchTransformation;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Implements UML2 Regex Replace Transformation
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2RegexReplaceTransformation extends
AbstractModelSearchTransformation<EObject, IModelSearchQuery, String, String> {
	public UML2RegexReplaceTransformation(NamedElement element, IModelSearchQuery query, String valuation) {
		super(element, query, valuation);
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean isValid() {
		return getModifiedElement() instanceof NamedElement;
	}
	/**
	 * {@inheritDoc}
	 */
	public IStatus perform() {
		try {
			if (getQuery() instanceof EcoreModelSearchQuery) {
				EcoreModelSearchQuery query = (EcoreModelSearchQuery) getQuery();
				String input = ((ENamedElement)getModifiedElement()).getName();
				String expr = getQuery().getQueryExpression();
				
				switch(query.getKind()) {
					case REGULAR_EXPRESSION:
						Pattern pattern = Pattern.compile(expr);
						String newValue = pattern.matcher(input).replaceFirst(getValuation());
						((NamedElement)getModifiedElement()).setName(newValue);
						break;
					case CASE_SENSITIVE:
					case NORMAL_TEXT:
						((NamedElement)getModifiedElement()).setName(getValuation());
						break;
				}
			}		
		} catch (Throwable t) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}
	@Override
	public String getResult() {
		return ((NamedElement)getModifiedElement()).getName();
	}
}
