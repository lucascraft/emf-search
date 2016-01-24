/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenConstants.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.constants;

import org.eclipse.core.runtime.IPath;
import org.eclipse.papyrus.infra.emf.search.codegen.Activator;

/**
 * Model Search Code Generator Common Constants.
 * 
 * <ul>
 *		<li> Model Search Plugins core directories
 *		<li> Model Search templates directories
 *		<li> Model Search classpath & bundle IDs
 * </ul> 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 *
 */
public final class GenConstants {
	
	public static final String CLASSPATH_VARIABLE_NAME = Activator.PLUGIN_ID;
	
    ///////////////////////////////////////////////////
	// MODEL SEARCH CORE DIRECTORIES  
	///////////////////////////////////////////////////

	public static String ICONS_DIRECTORY_SUFFIX = "icons";
	public static String META_INF_DIRECTORY_SUFFIX = "META-INF";
	public static String SRC_DIRECTORY_SUFFIX = "src";
	
    ///////////////////////////////////////////////////
	// MODEL SEARCH TEMPLATES DIRECTORIES  
	///////////////////////////////////////////////////
	
	public static final String ROOT_TEMPLATES_DIRECTORY = "templates";
    public static final String CORE_TEMPLATES_DIRECTORY = ROOT_TEMPLATES_DIRECTORY + IPath.SEPARATOR + "SEARCH_CORE";
    public static final String UI_TEMPLATES_DIRECTORY = ROOT_TEMPLATES_DIRECTORY + IPath.SEPARATOR + "SEARCH_UI";


    ///////////////////////////////////////////////////
	// MODEL SEARCH CODGEN VARS & PLUGINS IDs  
	///////////////////////////////////////////////////
	
    public static final String MODEL_SEARCH_PLUGIN_VAR = "MODEL_SEARCH_PLUGIN";
    public static final String MODEL_SEARCH_PLUGIN_ID = "org.eclipse.papyrus.infra.emf.search";

    public static final String MODEL_SEARCH_PLUGIN_UI_VAR = "MODEL_SEARCH_PLUGIN_UI";
    public static final String MODEL_SEARCH_PLUGIN_UI_ID = "org.eclipse.papyrus.infra.emf.search.ui";

    public static final String MODEL_SEARCH_ECORE_PLUGIN_VAR = "MODEL_SEARCH_ECORE_PLUGIN";
    public static final String MODEL_SEARCH_ECORE_PLUGIN_ID = "org.eclipse.papyrus.infra.emf.search.ecore";

    public static final String MODEL_SEARCH_ECORE_PLUGIN_UI_VAR = "MODEL_SEARCH_ECORE_PLUGIN_UI";
    public static final String MODEL_SEARCH_ECORE_PLUGIN_UI_ID = "org.eclipse.papyrus.infra.emf.search.ecore.ui";

    public static final String MODEL_SEARCH_CODEGEN_PLUGIN_VAR = "MODEL_SEARCH_CODEGEN_PLUGIN";
    public static final String MODEL_SEARCH_CODEGEN_PLUGIN_ID = "org.eclipse.papyrus.infra.emf.search.codegen";
}
