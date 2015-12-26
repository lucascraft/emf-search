/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: EStringAccessorImpl.java,v 1.1 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator.impl;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenTypedElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.search.codegen.model.generator.EStringAccessor;
import org.eclipse.emf.search.codegen.model.generator.GeneratorPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EString Accessor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl#getGenTypedElement <em>Gen Typed Element</em>}</li>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl#getGenPackage <em>Gen Package</em>}</li>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl#getLiteral <em>Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EStringAccessorImpl extends EObjectImpl implements EStringAccessor {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * The cached value of the '{@link #getGenTypedElement() <em>Gen Typed Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenTypedElement()
	 * @generated
	 * @ordered
	 */
	protected GenTypedElement genTypedElement;

	/**
	 * The cached value of the '{@link #getGenPackage() <em>Gen Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenPackage()
	 * @generated
	 * @ordered
	 */
	protected GenPackage genPackage;

	/**
	 * The default value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final String LITERAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected String literal = LITERAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStringAccessorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeneratorPackage.Literals.ESTRING_ACCESSOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenTypedElement getGenTypedElement() {
		if (genTypedElement != null && genTypedElement.eIsProxy()) {
			InternalEObject oldGenTypedElement = (InternalEObject)genTypedElement;
			genTypedElement = (GenTypedElement)eResolveProxy(oldGenTypedElement);
			if (genTypedElement != oldGenTypedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT, oldGenTypedElement, genTypedElement));
			}
		}
		return genTypedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenTypedElement basicGetGenTypedElement() {
		return genTypedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenTypedElement(GenTypedElement newGenTypedElement) {
		GenTypedElement oldGenTypedElement = genTypedElement;
		genTypedElement = newGenTypedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT, oldGenTypedElement, genTypedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenPackage getGenPackage() {
		if (genPackage != null && genPackage.eIsProxy()) {
			InternalEObject oldGenPackage = (InternalEObject)genPackage;
			genPackage = (GenPackage)eResolveProxy(oldGenPackage);
			if (genPackage != oldGenPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE, oldGenPackage, genPackage));
			}
		}
		return genPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenPackage basicGetGenPackage() {
		return genPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenPackage(GenPackage newGenPackage) {
		GenPackage oldGenPackage = genPackage;
		genPackage = newGenPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE, oldGenPackage, genPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLiteral(String newLiteral) {
		String oldLiteral = literal;
		literal = newLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GeneratorPackage.ESTRING_ACCESSOR__LITERAL, oldLiteral, literal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT:
				if (resolve) return getGenTypedElement();
				return basicGetGenTypedElement();
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE:
				if (resolve) return getGenPackage();
				return basicGetGenPackage();
			case GeneratorPackage.ESTRING_ACCESSOR__LITERAL:
				return getLiteral();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT:
				setGenTypedElement((GenTypedElement)newValue);
				return;
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE:
				setGenPackage((GenPackage)newValue);
				return;
			case GeneratorPackage.ESTRING_ACCESSOR__LITERAL:
				setLiteral((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT:
				setGenTypedElement((GenTypedElement)null);
				return;
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE:
				setGenPackage((GenPackage)null);
				return;
			case GeneratorPackage.ESTRING_ACCESSOR__LITERAL:
				setLiteral(LITERAL_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_TYPED_ELEMENT:
				return genTypedElement != null;
			case GeneratorPackage.ESTRING_ACCESSOR__GEN_PACKAGE:
				return genPackage != null;
			case GeneratorPackage.ESTRING_ACCESSOR__LITERAL:
				return LITERAL_EDEFAULT == null ? literal != null : !LITERAL_EDEFAULT.equals(literal);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (literal: ");
		result.append(literal);
		result.append(')');
		return result.toString();
	}

} //EStringAccessorImpl
