/*******************************************************************************
 * Copyright (c) 2007,2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchQueryArea.java
 * 
 * Contributors: 
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - add target model image support
 *		Lucas Bigeardel - add participants filtering support
 *		Lucas Bigeardel - add target meta elements listening support
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.areas;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.emf.search.core.engine.ITargetMetaElementSelectionListener;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetMetaElementSelectionEvent;

/**
 * <p>
 * Provide a query to be to be evaluated during the search 
 * </p>
 * <p>
 * Allows user to edit a query in large sense.
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public abstract class AbstractModelSearchQueryArea extends AbstractModelSearchCompositeArea implements IModelSearchQueryArea {
	private List<ITargetMetaElementSelectionListener> targetMetaElementListenerList;
	public AbstractModelSearchQueryArea() {
		targetMetaElementListenerList = new ArrayList<ITargetMetaElementSelectionListener>();
	}
	public void addTargetMetaElementSelectionListener(ITargetMetaElementSelectionListener listener) {
		if (!targetMetaElementListenerList.contains(listener)) {
			targetMetaElementListenerList.add(listener);
		}
	}
	public List<ITargetMetaElementSelectionListener> getTargetMetaElementSelectionListeners() {
		return targetMetaElementListenerList;
	}
	public void removeTargetMetaElementSelectionListener(ITargetMetaElementSelectionListener listener) {
		targetMetaElementListenerList.remove(listener);		
	}
	public void notifyListener(ITargetMetaElementSelectionListener listener, TargetMetaElementSelectionEvent event) {
		listener.handle(event);
	}
	public void notifyListeners(TargetMetaElementSelectionEvent event) {
		for (ITargetMetaElementSelectionListener listener : getTargetMetaElementSelectionListeners()) {
			notifyListener(listener, event);
		}
	}
}
