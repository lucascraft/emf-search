/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResourceUtils.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.emf.search.tests.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.search.tests.Activator;
import org.eclipse.emf.test.common.EMFTestCommonPlugin;
import org.osgi.framework.Bundle;


public class ModelSearchResourceUtils {

	private static IProject emfCommonTestproject, uml2toolsCommonTestProject, codegenCommonTestProject;
	
	/**
	 * Creates a project to be used for the test.
	 */
	public static void initEcoreModelSearchTestProject()  {
		if (emfCommonTestproject == null) {
			try {
				_initEcoreModelSearchTestProject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a project to be used for the test.
	 */
	public static void initCodegenModelSearchTestProject()  {
		if (codegenCommonTestProject == null) {
			try {
				_initCodegenModelSearchTestProject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a project to be used for the test.
	 */
	public static void initUML2ModelSearchTestProject()  {
		if (uml2toolsCommonTestProject == null) {
			try {
				_initUML2ModelSearchTestProject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	private static void _initEcoreModelSearchTestProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		
		emfCommonTestproject = wsroot.getProject( "modelSearchEcoreTestProject" );
		
		IProjectDescription desc =
			workspace.newProjectDescription(emfCommonTestproject.getName());

		// Create the project if it doesn't exist
		if (!emfCommonTestproject.exists()) {
			emfCommonTestproject.create(desc, null);
		}

		// Open the project if it isn't open
		if (!emfCommonTestproject.isOpen()) {
			emfCommonTestproject.open(null);
		}
		
		copyEcoreModelsFromTestEMFCommon();
	}
	
	private static void _initUML2ModelSearchTestProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		
		uml2toolsCommonTestProject = wsroot.getProject( "modelSearchUML2TestProject" );
		
		IProjectDescription desc =
			workspace.newProjectDescription(uml2toolsCommonTestProject.getName());

		// Create the project if it doesn't exist
		if (!uml2toolsCommonTestProject.exists()) {
			uml2toolsCommonTestProject.create(desc, null);
		}

		// Open the project if it isn't open
		if (!uml2toolsCommonTestProject.isOpen()) {
			uml2toolsCommonTestProject.open(null);
		}
		
		copyUML2ModelsFromTestEMFCommon();
	}
	
	private static void _initCodegenModelSearchTestProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		
		codegenCommonTestProject = wsroot.getProject( "modelSearchCodegenTestProject" );
		
		IProjectDescription desc =
			workspace.newProjectDescription(codegenCommonTestProject.getName());

		// Create the project if it doesn't exist
		if (!codegenCommonTestProject.exists()) {
			codegenCommonTestProject.create(desc, null);
		}

		// Open the project if it isn't open
		if (!codegenCommonTestProject.isOpen()) {
			codegenCommonTestProject.open(null);
		}
		
		copyCodegenModelsFromTestEMFCommon();
	}
	
	private static void copyUML2ModelsFromTestEMFCommon()  throws CoreException, IOException {
		Bundle bundle = Activator.getDefault().getBundle();

		@SuppressWarnings("unchecked")
		Enumeration<URL> genmodels = bundle.findEntries("models/uml2", "*.uml", true);
		while (genmodels.hasMoreElements()) {
			URL url = genmodels.nextElement();
			// Create the resource if it doesn't exist
			Path path = new Path(url.getFile());
			String filename = path.lastSegment();
			if (!uml2toolsCommonTestProject.getFile(filename).exists()) {
				uml2toolsCommonTestProject.getFile(filename).create(FileLocator.resolve(url).openStream(), true, new NullProgressMonitor());
			}
		}
	}
	
	private static void copyCodegenModelsFromTestEMFCommon()  throws CoreException, IOException {
		Bundle bundle = Activator.getDefault().getBundle();

		Path modelsFolderPath = new Path("models");
		if (!codegenCommonTestProject.getFolder(modelsFolderPath).exists()) {
			codegenCommonTestProject.getFolder(modelsFolderPath).create(true, true, new NullProgressMonitor());
		}
		
		Path modelsCodegenFolderPath = new Path("models" + IPath.SEPARATOR + "codegen");
		if (!codegenCommonTestProject.getFolder(modelsCodegenFolderPath).exists()) {
			codegenCommonTestProject.getFolder(modelsCodegenFolderPath).create(true, true, new NullProgressMonitor());
		}

		@SuppressWarnings("unchecked")
		Enumeration<String> modelsDir = bundle.getEntryPaths(IPath.SEPARATOR + "models" + IPath.SEPARATOR + "codegen");
		while (modelsDir.hasMoreElements()) {
			String urlModels = modelsDir.nextElement();
			if (urlModels.endsWith("CVS/")) {
				continue;
			}
			// Create the resource if it doesn't exist
			Path modelsDirPath = new Path(urlModels);
			if (!codegenCommonTestProject.getFolder(modelsDirPath).exists()) {
				codegenCommonTestProject.getFolder(modelsDirPath).create(true, true, new NullProgressMonitor());
			}
			Enumeration<URL> ecoreFiles = bundle.findEntries(urlModels, "*.ecore", true);
			while (ecoreFiles.hasMoreElements()) {
				URL urlEcoreFile = ecoreFiles.nextElement();
				Path ecoreFilePath = new Path(urlEcoreFile.getPath());
				if (!codegenCommonTestProject.getFile(ecoreFilePath).exists()) {
					codegenCommonTestProject.getFile(ecoreFilePath).create(FileLocator.resolve(urlEcoreFile).openStream(), true, new NullProgressMonitor());
				}
			}
//			String dataDirPathName = "models" + IPath.SEPARATOR + "codegen" + IPath.SEPARATOR + modelsDirPath.lastSegment() + IPath.SEPARATOR + "data";
//			Path dataDirPath = new Path(dataDirPathName);
//			if (!codegenCommonTestProject.getFolder(dataDirPath).exists()) {
//				codegenCommonTestProject.getFolder(dataDirPath).create(true, true, new NullProgressMonitor());
//			}
//			Enumeration<URL> dataFiles = bundle.findEntries(dataDirPathName, "*.*", true);
//			while (dataFiles.hasMoreElements()) {
//				URL urlDataFile = dataFiles.nextElement();
//				Path dataFilePath = new Path(urlDataFile.getPath());
//				if (dataFilePath.toOSString().endsWith("CVS") || dataFilePath.toOSString().endsWith("CVS")) {
//					continue;
//				}
//				if (!codegenCommonTestProject.getFile(dataFilePath).exists()) {
//					codegenCommonTestProject.getFile(dataFilePath).create(FileLocator.resolve(urlDataFile).openStream(), true, new NullProgressMonitor());
//				}
//			}
			Enumeration<URL> genModelFiles = bundle.findEntries(urlModels, "*.genmodel", true);
			while (genModelFiles.hasMoreElements()) {
				URL urlGenModelFile = genModelFiles.nextElement();
				Path genmodelFilePath = new Path(urlGenModelFile.getPath());
				if (!codegenCommonTestProject.getFile(genmodelFilePath).exists()) {
					codegenCommonTestProject.getFile(genmodelFilePath).create(FileLocator.resolve(urlGenModelFile).openStream(), true, new NullProgressMonitor());
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private static void copyEcoreModelsFromTestEMFCommon()  throws CoreException, IOException {
		Bundle bundle = EMFTestCommonPlugin.getPlugin().getBundle();

		Enumeration<URL> genmodels = bundle.findEntries("models", "*.genmodel", true);
		while (genmodels.hasMoreElements()) {
			URL url = genmodels.nextElement();
			// Create the resource if it doesn't exist
			Path path = new Path(url.getFile());
			String filename = path.lastSegment();
			if (!emfCommonTestproject.getFile(filename).exists()) {
				emfCommonTestproject.getFile(filename).create(FileLocator.resolve(url).openStream(), true, new NullProgressMonitor());
			}
		}

		Enumeration<URL> ecores = bundle.findEntries("models", "*.ecore", true);
		while (ecores.hasMoreElements()) {
			URL url = ecores.nextElement();
			// Create the resource if it doesn't exist
			Path path = new Path(url.getFile());
			String filename = path.lastSegment();
			if (!emfCommonTestproject.getFile(filename).exists()) {
				emfCommonTestproject.getFile(filename).create(FileLocator.resolve(url).openStream(), true, new NullProgressMonitor());
			}
		}
	}

	public static IProject getCodegenCommonTestProject() {
		return codegenCommonTestProject;
	}

	public static IProject getEMFCommonTestProject() {
		return emfCommonTestproject;
	}
}
