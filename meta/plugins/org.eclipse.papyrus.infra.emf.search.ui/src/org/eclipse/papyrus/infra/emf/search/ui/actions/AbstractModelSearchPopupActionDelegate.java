/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchPopupActionDelegate.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - moved action to lower level package
 ******************************************************************************/
package org.eclipse.papyrus.infra.emf.search.ui.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * Action contributable to Ecore diagram editor popup menus for model search purposes
 * or a simple regular popup menu action IObjectActionDelegate implementation.
 * 
 * <p>
 * <ul>
 * <li><b>Case 1: Normal Popup action:</b></li>
 * <p>
 * Everytime user right clicks on a selection enabled according the popup action extension point enablement rule,
 * the run method is triggered.
 * </p>
 * <li><b>Case 2: GMF Diagram editor popup contribution:</b></li>
 * <p>
 * Everytime user right clicks on a selection having one of the following action ID registred,
 * this provider returns a new instance of the requested Action implementation.
 * </p>
 * </ul>
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public abstract class AbstractModelSearchPopupActionDelegate  extends Action implements IObjectActionDelegate{
	protected IStructuredSelection eObjectSelection;

	public AbstractModelSearchPopupActionDelegate(String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
		setEnabled(true);
	}
	
	protected void refreshTargetFromCrurentWorkbenchSelection() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page.getActiveEditor() != null) {
			if (page.getActiveEditor().getEditorSite() != null) {
				ISelection selection = page.getActiveEditor().getEditorSite().getSelectionProvider().getSelection();
				if (selection instanceof IStructuredSelection) {
					Object o = ((IStructuredSelection)selection).getFirstElement();
					if (o instanceof IAdaptable) {
						Object a = ((IAdaptable)o).getAdapter(EObject.class);
						if (a instanceof EObject) {
							eObjectSelection = new StructuredSelection(a);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	@Override
	public void run() {
		run(this);
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			eObjectSelection = (IStructuredSelection)selection;
		}
	}

	/**
	 * Mainly called from Ecore Diagram Editors
	 */
	@Override
	public void runWithEvent(Event event) {
		refreshTargetFromCrurentWorkbenchSelection();
		run();
	}
	public void run(IAction action) {
		run();		
	}
}
