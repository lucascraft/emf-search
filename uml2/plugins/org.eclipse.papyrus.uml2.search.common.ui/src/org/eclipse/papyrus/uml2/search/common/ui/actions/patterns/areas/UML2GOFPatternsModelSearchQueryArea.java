/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * UML2GOFPatternsModelSearchQueryArea.java
 * 
 * Contributors:
 *		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.papyrus.uml2.search.common.ui.actions.patterns.areas;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetMetaElementSelectionEvent;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetSelectionEnum;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchQueryArea;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.papyrus.uml2.search.common.ui.helpers.UML2GOFPatternsModelSearchQueryBuilderHelper;
import org.eclipse.papyrus.uml2.search.common.ui.providers.UML2GOFPatternsSearchQueryContentProvider;
import org.eclipse.papyrus.uml2.search.ui.providers.UML2ModelSearchQueryLabelProvider;

/**
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class UML2GOFPatternsModelSearchQueryArea extends
	AbstractModelSearchQueryArea implements ISelectionProvider {

	private Composite container;
	private FilteredTree gofPatternsFileteredTree;
	private Label label;
	
	public UML2GOFPatternsModelSearchQueryArea(Composite parent, AbstractModelSearchPage page, int style) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		label = new Label(container, SWT.READ_ONLY);
		label.setText("GOF Pattern Queries:");
		
		gofPatternsFileteredTree = new FilteredTree(container, SWT.BORDER, new PatternFilter());
		gofPatternsFileteredTree.getViewer().setContentProvider(new UML2GOFPatternsSearchQueryContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;
			}
		});
		gofPatternsFileteredTree.getViewer().setLabelProvider(new UML2ModelSearchQueryLabelProvider());
		gofPatternsFileteredTree.getViewer().setInput(UML2GOFPatternsModelSearchQueryBuilderHelper.instance().getGOFPatternModelSearchQueries().toArray());
	}
	
	public String getQueryExpression() {
		return "*";
	}

	public void handleTargetMetaModel(String targetMetaModelID) {
		notifyListeners(new TargetMetaElementSelectionEvent(this, new StructuredSelection(new Object[0]), targetMetaModelID, TargetSelectionEnum.BLOCKING));
	}

	public EObject getTargetMetaElement() {
		return null; // Do Nothing : in this case, textual query edition is not target metamodel dependant
	}

	public Collection<EObject> getTargetMetaElements() {
		return null;
	}

	public Control getControl() {
		return container;
	}

	public void loadDialogSettings() {
		//Do nothing
	}

	public void prepare() {
		//Do nothing
	}

	public void storeDialogSettings() {
		//Do nothing
	}

	public boolean validateStatus() {
		return true;
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
	}
	
	public ISelection getSelection() {
		ISelection selection = gofPatternsFileteredTree.getViewer().getSelection();
		if (selection instanceof IStructuredSelection) {
			return selection;
		}
		return new StructuredSelection();
	}
	
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
	}
	public void setSelection(ISelection selection) { }

}
