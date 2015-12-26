/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GoToEcoreMetaModelAction.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.ui.actions;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.genmodel.ui.Activator;
import org.eclipse.emf.search.genmodel.ui.l10n.Messages;
import org.eclipse.emf.search.ui.actions.AbstractModelSearchPageAction;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;

public class GoToEcoreMetaModelAction extends AbstractModelSearchPageAction {
	public GoToEcoreMetaModelAction() {
		super(Messages.getString("GoToMetaEcoreModelAction.Label"), ModelSearchImagesUtil.getImageDescriptor(Activator.getDefault().getBundle(), "icons/full/obj16/jcu_obj.gif"));
	}
	
	@Override
	public boolean isEnabled() {
		Object o = getModelSearchResultPageSelection();
		if (o instanceof IModelResultEntry) {
			Object target = ((IModelResultEntry)o).getSource();
			if (target instanceof EObject) {
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
				handleGenBaseElement((GenBase)source);
			}
		}
	}
	
	private void handleGenBaseElement(GenBase genBase) {
		super.getResultPage().handleEObjectToOpenEditorFrom(genBase.getEcoreModelElement());
	}
	
	@Override
	public void runWithEvent(Event event) {
		run();
	}
	public void run(IAction action) {
		run();		
	}
}
