/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchResultOccurence.java
 * 
 * Contributors:
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.search.core.results;

import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.search.Activator;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;


public abstract class AbstractModelSearchResultOccurence extends AbstractModelSearchResultEntry implements
		IModelResultOccurence {
	private Object elem;
	protected String valuation;
	protected Image image;
	
	public AbstractModelSearchResultOccurence(IModelResultEntry parent, Object obj, Object element, boolean matched, String val, Image img) {
		super(parent, obj, null, matched);
		elem = element;
		valuation = val;
		image = img;
	}
	
	public AbstractModelSearchResultOccurence(IModelResultEntry parent, Object obj, Object elem, boolean matched, String val) {
		this(parent, obj, elem, matched, val, ModelSearchImagesUtil.getImage(Activator.getDefault().getBundle(), "icons/full/coccurence.gif"));
	}
	
	public AbstractModelSearchResultOccurence(IModelResultEntry parent, Object obj, Object elem, boolean matched) {
		this(parent, obj, elem, matched, "", ModelSearchImagesUtil.getImage(Activator.getDefault().getBundle(), "icons/full/coccurence.gif"));
	}
	

	@Override
	public String getModelResultFullyQualifiedName() {
		return "";
	}

	public Object getFile() {
		return super.getTarget();
	}

	public boolean isExcluded() {
		return false;
	}

	public Image getImage() {
		return image;
	}
	
	@Override
	public Object getSource() {
		return getParent().getSource();
	}
	
	public String getValuation() {
		return valuation;
	}
	
	public ETypedElement getTypedElement() {
		return elem instanceof ETypedElement ?(ETypedElement) elem : null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elem == null) ? 0 : elem.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((valuation == null) ? 0 : valuation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModelSearchResultOccurence other = (AbstractModelSearchResultOccurence) obj;
		if (elem == null) {
			if (other.elem != null)
				return false;
		} else if (!elem.equals(other.elem))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (valuation == null) {
			if (other.valuation != null)
				return false;
		} else if (!valuation.equals(other.valuation))
			return false;
		return true;
	}
}
