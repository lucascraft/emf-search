/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenerateCodeAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.genmodel.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.genmodel.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ui.actions.AbstractModelSearchPageAction;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;

public abstract class AbstractGenerateCodeAction extends AbstractModelSearchPageAction {
	
	public AbstractGenerateCodeAction(String label) {
		super(label, ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/full/obj16/jcu_obj.gif"));
	}
	
	@Override
	public boolean isEnabled() {
		Object o = getModelSearchResultPageSelection();
		if (o instanceof IModelResultEntry) {
			Object target = ((IModelResultEntry)o).getSource();
			if (target instanceof GenBase) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		Object o = getModelSearchResultPageSelection();
		if (o instanceof IModelResultEntry) {
			Object source = ((IModelResultEntry)o).getSource();
			if (source instanceof GenBase) {
				handleGenBaseElement((GenBase)source, getTargetProjectTypes());
			}
		}
	}
	
	protected abstract String[] getTargetProjectTypes();
	
	private class CodeGenJob extends Job {
		private GenBase genElem;
		private String[] pTypes;
		public CodeGenJob(GenBase genBase, String[] projectTypes) {
			super("EMF Search GenModel Generator");
			genElem = genBase;
			pTypes = projectTypes;
		}
		@Override
		public IStatus run(IProgressMonitor monitor) {
			genElem.getGenModel().setCanGenerate(true);
			
			monitor.beginTask("Generate Projects ...", pTypes.length);
			
			Generator generator = new Generator();
			
			generator.setInput(genElem.getGenModel());
			
			for (String projectType : pTypes) {
				monitor.setTaskName(getTargetProjectGenTaskLabel(projectType));
				generator.generate(genElem, projectType, BasicMonitor.toMonitor(monitor));			
				monitor.worked(1);
			}
			
			return Status.OK_STATUS;
		}
	}
	
	private String getTargetProjectGenTaskLabel(String type) {
		if (GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE.equals(type)) {
			return "Generate Model Code";
		} else if (GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE.equals(type)) {
			return "Generate Edit Code";
		} else if (GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE.equals(type)) {
			return "Generate Editor Code";
		} else if (GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE.equals(type)) {
			return "Generate Tests Code";
		}
		return "";
	}
	
	private void handleGenBaseElement(final GenBase genBase, final String[] projectTypes) {
		new CodeGenJob(genBase, projectTypes).schedule();
	}
	
}
