/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * GenModelMetaModelParticipantsItemProvider.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.emf.search.genmodel.ui.providers;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.search.ui.providers.AbstractMetaModelParticipantsItemProvider;


public final class GenModelMetaModelParticipantsItemProvider extends AbstractMetaModelParticipantsItemProvider {
	public Object[] getElements(Object inputElement) {
		return GenModelPackage.eINSTANCE.getEClassifiers().toArray();
	}
}
