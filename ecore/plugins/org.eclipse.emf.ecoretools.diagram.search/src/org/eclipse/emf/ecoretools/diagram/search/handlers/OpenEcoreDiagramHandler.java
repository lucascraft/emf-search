/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenEcoreDiagramHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.ecoretools.diagram.search.handlers;

import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorUtil;
import org.eclipse.emf.search.ui.handlers.AbstractOpenDiagramHandler;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.util.SelectInDiagramHelper;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;


public class OpenEcoreDiagramHandler extends AbstractOpenDiagramHandler {
	@Override
	protected void selectElements(IEditorPart editorPart, List<Object> selection) {
		DiagramEditor diagramEditor = null;
		if (editorPart instanceof DiagramEditor) {
			diagramEditor = (DiagramEditor)editorPart;
			if (selection!= null && !selection.isEmpty()) {
				DiagramEditPart diagramEditPart = diagramEditor.getDiagramEditPart();
				
				EObject target = (EObject)selection.get(0);
				
				HashSet<EObject> targetSet = new HashSet<EObject>();
				targetSet.add(target);
				
//				EcoreDiagramEditorUtil.LazyElement2ViewMap map = new EcoreDiagramEditorUtil.LazyElement2ViewMap(diagramEditPart.getDiagramView(), targetSet);
//				
//				View view = EcoreDiagramEditorUtil.findView(diagramEditPart, target, map);
//				
//				diagramEditPart.getViewer().reveal((EditPart)diagramEditor.getDiagramGraphicalViewer().getEditPartRegistry().get(view));
//				
//				SelectInDiagramHelper.selectElement(view);
			}
		}
	}
	
	@Override
	protected String getDiagramEditorID() {
		return "org.eclipse.emf.ecore.diagram.part.EcoreDiagramEditorID";
	}
	
	protected String getDiagramFileExtension() {
		return "ecorediag";
	}
}
