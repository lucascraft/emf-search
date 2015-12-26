/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: TextualSettings.java,v 1.2 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Textual Settings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.TextualSettings#getEStringAccessors <em>EString Accessors</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getTextualSettings()
 * @model
 * @generated
 */
public interface TextualSettings extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * Returns the value of the '<em><b>EString Accessors</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EString Accessors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EString Accessors</em>' reference list.
	 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getTextualSettings_EStringAccessors()
	 * @model
	 * @generated
	 */
	EList<EStringAccessor> getEStringAccessors();

} // TextualSettings
