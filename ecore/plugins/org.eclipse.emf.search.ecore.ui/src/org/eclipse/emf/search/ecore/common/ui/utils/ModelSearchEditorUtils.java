/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchEditorUtils.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *      Lucas Bigeardel - added javadoc
 ******************************************************************************/

package org.eclipse.emf.search.ecore.common.ui.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.search.ecore.ui.Activator;
import org.eclipse.emf.search.ui.handlers.IModelElementEditorSelectionHandler;
import org.eclipse.emf.search.ui.pages.ModelEditorOpenEnum;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public final class ModelSearchEditorUtils {
	/**
	 * Obtains the workspace file corresponding to the specified resource, if
	 * it has a platform-resource URI.  Note that the resulting file, if not
	 * <code>null</code>, may nonetheless not actually exist (as the file is
	 * just a handle).
	 * 
	 * @param resource an EMF resource
	 * 
	 * @return the corresponding workspace file, or <code>null</code> if the
	 *    resource's URI is not a platform-resource URI
	 */
	public static IFile getFile(Resource resource) {
        ResourceSet rset = resource.getResourceSet();
        
        return getFile(
            resource.getURI(),
            (rset != null)? rset.getURIConverter() : null);
	}
    
    /**
     * Finds the file corresponding to the specified URI, using a URI converter
     * if necessary (and provided) to normalize it.
     * 
     * @param uri a URI
     * @param converter an optional URI converter (may be <code>null</code>)
     * 
     * @return the file, if available in the workspace
     */
    private static IFile getFile(URI uri, URIConverter converter) {
        if ("platform".equals(uri.scheme()) && (uri.segmentCount() > 2)) { //$NON-NLS-1$
            if ("resource".equals(uri.segment(0))) { //$NON-NLS-1$
                IPath path = new Path(URI.decode(uri.path())).removeFirstSegments(1);
                return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            }
        } else if (uri.isFile() && !uri.isRelative()) {
            return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
                new Path(uri.toFileString()));
        }
        // normalize, to see whether may we can resolve it this time
        if (converter instanceof URIConverter) {
            URI normalized = converter.normalize(uri);
            if (!uri.equals(normalized)) {
               // recurse on the new URI
               return getFile(normalized, converter);
            }
        }
        return null;
    }

    /**
     * Open an editor and set the selection on a given element.
     * 
     * @param handler Model Search Area API contracts user must implement in order to handle graphical editors opening.
     * @param o object to select in the newly opened editor
     * @param mode Enumeration wich literal allows to open a tree or diagram editor
     */
    public static void openEditorWithSelection(IModelElementEditorSelectionHandler handler, EObject o, ModelEditorOpenEnum mode) {
        if (o != null) {
        	IFile file = getFile(o.eResource());
        	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        	try {
				IEditorPart part = IDE.openEditor(page, file, true);
                // check whether the selected EditorPart implements the IEditingDomainProvider interface or not
                if (part instanceof IEditingDomainProvider) {
                    EditingDomain editingDomain = ((IEditingDomainProvider)part).getEditingDomain();
                    Object object = editingDomain.getResourceSet().getEObject(EcoreUtil.getURI(o), true);
                    if (handler != null) {
                    	handler.handleModelSearchElementSelection(part, object, mode);
                    }
                }
			} catch (PartInitException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to open editor !", e));
			}
        }
    }
}
