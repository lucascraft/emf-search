/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractCompositeModelResultEntry.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.search.core.results;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.search.Activator;
import org.eclipse.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author lucas
 *
 */
public abstract class AbstractCompositeModelResultEntry extends AbstractModelSearchResultEntry implements ICompositeModelResultEntry {
	private Collection<IModelResultEntry> entries;
	protected String label;
	protected Image image;
	
	public AbstractCompositeModelResultEntry(IModelResultEntry p, Object t, boolean m, String lbl, Image img) {
		super(p, t, null, m);
		entries = new ArrayList<IModelResultEntry>();
		label = lbl;
		image = img;
	}
	
	public AbstractCompositeModelResultEntry(IModelResultEntry p, Object t, boolean m, String lbl) {
		this(p, t, m, lbl, ModelSearchImagesUtil.getImage(Activator.getDefault().getBundle(), "icons/full/cresult.gif"));
	}
	
	public AbstractCompositeModelResultEntry(IModelResultEntry p, Object t, boolean m) {
		this(p, t, m, "", ModelSearchImagesUtil.getImage(Activator.getDefault().getBundle(), "icons/full/cresult.gif"));
	}
	
	public void addChildren(Collection<IModelResultEntry> entryCollection) {
		entries.addAll(entryCollection);
	}

	public void addChild(IModelResultEntry entry) {
		entries.add(entry);
	}

	public Collection<IModelResultEntry> getChildren() {
		return entries;
	}

	public void removeChildren(Collection<IModelResultEntry> entryCollection) {
		entries.removeAll(entryCollection);
	}

	public void removeChild(IModelResultEntry entry) {
		entries.remove(entry);
	}

	@Override
	public String getModelResultFullyQualifiedName() {
		return "Compound result";
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
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		AbstractCompositeModelResultEntry other = (AbstractCompositeModelResultEntry) obj;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!entries.equals(other.entries))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
