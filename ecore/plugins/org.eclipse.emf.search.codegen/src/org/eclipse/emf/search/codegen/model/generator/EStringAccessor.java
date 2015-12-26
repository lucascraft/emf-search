/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: EStringAccessor.java,v 1.1 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EString Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenTypedElement <em>Gen Typed Element</em>}</li>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenPackage <em>Gen Package</em>}</li>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getLiteral <em>Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getEStringAccessor()
 * @model
 * @generated
 */
public interface EStringAccessor extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * Returns the value of the '<em><b>Gen Typed Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Typed Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Typed Element</em>' reference.
	 * @see #setGenTypedElement(GenTypedElement)
	 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getEStringAccessor_GenTypedElement()
	 * @model
	 * @generated
	 */
	GenTypedElement getGenTypedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenTypedElement <em>Gen Typed Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen Typed Element</em>' reference.
	 * @see #getGenTypedElement()
	 * @generated
	 */
	void setGenTypedElement(GenTypedElement value);

	/**
	 * Returns the value of the '<em><b>Gen Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Package</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Package</em>' reference.
	 * @see #setGenPackage(GenPackage)
	 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getEStringAccessor_GenPackage()
	 * @model
	 * @generated
	 */
	GenPackage getGenPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenPackage <em>Gen Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen Package</em>' reference.
	 * @see #getGenPackage()
	 * @generated
	 */
	void setGenPackage(GenPackage value);

	/**
	 * Returns the value of the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' attribute.
	 * @see #setLiteral(String)
	 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorPackage#getEStringAccessor_Literal()
	 * @model
	 * @generated
	 */
	String getLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getLiteral <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' attribute.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(String value);

} // EStringAccessor
