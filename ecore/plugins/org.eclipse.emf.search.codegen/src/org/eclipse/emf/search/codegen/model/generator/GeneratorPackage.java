/**
 *   Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation
 * 		Lucas Bigeardel - update EPL header
 *
 * $Id: GeneratorPackage.java,v 1.3 2008/04/10 23:32:22 lbigearde Exp $
 */
package org.eclipse.emf.search.codegen.model.generator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.search.codegen.model.generator.GeneratorFactory
 * @model kind="package"
 * @generated
 */
public interface GeneratorPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "  Copyright (c) 2007-2008 Anyware Technologies and others. All rights reserved. This program   and the accompanying materials are made available under the terms of the   Eclipse Public License v1.0 which accompanies this distribution, and is   available at http://www.eclipse.org/legal/epl-v10.html        Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and   implementation\r\t\tLucas Bigeardel - update EPL header";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "generator";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/search/generator/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "EMS";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GeneratorPackage eINSTANCE = org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl <em>Model Search Gen Settings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getModelSearchGenSettings()
	 * @generated
	 */
	int MODEL_SEARCH_GEN_SETTINGS = 0;

	/**
	 * The feature id for the '<em><b>Gen Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Textual Settings</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS = 1;

	/**
	 * The number of structural features of the '<em>Model Search Gen Settings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_SEARCH_GEN_SETTINGS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.TextualSettingsImpl <em>Textual Settings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.TextualSettingsImpl
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getTextualSettings()
	 * @generated
	 */
	int TEXTUAL_SETTINGS = 1;

	/**
	 * The feature id for the '<em><b>EString Accessors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_SETTINGS__ESTRING_ACCESSORS = 0;

	/**
	 * The number of structural features of the '<em>Textual Settings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXTUAL_SETTINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl <em>EString Accessor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl
	 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getEStringAccessor()
	 * @generated
	 */
	int ESTRING_ACCESSOR = 2;

	/**
	 * The feature id for the '<em><b>Gen Typed Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_ACCESSOR__GEN_TYPED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Gen Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_ACCESSOR__GEN_PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_ACCESSOR__LITERAL = 2;

	/**
	 * The number of structural features of the '<em>EString Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_ACCESSOR_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings <em>Model Search Gen Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Search Gen Settings</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings
	 * @generated
	 */
	EClass getModelSearchGenSettings();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings#getGenModel <em>Gen Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Gen Model</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings#getGenModel()
	 * @see #getModelSearchGenSettings()
	 * @generated
	 */
	EReference getModelSearchGenSettings_GenModel();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings#getTextualSettings <em>Textual Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Textual Settings</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.ModelSearchGenSettings#getTextualSettings()
	 * @see #getModelSearchGenSettings()
	 * @generated
	 */
	EReference getModelSearchGenSettings_TextualSettings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.search.codegen.model.generator.TextualSettings <em>Textual Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Textual Settings</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.TextualSettings
	 * @generated
	 */
	EClass getTextualSettings();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.search.codegen.model.generator.TextualSettings#getEStringAccessors <em>EString Accessors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>EString Accessors</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.TextualSettings#getEStringAccessors()
	 * @see #getTextualSettings()
	 * @generated
	 */
	EReference getTextualSettings_EStringAccessors();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor <em>EString Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString Accessor</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.EStringAccessor
	 * @generated
	 */
	EClass getEStringAccessor();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenTypedElement <em>Gen Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Gen Typed Element</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenTypedElement()
	 * @see #getEStringAccessor()
	 * @generated
	 */
	EReference getEStringAccessor_GenTypedElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenPackage <em>Gen Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Gen Package</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getGenPackage()
	 * @see #getEStringAccessor()
	 * @generated
	 */
	EReference getEStringAccessor_GenPackage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.eclipse.emf.search.codegen.model.generator.EStringAccessor#getLiteral()
	 * @see #getEStringAccessor()
	 * @generated
	 */
	EAttribute getEStringAccessor_Literal();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GeneratorFactory getGeneratorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl <em>Model Search Gen Settings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.ModelSearchGenSettingsImpl
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getModelSearchGenSettings()
		 * @generated
		 */
		EClass MODEL_SEARCH_GEN_SETTINGS = eINSTANCE.getModelSearchGenSettings();

		/**
		 * The meta object literal for the '<em><b>Gen Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_SEARCH_GEN_SETTINGS__GEN_MODEL = eINSTANCE.getModelSearchGenSettings_GenModel();

		/**
		 * The meta object literal for the '<em><b>Textual Settings</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_SEARCH_GEN_SETTINGS__TEXTUAL_SETTINGS = eINSTANCE.getModelSearchGenSettings_TextualSettings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.TextualSettingsImpl <em>Textual Settings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.TextualSettingsImpl
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getTextualSettings()
		 * @generated
		 */
		EClass TEXTUAL_SETTINGS = eINSTANCE.getTextualSettings();

		/**
		 * The meta object literal for the '<em><b>EString Accessors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXTUAL_SETTINGS__ESTRING_ACCESSORS = eINSTANCE.getTextualSettings_EStringAccessors();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl <em>EString Accessor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.EStringAccessorImpl
		 * @see org.eclipse.emf.search.codegen.model.generator.impl.GeneratorPackageImpl#getEStringAccessor()
		 * @generated
		 */
		EClass ESTRING_ACCESSOR = eINSTANCE.getEStringAccessor();

		/**
		 * The meta object literal for the '<em><b>Gen Typed Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESTRING_ACCESSOR__GEN_TYPED_ELEMENT = eINSTANCE.getEStringAccessor_GenTypedElement();

		/**
		 * The meta object literal for the '<em><b>Gen Package</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESTRING_ACCESSOR__GEN_PACKAGE = eINSTANCE.getEStringAccessor_GenPackage();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_ACCESSOR__LITERAL = eINSTANCE.getEStringAccessor_Literal();

	}

} //GeneratorPackage
