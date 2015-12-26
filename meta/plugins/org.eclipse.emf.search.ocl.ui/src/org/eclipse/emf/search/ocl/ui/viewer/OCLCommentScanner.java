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
 * $Id: OCLCommentScanner.java,v 1.2 2008/01/29 20:30:15 lbigearde Exp $
 */

package org.eclipse.emf.search.ocl.ui.viewer;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;


/**
 * Syntax-highlighting scanner for OCL comments.  I consists of no rules because
 * all of the text in a comment should have the same style.
 * 
 * @author Christian W. Damus (cdamus)
 */
class OCLCommentScanner
    extends RuleBasedScanner {

    OCLCommentScanner(ColorManager manager) {
        super();
        
        setRules(new IRule[0]);
    }
}
