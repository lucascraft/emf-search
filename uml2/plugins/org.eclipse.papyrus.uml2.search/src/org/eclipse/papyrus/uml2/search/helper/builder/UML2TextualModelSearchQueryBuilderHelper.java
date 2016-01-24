/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2TextualModelSearchQueryBuilderHelper.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.helper.builder;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.scope.IModelSearchScope;
import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.uml2.uml.UMLPackage;

public class UML2TextualModelSearchQueryBuilderHelper extends AbstractUML2TextualModelSearchQueryBuilderHelper {
	
	private static UML2TextualModelSearchQueryBuilderHelper instance;
	
	// Singleton
	public static UML2TextualModelSearchQueryBuilderHelper getInstance() {
		return instance==null?instance = new UML2TextualModelSearchQueryBuilderHelper():instance;
	}
	
	//
	// NORMAL
	//
	public IModelSearchQuery buildGlobalTextualModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, UMLPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, scope, UMLPackage.eNS_URI);
	}
	
	//
	// REGEX
	//
	public IModelSearchQuery buildGlobalRegexModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, UMLPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION, scope, UMLPackage.eNS_URI);
	}
	
	//
	// CASE SENSITIVE
	//
	public IModelSearchQuery buildGlobalCaseSensitiveModelSearchQuery(String pattern, IModelSearchScope<Object, Resource> scope, String nsURI) {
		return buildTextualModelSearchQuery(pattern, UMLPackage.eINSTANCE.getEClassifiers(), ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE, scope, UMLPackage.eNS_URI);
	}
}
