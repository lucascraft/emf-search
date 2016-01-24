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
 * $Id: OCLCollectionTupleRule.java,v 1.2 2008/01/29 20:30:16 lbigearde Exp $
 */

package org.eclipse.papyrus.infra.emf.search.ocl.ui.viewer;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;


/**
 * A rule that matches the collection and tuple literal type identifiers and
 * braces.
 * 
 * @author Christian W. Damus (cdamus)
 */
class OCLCollectionTupleRule
    extends WordRule {

    private static final String[] KEYWORDS = {
        "Set", //$NON-NLS-1$
        "OrderedSet", //$NON-NLS-1$
        "Bag", //$NON-NLS-1$
        "Sequence", //$NON-NLS-1$
        "Tuple", //$NON-NLS-1$
    };

    private static final String[] BRACES = {
        "{", //$NON-NLS-1$
        "}", //$NON-NLS-1$
        "}}", //$NON-NLS-1$
        "}}}", //$NON-NLS-1$
    };
    
    /**
     * @param detector
     */
    OCLCollectionTupleRule(ColorManager colorManager, boolean isBraces) {
        super(new CollectionTupleDetector(isBraces));
        
        IToken token = new Token(new TextAttribute(
            colorManager.getColor(ColorManager.COLLECTION_TUPLE), null, SWT.BOLD));
        
        if (isBraces) {
            for (String word : BRACES) {
                addWord(word, token);
            }
        } else {
            for (String word : KEYWORDS) {
                addWord(word, token);
            }
        }
    }
    
    private static class CollectionTupleDetector implements IWordDetector {
        private final boolean isBraces;
        
        CollectionTupleDetector(boolean isBraces) {
            this.isBraces = isBraces;
        }
        
        public boolean isWordPart(char c) {
            return isBraces? (c == '{') || (c == '}') :
                (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }

        public boolean isWordStart(char c) {
            return isWordPart(c);
        }
    }
}
