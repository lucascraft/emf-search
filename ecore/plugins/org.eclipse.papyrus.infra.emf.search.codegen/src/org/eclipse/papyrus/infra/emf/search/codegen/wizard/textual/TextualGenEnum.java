/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * TextualGenEnum.java
 * 
 * Contributors: 
 * 			Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.wizard.textual;

/**
 * Describe all possible Model Search codegen kind
 * 
 * <ul>
 * <li> <b>NONE</b> : genrating nothing
 * <li> <b>CORE</b> : generating only search.core plugin
 * <li> <b>UI</b> : generating only search.core plugin
 * <li> <b>CORE_AND_UI</b> : generating search.core & search.ui plugins
 * </ul>
 * 
 * @author lucas
 *
 */
public enum TextualGenEnum {
	NONE,
	CORE, 
	UI, 
	CORE_AND_UI
}
