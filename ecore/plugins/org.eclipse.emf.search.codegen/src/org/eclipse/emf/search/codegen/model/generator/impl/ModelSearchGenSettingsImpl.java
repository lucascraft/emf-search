/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: ModelSearchGenSettingsImpl.java,v 1.3 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator.impl;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.search.codegen.model.generator.GeneratorPackage;
import org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings;
import org.eclipse.emf.search.codegen.model.generator.TextualSettings;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Search Gen Settings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl#getGenModel <em>Gen Model</em>}</li>
 *   <li>{@link org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl#getTextualSettings <em>Textual Settings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelSearchGenSettingsImpl extends EObjectImpl implements ModelSearchGenSettings {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * The cached value of the '{@link #getGenModel() <em>Gen Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenModel()
	 * @generated
	 * @ordered
	 */
	protected GenModel genModel;

	/**
	 * The cached value of the '{@link #getTextualSettings() <em>Textual Settings</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTextualSettings()
	 * @generated
	 * @ordered
	 */
	protected TextualSettings textualSettings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSearchGenSettingsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GeneratorPackage.Literals.MODEL_SEARCH_GEN_SETTINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenModel getGenModel() {
		if (genModel != null && genModel.eIsProxy()) {
			InternalEObject oldGenModel = (InternalEObject)genModel;
			genModel = (GenModel)eResolveProxy(oldGenModel);
			if (genModel != oldGenModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL, oldGenModel, genModel));
			}
		}
		return genModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenModel basicGetGenModel() {
		return genModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenModel(GenModel newGenModel) {
		GenModel oldGenModel = genModel;
		genModel = newGenModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL, oldGenModel, genModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextualSettings getTextualSettings() {
		if (textualSettings != null && textualSettings.eIsProxy()) {
			InternalEObject oldTextualSettings = (InternalEObject)textualSettings;
			textualSettings = (TextualSettings)eResolveProxy(oldTextualSettings);
			if (textualSettings != oldTextualSettings) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS, oldTextualSettings, textualSettings));
			}
		}
		return textualSettings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextualSettings basicGetTextualSettings() {
		return textualSettings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextualSettings(TextualSettings newTextualSettings) {
		TextualSettings oldTextualSettings = textualSettings;
		textualSettings = newTextualSettings;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS, oldTextualSettings, textualSettings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL:
				if (resolve) return getGenModel();
				return basicGetGenModel();
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS:
				if (resolve) return getTextualSettings();
				return basicGetTextualSettings();
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
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL:
				setGenModel((GenModel)newValue);
				return;
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS:
				setTextualSettings((TextualSettings)newValue);
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
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL:
				setGenModel((GenModel)null);
				return;
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS:
				setTextualSettings((TextualSettings)null);
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
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL:
				return genModel != null;
			case GeneratorPackage.MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS:
				return textualSettings != null;
		}
		return super.eIsSet(featureID);
	}

} //ModelSearchGenSettingsImpl
