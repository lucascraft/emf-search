/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchFilteredUMLClassSelectionDialog.java
 * 
 * Contributors: Lucas Bigeardel (Anyware Technologies) - initial API and
 * implementation
 ******************************************************************************/

package org.eclipse.uml2.search.common.ui.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.eval.IModelSearchQueryEvaluator;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.ecore.common.ui.dialogs.AbstractModelSearchFilteredMetaElementsSelectionDialog;
import org.eclipse.emf.search.ui.handlers.IModelElementEditorSelectionHandler;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.search.common.ui.Activator;
import org.eclipse.uml2.search.engine.UML2TextualEngine;
import org.eclipse.uml2.search.evaluators.UML2TextualModelSearchQueryEvaluator;
import org.eclipse.uml2.search.ui.handlers.UML2EditorSelectionHandler;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * Class responsible for UML Classifier selection.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">Lucas Bigeardel</a>
 *
 */
public final class ModelSearchFilteredUMLClassSelectionDialog extends
		AbstractModelSearchFilteredMetaElementsSelectionDialog {
	public ModelSearchFilteredUMLClassSelectionDialog(Shell shell,
			boolean multi) {
		super(shell, multi);
	}
	
	@Override
	protected boolean isAMetaElementToConsider(Object item) {
		if (item instanceof IModelResultEntry) {
			return ((IModelResultEntry)item).getSource() instanceof Classifier;
		}
		return false;
	}
	
	@Override
	protected List<EClassifier> getMetaElementParticipants() {
		return Arrays.asList(new EClassifier[] { UMLPackage.Literals.CLASSIFIER });
	}
	
	@Override
	protected IModelSearchQueryEvaluator<IModelSearchQuery, Resource> getModelQueryEvaluator() {
		return new UML2TextualModelSearchQueryEvaluator<IModelSearchQuery, Resource>();
	}
	
	@Override
	protected String getModelSearchEngineID() {
		return UML2TextualEngine.ID; //$NON-NLS-1$
	}

	@Override
	public String getElementName(Object item) {
		if (item instanceof IModelResultEntry) {
			if (((IModelResultEntry)item).getSource() instanceof NamedElement) {
				String name = ((NamedElement)((IModelResultEntry)item).getSource()).getName();
				return name!=null?name:""; //$NON-NLS-1$
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Implement few Ecore basic composeable Adapter Factories.
	 * This will be overidden by user. This would allow to avoid void lists ... ^^
	 */
	public List<AdapterFactory> getMetaElementComposeableAdapterFactories() {
		List<AdapterFactory> composeableAdapterFactories = new ArrayList<AdapterFactory>();
		composeableAdapterFactories.add(new UMLItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ResourceItemProviderAdapterFactory());
		composeableAdapterFactories.add(new ReflectiveItemProviderAdapterFactory());
		return composeableAdapterFactories;
	}
	
	@Override
	public String getModelResultFullyQualifiedName(IModelResultEntry entry) {
		String txt = ""; //$NON-NLS-1$
		if (entry != null) {
			for (IModelResultEntry e : entry.getHierarchyFromRootToLeaf()) {
				if (e != null && e.getSource() instanceof NamedElement) {
					txt = ((NamedElement)e.getSource()).getName() + "." + txt; //$NON-NLS-1$
				}
			}
		}
		return txt.endsWith(".")?txt.substring(0, txt.lastIndexOf(".")):txt; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected IModelElementEditorSelectionHandler getModelEditorSelectionHandler() {
		return new UML2EditorSelectionHandler();
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		return Activator.getDefault().getDialogSettings();
	}
}
