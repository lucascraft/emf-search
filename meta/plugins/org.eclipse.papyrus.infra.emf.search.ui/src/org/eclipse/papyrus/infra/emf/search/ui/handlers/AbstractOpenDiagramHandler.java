/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractOpenDiagramHandler.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.search.ui.dialogs.DiagramResourceListSelectionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Abstract implementation for Diagram Open action handler.
 * 
 * Its role is to open a gievn selection into a diagram given its
 * corresponding resource and a list of selected objects. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 *
 */
public abstract class AbstractOpenDiagramHandler implements IOpenDiagramHandler {
	/**
	 * {@inheritDoc}
	 */
	public IStatus openDiagramEditor(String editorID, List<Object> list) {
		List<IResource> diagramFiles = selectDiagramResourceFromDialog();
		if (!diagramFiles.isEmpty()) {
			return openDiagramEditor(diagramFiles.get(0), list);
		}
		return Status.CANCEL_STATUS;
	}

	/**
	 * Opens the editor corresponding to the given diagram {@link IResource} with given
	 * object list as selection
	 *  
	 * @param diagramFile diagram resource to be considered
	 * @param list object selection list to show
	 * 
	 * @return {@link Status} : OK if sucess, CANCEL otherwise
	 */
	protected IStatus openDiagramEditor(IResource diagramFile, List<Object> list) {
		if (diagramFile instanceof IFile) {
			IEditorPart editorPart = null;
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			try {
				editorPart = page.openEditor(new FileEditorInput(
						(IFile) diagramFile), getDiagramEditorID());
			} catch (PartInitException e) {
				e.printStackTrace();
				return Status.CANCEL_STATUS;
			}
			
			if (editorPart instanceof IEditorPart) {
				selectElements(editorPart, list);
			}
		}

		return Status.OK_STATUS;
	}
	
	/**
	 * Users must provide diagram editor ID as it has been contributed to Eclipse UI editor ext point
	 * 
	 * @return diagram editor ID as it has been contributed to Eclipse UI editor ext point
	 */
	protected abstract String getDiagramEditorID();
	
	/**
	 * Action to select elements in an given {@link EditorPart}
	 * 
	 * @param editorPart editor part having to be opened
	 * @param selection object list having to be shown in given editor part
	 */
	protected abstract void selectElements(IEditorPart editorPart, List<Object> selection);
	
	/**
	 * Getter for Diagram Selection list
	 * 
	 * @return Diagram Selection list typed as a {@link IResource}
	 */
	protected List<IResource> selectDiagramResourceFromDialog() {
		List<IResource> list = new ArrayList<IResource>(); 
		DiagramResourceListSelectionDialog d = new DiagramResourceListSelectionDialog(Display.getDefault().getActiveShell(), getDiagramFiles().toArray(new IResource[0]));
		if (d.open() == Window.OK) {
			for (Object o : d.getResult()) {
				if (o instanceof IResource) {
					list.add((IResource)o);
				}
			}
		}
		return list;
	}
	
	/**
	 * Recursively visit IResource tree adding each potential 
	 * participant according to diagram extension.
	 *  
	 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
	 *
	 */
	class DiagramResourceVisitor implements IResourceProxyVisitor  {
		/**
		 * matches of digram participant resources
		 */
		private List<IResource> matches;
		
		/**
		 * Visitor looking for potential participants.
		 */
		public DiagramResourceVisitor() {
			matches = new ArrayList<IResource>();
		}
		
		/**
		 * Visit each resource proxy and add it if potential search participant.
		 */
		public boolean visit(IResourceProxy proxy) throws CoreException {
			switch (proxy.getType()) {
				case IResource.FILE:
					IResource resource = proxy.requestResource();
					if (getDiagramFileExtension().equals(resource.getFileExtension())) {
						matches.add(resource);
					}
					break;
				default:
					break;
			}
			return true;
		}

		/**
		 * Getter for current resources containing matches
		 * 
		 * @return list of  current resources containing matches
		 */
		public List<IResource> getMatches() {
			return matches;
		}
	}
	/**
	 * Abstract definition for Diagram file extension getter
	 *  
	 * @return diagram file extension getter
	 */
	protected abstract String getDiagramFileExtension();

	/**
	 * {@inheritDoc}
	 */
	public List<IResource> getDiagramFiles() {
		try {
			DiagramResourceVisitor diagramResourceVisitor = new DiagramResourceVisitor();
			ResourcesPlugin.getWorkspace().getRoot().accept(diagramResourceVisitor, IResource.DEPTH_INFINITE);
			return diagramResourceVisitor.getMatches();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return new ArrayList<IResource>();
	}
}
