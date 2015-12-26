/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ContainerCheckboxFilteredTree.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.ui.utils;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public class ContainerCheckboxFilteredTree extends FilteredTree {
	public ContainerCheckboxFilteredTree(Composite parent, int style,
			PatternFilter filter) {
		super(parent, style, filter);
	}
	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		return new ContainerCheckedTreeViewer(parent, style);
	}
	public ContainerCheckedTreeViewer getCheckboxTreeViewer() {
		return (ContainerCheckedTreeViewer) getViewer();
	}
}
