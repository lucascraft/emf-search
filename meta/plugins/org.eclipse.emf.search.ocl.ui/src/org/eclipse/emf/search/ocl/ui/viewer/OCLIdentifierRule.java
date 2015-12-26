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
 * $Id: OCLIdentifierRule.java,v 1.2 2008/01/29 20:30:15 lbigearde Exp $
 */

package org.eclipse.emf.search.ocl.ui.viewer;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;


/**
 * Rule to match OCL identifiers.
 * 
 * @author Christian W. Damus (cdamus)
 */
class OCLIdentifierRule
    extends WordRule {
    
    OCLIdentifierRule() {
        super(new OCLKeywordDetector(), new Token(new TextAttribute(null)));
    }
    
    private static class OCLKeywordDetector
        implements IWordDetector {
    
        public boolean isWordPart(char c) {
            return isWordStart(c) || c == '$' || (c >= '0' && c <= '9');
        }
    
        public boolean isWordStart(char c) {
            return c == '_' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }
    }
}
