/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchFilteredEClassifierSelectionDialog.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.common.ui.dialogs;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.core.results.IModelResultEntry;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.papyrus.infra.emf.search.ecore.evaluators.EcoreTextualModelSearchQueryEvaluator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.factories.EcoreCommonUtils;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.handlers.EcoreEditorSelectionHandler;
import org.eclipse.papyrus.infra.emf.search.ui.handlers.IModelElementEditorSelectionHandler;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Shell;

/**
 * Class responsible for EClassifier selection.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas Bigeardel</a>
 *
 */
public final class ModelSearchFilteredEClassifierSelectionDialog extends
		AbstractModelSearchFilteredMetaElementsSelectionDialog {
	public ModelSearchFilteredEClassifierSelectionDialog(Shell shell,
			boolean multi) {
		super(shell, multi);
	}
	@Override
	protected boolean isAMetaElementToConsider(Object item) {
		if (item instanceof IModelResultEntry) {
			return ((IModelResultEntry)item).getSource() instanceof EClass;
		}
		return false;
	}
	@Override
	protected List<EClassifier> getMetaElementParticipants() {
        return Arrays.asList(new EClassifier[] { EcorePackage.Literals.ECLASS });
	}
	@Override
	protected IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getModelQueryEvaluator() {
		return new EcoreTextualModelSearchQueryEvaluator<IModelSearchQuery, Resource>();
	}
	@Override
	protected String getModelSearchEngineID() {
		return EcoreTextualEngine.ID;
	}
	@Override
	public String getModelResultFullyQualifiedName(IModelResultEntry entry) {
		String txt = ""; //$NON-NLS-1$
		if (entry != null) {
			for (IModelResultEntry e : entry.getHierarchyFromRootToLeaf()) {
				if (e != null && e.getSource() instanceof ENamedElement) {
					txt = ((ENamedElement)e.getSource()).getName() + "." + txt; //$NON-NLS-1$
				}
			}
		}
		return txt.endsWith(".")?txt.substring(0, txt.lastIndexOf(".")):txt; //$NON-NLS-1$ //$NON-NLS-2$
	}
	@Override
	public String getElementName(Object item) {
		if (item instanceof IModelResultEntry) {
			if (((IModelResultEntry)item).getSource() instanceof ENamedElement) {
				return ((ENamedElement)((IModelResultEntry)item).getSource()).getName();
			}
		}
		return "";
	}
	@Override
	protected IModelElementEditorSelectionHandler getModelEditorSelectionHandler() {
		return new EcoreEditorSelectionHandler();
	}
	@Override
	protected List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		return EcoreCommonUtils.getMetaElementComposeableAdapterFactories();
	}
	@Override
	protected IDialogSettings getDialogSettings() {
		return Activator.getDefault().getDialogSettings();
	}
}
