/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * OpenUML2ClassDiagramHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.diagram.clazz.search.handlers;

import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.search.ui.handlers.AbstractOpenDiagramHandler;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.util.SelectInDiagramHelper;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.diagram.clazz.part.UMLDiagramEditorUtil;

/**
 * This entity handles UML2 diagram in terms of model search matched elements selection.
 * 
 * It basically handles selection/reveal specifically for GMF based UML2 diagrams.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class OpenUML2ClassDiagramHandler extends  AbstractOpenDiagramHandler {

	protected String getDiagramFileExtension() {
		return "umlclass"; //$NON-NLS-1$
	}

	@Override
	protected String getDiagramEditorID() {
		return "org.eclipse.uml2.diagram.clazz.part.UMLDiagramEditorID"; //$NON-NLS-1$
	}
	
	@Override
	protected void selectElements(IEditorPart editorPart, List<Object> selection) {
		DiagramEditor classDiagramEditor = null;
		if (editorPart instanceof DiagramEditor) {
			classDiagramEditor = (DiagramEditor)editorPart;
			if (selection!= null && !selection.isEmpty()) {
				DiagramEditPart diagramEditPart = classDiagramEditor.getDiagramEditPart();
				
				EObject target = (EObject)selection.get(0);
				
				HashSet<EObject> targetSet = new HashSet<EObject>();
				targetSet.add(target);
				
				UMLDiagramEditorUtil.LazyElement2ViewMap map = new UMLDiagramEditorUtil.LazyElement2ViewMap(diagramEditPart.getDiagramView(), targetSet);
				
				View view = UMLDiagramEditorUtil.findView(diagramEditPart, target, map);
				
				diagramEditPart.getViewer().reveal((EditPart)classDiagramEditor.getDiagramGraphicalViewer().getEditPartRegistry().get(view));

				SelectInDiagramHelper.selectElement(view);
			}
		}
	}
}
