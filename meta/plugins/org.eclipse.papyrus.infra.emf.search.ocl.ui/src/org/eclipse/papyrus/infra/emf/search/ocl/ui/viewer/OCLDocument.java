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
 * $Id: OCLDocument.java,v 1.3 2008/01/29 20:30:14 lbigearde Exp $
 */

package org.eclipse.papyrus.infra.emf.search.ocl.ui.viewer;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.ocl.engine.IOCLFactory;
import org.eclipse.papyrus.infra.emf.search.ocl.engine.ModelingLevel;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.ocl.helper.OCLHelper;


/**
 * In-memory document implementation for the OCL Console's input area.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class OCLDocument
    extends Document {

    private EObject context;
    private IOCLFactory<Object> oclFactory;
    private ModelingLevel level;
    
    public OCLDocument() {
        super();
        
        IDocumentPartitioner partitioner =
            new FastPartitioner(
                new OCLPartitionScanner(),
                new String[] {
                    OCLPartitionScanner.COMMENT });
        
        partitioner.connect(this);
        setDocumentPartitioner(partitioner);
    }
    
    public void setOCLContext(EObject ctx) {
        context = ctx;
    }
    
    public EObject getOCLContext() {
        return context;
    }
    
    public void setOCLFactory(IOCLFactory<Object> factory) {
        oclFactory = factory;
    }
    
    public IOCLFactory<Object> getOCLFactory() {
        return oclFactory;
    }
    
    public void setModelingLevel(ModelingLevel lvl) {
        level = lvl;
    }
    
    public ModelingLevel getModelingLevel() {
        return level;
    }
    
    List<Choice> getOCLChoices(int offset) {
        if (context == null) {
            return Collections.emptyList();
        }
        
        try {
            String text = get(0, offset);
            
            OCL<?, Object, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> ocl = oclFactory.createOCL();
            OCLHelper<Object, ?, ?, ?> helper = ocl.createOCLHelper();
            
            return helper.getSyntaxHelp(
                level.setContext(helper, context, oclFactory),
                text);
        } catch (BadLocationException e) {
            // just don't provide proposals
            return Collections.emptyList();
        }
    }
}
