/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: TextualSettingsImpl.java,v 1.2 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.search.codegen.model.generator.EStringAccessor;
import org.eclipse.emf.search.codegen.model.generator.GeneratorPackage;
import org.eclipse.emf.search.codegen.model.generator.TextualSettings;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Textual Settings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.TextualSettingsImpl#getEStringAccessors <em>EString Accessors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextualSettingsImpl extends EObjectImpl implements TextualSettings {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * The cached value of the '{@link #getEStringAccessors() <em>EString Accessors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEStringAccessors()
	 * @generated
	 * @ordered
	 */
	protected EList<EStringAccessor> eStringAccessors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextualSettingsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeneratorPackage.Literals.TEXTUAL_SETTINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EStringAccessor> getEStringAccessors() {
		if (eStringAccessors == null) {
			eStringAccessors = new EObjectResolvingEList<EStringAccessor>(EStringAccessor.class, this, GeneratorPackage.TEXTUAL_SETTINGS__ESTRING_ACCESSORS);
		}
		return eStringAccessors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GeneratorPackage.TEXTUAL_SETTINGS__ESTRING_ACCESSORS:
				return getEStringAccessors();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GeneratorPackage.TEXTUAL_SETTINGS__ESTRING_ACCESSORS:
				getEStringAccessors().clear();
				getEStringAccessors().addAll((Collection<? extends EStringAccessor>)newValue);
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
			case GeneratorPackage.TEXTUAL_SETTINGS__ESTRING_ACCESSORS:
				getEStringAccessors().clear();
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
			case GeneratorPackage.TEXTUAL_SETTINGS__ESTRING_ACCESSORS:
				return eStringAccessors != null && !eStringAccessors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TextualSettingsImpl
