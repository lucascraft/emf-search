/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * RuntimeModelSearchAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - minor changes
 ******************************************************************************/

package org.eclipse.emf.search.examples.ecore.rcp.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class RuntimeModelSearchAction implements IWorkbenchWindowActionDelegate {
	/**
	 * The constructor.
	 */
	public RuntimeModelSearchAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		DirectoryDialog d = new DirectoryDialog(Display.getDefault().getActiveShell());
		
		File f = new File(d.open());
		
		List<File> fileList = new ArrayList<File>();
		fileList.add(f);
		
		IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemDirectoryScope(EcoreTextualEngine.ID, fileList.toArray(new File[0]));
		IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildGlobalTextualModelSearchQuery("*", scope, EcorePackage.eNS_URI);
		
		NewSearchUI.runQueryInBackground(query);
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
}