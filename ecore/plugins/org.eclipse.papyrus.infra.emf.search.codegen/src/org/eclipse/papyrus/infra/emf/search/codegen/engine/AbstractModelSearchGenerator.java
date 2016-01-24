/*******************************************************************************
 * Copyright (c) 2006,2008 ANYWARE TECHNOLOGIES and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchGenerator.java
 * 
 * Contributors: 
 * 		Jose Alfredo Serrano (Anyware Technologies) - initial API
 * 		Lucas Bigeardel (Anyware Technologies) - Model Search implementations
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.codegen.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.papyrus.infra.emf.search.codegen.Activator;
import org.eclipse.papyrus.infra.emf.search.codegen.constants.GenConstants;
import org.eclipse.papyrus.infra.emf.search.codegen.jet.DefaultJETEmitter;
import org.eclipse.papyrus.infra.emf.search.codegen.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.codegen.model.generator.ModelSearchGenSettings;
import org.osgi.framework.Bundle;

/**
 * Entry point for all Model Search plugins generation.<br>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas BIGEARDEL</a>
 * 
 */
public abstract class AbstractModelSearchGenerator extends
		AbstractModelSearchCodeGenerator {
	
	private class PathEntry {
		
		final static String XML_EXTENSION = "xmljet";
		final static String PROPERTIES_EXTENSION = "propertiesjet";
		final static String MF_EXTENSION = "mfjet";
		final static String JAVA_EXTENSION = "javajet";
		
		final static int XML_TYPE = 1;
		final static int PROPERTIES_TYPE = 2;
		final static int MF_TYPE = 3;
		final static int JAVA_TYPE = 4;
		final static int UNKNOWN_TYPE = 0;
		final static int FOLDER_TYPE = -1;
		
		private Bundle bundle;
		private String pathEntry;
		
		public PathEntry(Bundle bundle, String pathEntry) {
			this.bundle = bundle;
			this.pathEntry = pathEntry;
		}
		
		public Bundle getBundle() {
			return bundle;
		}
		
		public String getPathEntry() {
			return pathEntry;
		}
		
		public IPath getPath() {
			return new Path(pathEntry);
		}
		
		public String getName() {
			return getPath().lastSegment();
		}

		public String getRelativeTo(String anotherEntry) {
			IPath entryPath = new Path(pathEntry);
			IPath anotherEntryPath = new Path(anotherEntry);
			
			return entryPath.removeFirstSegments(entryPath.matchingFirstSegments(anotherEntryPath)).toOSString();
		}
		
		public boolean isFolder() {
			return (pathEntry != null && pathEntry.endsWith("" + IPath.SEPARATOR));
		}
		
		public boolean isSourceDirectory() {
			return isFolder() && GenConstants.SRC_DIRECTORY_SUFFIX.equals(getPath().lastSegment());
		}
		
		public boolean isSourceEntry() {
			return (!GenConstants.SRC_DIRECTORY_SUFFIX.equals(getPath().lastSegment()) && getPathEntry().startsWith( getTemplatesDirectory() + IPath.SEPARATOR + GenConstants.SRC_DIRECTORY_SUFFIX));
		}
		
		public boolean isTemplate() {
			return !isFolder() && getPath().getFileExtension().toLowerCase().endsWith("jet");
		}
		
		private int getType() {
			if (isFolder()) return FOLDER_TYPE;
			
			String fileExtension = getPath().getFileExtension().toLowerCase();

			if (XML_EXTENSION.equals(fileExtension)) return XML_TYPE;
	        if (PROPERTIES_EXTENSION.equals(fileExtension)) return PROPERTIES_TYPE;
			if (MF_EXTENSION.equals(fileExtension)) return MF_TYPE;
			if (JAVA_EXTENSION.equals(fileExtension)) return JAVA_TYPE;

			return UNKNOWN_TYPE;
		}
		
		public boolean isMergeable() {
			int fileType = getType();
			return (fileType == JAVA_TYPE || fileType == PROPERTIES_TYPE); 
		}
		
		public URL getURI() {
			return getBundle().getEntry(getPathEntry());
		}
		
	}
	
	
	/** The model input. This is the root object from the tree */
	protected ModelSearchGenSettings settings;

	/** The project to generate */
	protected IProject generatedProject;

	/**
	 * Class constructor. Creates a new Model Search JET Generator
	 * 
	 * @param modelSearchGeneratorInput
	 *            the model input
	 */
	public AbstractModelSearchGenerator(
			ModelSearchGenSettings modelSearchGeneratorInput) {
		settings = modelSearchGeneratorInput;
	}

	public IProject getGeneratedProject() {
		return generatedProject;
	}

	/**
	 * Launch the generation for the current model search
	 * 
	 * @param monitor
	 *            the monitor for the work progression
	 * @return the generated IProject
	 * 
	 * @throws CoreException
	 *             if the generation fails
	 */
	public Diagnostic generate(Monitor monitor) throws CoreException,
			JETException {
		try {
			generatePluginProject(monitor);
			
			processTemplatesDir(getCodegenBundle(), new PathEntry(getCodegenBundle(), getTemplatesDirectory()), monitor);
			
		} catch (JETException e) {
			e.printStackTrace();
			throw e;
		}

		return Diagnostic.OK_INSTANCE;
	}
	
	public abstract String getTemplatesDirectory();
	
	protected abstract String getProjectIDSuffix();
	
	protected IPath getBasePackagePath() {
		String basePackageName = new String(getProjectIDSuffix());
        return new Path(basePackageName.replace('.', IPath.SEPARATOR));
	}
	private Bundle getCodegenBundle() {
		return Activator.getDefault().getBundle();
	}
	
	private void processIconsDir(Bundle templatesBundle, PathEntry pathEntry, Monitor monitor) throws CoreException, JETException {
		Enumeration<URL> entries = templatesBundle.findEntries(pathEntry.getPathEntry(), "*.gif", false);
		while (entries.hasMoreElements()) {
			URL url = entries.nextElement();
			String entryPath = url.toString();
			
			if (
					entryPath.toLowerCase().endsWith("gif") ||
					entryPath.toLowerCase().endsWith("jpg") ||
					entryPath.toLowerCase().endsWith("jpeg") ||
					entryPath.toLowerCase().endsWith("png")
			) {
				PathEntry entry = new PathEntry(pathEntry.getBundle(), entryPath);
				
				InputStream input;
				try {
					IFile file = getGeneratedProject().getFolder("icons").getFile(entry.getPath().lastSegment());
					file.create(FileLocator.resolve(url).openStream(), IResource.FORCE, BasicMonitor.toIProgressMonitor(monitor));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void processTemplatesDir(Bundle templatesBundle, PathEntry pathEntry, Monitor monitor) throws CoreException, JETException {
		Enumeration<?> entries = templatesBundle.getEntryPaths(pathEntry.getPathEntry());
		
		while (entries.hasMoreElements()) {
			PathEntry entry = new PathEntry(pathEntry.getBundle(), entries.nextElement().toString());
			
			if (entry.isFolder()) {
				if ("cvs".equals(entry.getName().toLowerCase())) { 
					//do nothing
				} else if ("icons".equals(entry.getName().toLowerCase())) {
					// create icons ... flat !!!
					createFolder(getGeneratedProject(), entry, monitor);
					processIconsDir(templatesBundle, entry, monitor);
				} else {
					monitor.subTask(Messages.getString("AbstractModelSearchGenerator.GenDirectoriesLabel") + entry.getPathEntry() + Messages.getString("AbstractModelSearchGenerator.GenDirectoriesText")); //$NON-NLS-1$ //$NON-NLS-2$
					createFolder(getGeneratedProject(), entry, monitor);
					
					//create base package folders as well if current resource is source directory
					if (entry.isSourceDirectory()) {
						createBasePackage(getGeneratedProject(), entry, monitor);
					}
					
					monitor.worked(1);

					processTemplatesDir(templatesBundle, entry, monitor);
				}
			} else {
				processTemplateFile(getGeneratedProject(), pathEntry, entry, monitor);
			}
		}
	}
	
	private IPath constructFullTargetPath (IProject targetProject, PathEntry pathEntry) {
		IPath targetProjectPath = targetProject.getFullPath();
		IPath resultPath = targetProjectPath;

		//if processed the source resource i.e. under SRC_DIRECTORY
		if (pathEntry.isSourceEntry()) {
			String srcPath = pathEntry.getRelativeTo(getTemplatesDirectory() + IPath.SEPARATOR + GenConstants.SRC_DIRECTORY_SUFFIX + IPath.SEPARATOR);
			//construct resulting path containing base package
			resultPath = resultPath.append(IPath.SEPARATOR + GenConstants.SRC_DIRECTORY_SUFFIX).append(getBasePackagePath()).append(srcPath);
		} else {
			resultPath = resultPath.append(IPath.SEPARATOR + pathEntry.getRelativeTo(getTemplatesDirectory()));
		}
		
		//cut off "jet" suffix from the template file extension
		if (resultPath.getFileExtension() != null) {
			String fileExtension = resultPath.getFileExtension();
			if (fileExtension.lastIndexOf("jet") != -1) {
				return resultPath.removeFileExtension().addFileExtension(fileExtension.substring(0, fileExtension.lastIndexOf("jet"))); 
			}
		}

		return resultPath;
		//return targetProject.getFullPath().append(pathEntry.getRelativeTo(getTemplatesDirectory()));
	}
	
	private void createFolder(IProject targetProject, PathEntry folderEntry, Monitor monitor) throws CoreException {
		IFolder targetFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(constructFullTargetPath(targetProject, folderEntry));
		if (!(targetFolder.exists())) {
			targetFolder.create(true, false, BasicMonitor.toIProgressMonitor(monitor));
		}
	}
	
	private void createBasePackage(IProject targetProject, PathEntry srcDirectoryEntry, Monitor monitor) throws CoreException {
		String basePackageName = new String(getProjectIDSuffix()); //$NON-NLS-1$
		
		IPath targetSrcPath = constructFullTargetPath(targetProject, srcDirectoryEntry);
        IPath basePackagePath = new Path(basePackageName.replace('.', IPath.SEPARATOR)); //$NON-NLS-1$

        for (int i = 1; i < basePackagePath.segmentCount() + 1; i++)
        {
            IPath packagePath = targetSrcPath.append(basePackagePath.removeLastSegments(basePackagePath.segmentCount() - i));
      
            IFolder packageFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(packagePath);
            if (!(packageFolder.exists()))
            {
                packageFolder.create(true, false, BasicMonitor.toIProgressMonitor(monitor));
            }
        }
	}
	
	private void processTemplateFile(
			IProject targetProject,
			PathEntry templateFolder,
			PathEntry templatePath,
			Monitor monitor
		) throws CoreException, JETException {
		
		IPath targetFilePath = constructFullTargetPath(targetProject, templatePath);
		
		if (!templatePath.isMergeable()) {
			//do not merge generated stuff with existing. 
			//place generated stuff into the file with the name XXXToMerge in the same directory.
			IFile targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(targetFilePath);
			if (targetFile.exists()) {
				String targetFileName = targetFile.getName();
				String targetFileExtension = targetFile.getFileExtension();
				String toMergeFileName = targetFileName.substring(0, targetFileName.lastIndexOf(targetFileExtension)) + "ToMerge" + targetFileExtension;
				targetFilePath = targetFilePath.removeLastSegments(1).append(toMergeFileName);
			}
		}
		
		applyTemplate(settings, templatePath.getURI().toString(), targetFilePath, false, monitor);
	}
	
//////////////////ALL STUFF BELOW WILL BE REMOVED/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	protected JETEmitter createJETEmitter(String templateURI) {
		return new DefaultJETEmitter(templateURI) {
			public void initialize(Monitor monitor) throws JETException {
				addVariable(GenConstants.MODEL_SEARCH_PLUGIN_VAR, GenConstants.MODEL_SEARCH_PLUGIN_ID);
				addVariable(GenConstants.MODEL_SEARCH_PLUGIN_UI_VAR, GenConstants.MODEL_SEARCH_PLUGIN_UI_ID);
				addVariable(GenConstants.MODEL_SEARCH_ECORE_PLUGIN_VAR,	GenConstants.MODEL_SEARCH_ECORE_PLUGIN_ID);
				addVariable(GenConstants.MODEL_SEARCH_ECORE_PLUGIN_UI_VAR, GenConstants.MODEL_SEARCH_ECORE_PLUGIN_UI_ID);
				addVariable(GenConstants.MODEL_SEARCH_CODEGEN_PLUGIN_VAR, GenConstants.MODEL_SEARCH_CODEGEN_PLUGIN_ID);

				// TODO: LB to find out why this SWT plugin ID naming is not
				// consistant accross platforms : should it be ?
				if (Platform.getOS().equals(Platform.OS_MACOSX)) {
					addVariable("SWT", "org.eclipse.swt." + Platform.getWS() + "." + Platform.getOS()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				} else {
					addVariable("SWT", "org.eclipse.swt." + Platform.getWS() + "." + Platform.getOS() + "." + Platform.getOSArch()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				super.initialize(monitor);
			}
		};
	}

	// //////////// Public API Methods ///////////////
	// ///////////////////////////////////////////////

	/**
	 * Returns IPath of the given packageName. The parameter may have the
	 * following format: <code>org.eclipse.emf.something.else</code> The
	 * return IPath will replace the '.' into IPaths Separators
	 * 
	 * @param packageName
	 *            the package in java format
	 * @return IPath of the given package.
	 */
	public static IPath getPackageIPath(String packageName) {
		return new Path(new String(packageName).replace('.', IPath.SEPARATOR));
	}

	// ///////////// Helpers ///////////////
	// /////////////////////////////////////

	private Diagnostic generatePluginProject(Monitor monitor) throws JETException, CoreException {
		monitor.subTask(Messages.getString("AbstractModelSearchGenerator.GenProjects")); //$NON-NLS-1$
		IProject project = createEMFProject(settings.getGenModel().getModelPluginID() + new String(getProjectIDSuffix())); //$NON-NLS-1$
		generatedProject = project;
		monitor.worked(1);
		return Diagnostic.OK_INSTANCE;
	}

	public ModelSearchGenSettings getSettings() {
		return settings;
	}
}