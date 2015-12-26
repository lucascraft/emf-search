/**
 * <copyright>
 *
 * Copyright (c) 2007,2008 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Lucas Bigeardel - EMF Search integrations
 *
 * </copyright>
 *
 * $Id: OCLWhitespaceDetector.java,v 1.2 2008/01/29 20:30:16 lbigearde Exp $
 */

package org.eclipse.emf.search.ocl.ui.viewer;

import org.eclipse.jface.text.rules.IWhitespaceDetector;


/**
 * Detector for whitespace in OCL text.
 * 
 * @author Christian W. Damus (cdamus)
 */
class OCLWhitespaceDetector
    implements IWhitespaceDetector {

    public boolean isWhitespace(char c) {
        switch (c) {
            case ' ':
            case '\t':
            case '\n':
            case '\r':
                return true;
            default:
                return false;
        }
    }

}
