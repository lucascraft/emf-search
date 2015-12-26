/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenBaseJDTUtils.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.ui.jdtutils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.search.genmodel.ui.Activator;
import org.eclipse.emf.search.genmodel.ui.l10n.Messages;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.PartInitException;

/**
 * Utility entity mainly offering ways to navigate from GenBase generalizations to
 * its corresponding generated code if any.
 * 
 * Some utilities are also available concerning IType resolution from GenBase objects.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class GenBaseJDTUtils {
	private static GenBaseJDTUtils instance;
	private GenClassSwitchUtil genClassSwitch;
	
	public GenBaseJDTUtils() {
		genClassSwitch = new GenClassSwitchUtil();
	}
	
	/**
	 * Singleton
	 * @return a unique GenBaseJDTUtils instance.
	 */
	public static GenBaseJDTUtils getInstance() {
		return instance == null ? instance = new GenBaseJDTUtils() : instance;
	}
	
	public void openITypeInJavaEditor(IType type) {
		try {
			JavaUI.openInEditor((IJavaElement)type, true, true);
		} catch (PartInitException e) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("GenBaseJDTUtils.OpenITypeInJavaEditorError"), e));
		} catch (JavaModelException e) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("GenBaseJDTUtils.OpenITypeInJavaEditorError"), e));
			e.printStackTrace();
		}
	}
	
	/**
	 * Resolves Java IType from an arbitrary GenBase object.
	 * @param genBase an arbitrary GenBase object.
	 * @return corresponding IType, null otherwise.
	 */
	public IType resolveGenBaseType(GenBase genBase) {
		IProject project = resolveModelProject(genBase);
		if (project instanceof IProject) {
			if (isAnAccessibleJavaProject(project)) {
				return resolveGenBaseType(JavaCore.create(project), computeGeneratedJavaTypeFullyQualifiedName(genBase));
			}
		}
		return null;
	}
	
	private class GenClassSwitchUtil extends GenModelSwitch<GenBase> {
		private String fullyQualifiedName;
		
		public GenClassSwitchUtil() {
			fullyQualifiedName = ""; //$NON-NLS-1$
		}

		@Override
		public GenBase caseGenClass(GenClass object) {
			fullyQualifiedName =	object.isInterface() ?  
										object.getQualifiedInterfaceName() :
											object.getQualifiedClassName();
			return super.caseGenClass(object);
		}

		@Override
		public GenBase caseGenEnum(GenEnum object) {
			fullyQualifiedName = object.getQualifiedName();
			return super.caseGenEnum(object);
		}

		@Override
		public GenBase caseGenPackage(GenPackage object) {
			fullyQualifiedName = object.getQualifiedPackageClassName();
			return super.caseGenPackage(object);
		}

		public String getFullyQualifiedName() {
			return fullyQualifiedName;
		}
		
		@Override
		public GenBase doSwitch(EObject theEObject) {
			fullyQualifiedName = ""; //$NON-NLS-1$
			return super.doSwitch(theEObject);
		}
		@Override
		protected GenBase doSwitch(EClass theEClass, EObject theEObject) {
			fullyQualifiedName = ""; //$NON-NLS-1$
			return super.doSwitch(theEClass, theEObject);
		}
		@Override
		protected GenBase doSwitch(int classifierID, EObject theEObject) {
			fullyQualifiedName = ""; //$NON-NLS-1$
			return super.doSwitch(classifierID, theEObject);
		}
	}
	
	private String computeGeneratedJavaTypeFullyQualifiedName(GenBase genBase) {
		genClassSwitch.doSwitch(genBase);
		return genClassSwitch.getFullyQualifiedName();
	}
	
	private IType resolveGenBaseType(IJavaProject javaProject, String fullyQualifiedName) {
		try {
			return javaProject.findType(fullyQualifiedName);
		} catch (JavaModelException e) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("GenBaseJDTUtils.ResolveGenBaseTypeError"), e));
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isAnAccessibleJavaProject(IProject project) {
		boolean status = false;
		try {
			status = project != null 
								&& (project.getType()==IProject.PROJECT) 
								&& project.isAccessible()
								&& project.hasNature(JavaCore.NATURE_ID);
		} catch (CoreException e) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("GenBaseJDTUtils.IsAnAccessibleJavaProjectError"), e));
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * Computes project from a given GenBase.
	 * @param genBase a given arbitrary GenBase object.
	 * @return corresponding project, null otherwise.
	 */
	public IProject resolveModelProject(GenBase genBase) {
		if (genBase.eResource() != null) {
			ResourceSet resourceSet = genBase.eResource().getResourceSet();
			if (resourceSet != null && !resourceSet.getResources().isEmpty()) {
				Object firstResource = resourceSet.getResources().get(0);
				if (firstResource instanceof Resource) {
					if (!((Resource)firstResource).getContents().isEmpty()) {
						Object o = ((Resource)firstResource).getContents().get(0);
						if (o instanceof GenModel) {
							return resolveModelProject((GenModel)o);
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Computes project from a given GenModel.
	 * @param genModel a given workspace root relative project name.
	 * @return corresponding project, null otherwise.
	 */
	public IProject resolveModelProject(GenModel genModel) {
		IResource p = ResourcesPlugin.getWorkspace().getRoot().findMember(genModel.getModelProjectDirectory());
		return p instanceof IProject ? (IProject) p : null;
		
	}
	
	/**
	 * Computes project from its workspace root relative project name.
	 * @param genModel a given workspace root relative project name.
	 * @return corresponding project, null otherwise.
	 */
	public IProject resolveModelProject(String platformProjectName) {
		IResource p = ResourcesPlugin.getWorkspace().getRoot().findMember(platformProjectName, true);
		return p instanceof IProject ? (IProject) p : null;
	}
	
	/**
	 * Computes project name relative to workspace root.
	 * @param genModel a given GenModel.
	 * @return project name relative to workspace root, null otherwise.
	 */
	public String computeModelProjectDirectory(GenModel genModel) {
		return genModel.getModelProjectDirectory();
	}
}
