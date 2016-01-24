/**
 * 
 */
package org.eclipse.papyrus.uml2.search.ui.providers;

import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.utils.ModelSearchImagesUtil;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.papyrus.uml2.search.engine.UML2ModelSearchQuery;

/**
 * @author lucas
 *
 */
public class UML2ModelSearchQueryLabelProvider extends ColumnLabelProvider implements ILabelProvider {

	/**
	 * 
	 */
	public UML2ModelSearchQueryLabelProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof IModelSearchQuery) {
			return ModelSearchImagesUtil.getImage(
					Platform.getBundle(((IModelSearchQuery) element).getBundleSymbolicName()),
					((IModelSearchQuery) element).getResultImagePath()
				); 
		}
		return ModelSearchImagesUtil.getImage(
				Activator.getDefault().getBundle(),
				"icons/full/esearch.gif"
			);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof UML2ModelSearchQuery) {
			return ((UML2ModelSearchQuery) element).getLabel();
		}
		return super.getText(element);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}
}
