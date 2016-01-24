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
 * $Id: OCLPartitionScanner.java,v 1.2 2008/01/29 20:30:17 lbigearde Exp $
 */

package org.eclipse.papyrus.infra.emf.search.ocl.ui.viewer;

import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;


/**
 * A rule-based partition scanner for OCL text.
 * 
 * @author Christian W. Damus (cdamus)
 */
class OCLPartitionScanner
    extends RuleBasedPartitionScanner {

    public static final String COMMENT = "__ocl_comment"; //$NON-NLS-1$
    
    OCLPartitionScanner() {
        super();
        
        List<IRule> rules = new java.util.ArrayList<IRule>();
        
        IToken commentToken = new Token(COMMENT);
        
        // rule for single-line comments
        rules.add(new EndOfLineRule("--", commentToken)); //$NON-NLS-1$

        // rule for paragraph comments
        rules.add(new MultiLineRule("/*", "*/", commentToken)); //$NON-NLS-1$ //$NON-NLS-2$
        
        setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
    }
}
