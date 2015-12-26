/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * DecoratingModelSearchResultLabelProvider.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - clean code
 *		Lucas Bigeardel - Add textual query support for all ETypedElement
 *
 ******************************************************************************/

package org.eclipse.emf.search.ui.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.search.core.results.ICompositeModelResultEntry;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelResultOccurence;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.ui.l10n.Messages;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author lucas
 *
 */
public class DecoratingModelSearchResultLabelProvider extends
AdapterFactoryLabelProvider implements IColorProvider {
	
	private IModelSearchResult modelSearchResult;
	
	private static Color NORMAL_RESULT_COLOR = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private static Color UNMATCHED_RESULT_COLOR =  Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
	
	public DecoratingModelSearchResultLabelProvider(AdapterFactory adapterFactory, IModelSearchResult searchResult) {
		super(adapterFactory);
		modelSearchResult = searchResult;
	}
	
	public Color getBackground(Object element) {
		return null;
	}

	public Color getForeground(Object element) {
		if (element instanceof IModelResultEntry) {
			if (!((IModelResultEntry)element).wasMatchedAtleastOnce()) {
				return UNMATCHED_RESULT_COLOR;
			}
		}
		return NORMAL_RESULT_COLOR; 
	}
	@Override
	public String getText(Object object) {
		if (object instanceof IModelResultOccurence) {
			IModelResultOccurence mOccurence = (IModelResultOccurence) object;
			return mOccurence.getTypedElement().getName() + " : '" + mOccurence.getValuation() + "'";
		} else if (object instanceof ICompositeModelResultEntry) {
			return ((ICompositeModelResultEntry) object).getLabel();
		} else if (object instanceof IModelResultEntry) {
			IModelResultEntry entry = (IModelResultEntry) object;
			int occurences = modelSearchResult.getMatches(entry.getResults());
			String occurenceMsg = Messages.getString("ModelExtensibleSearchResultPage.Occurence1") + (occurences>1?Messages.getString("ModelExtensibleSearchResultPage.Occurence2"):""); //$NON-NLS-1$ //$NON-NLS-2$ 
			return super.getText((EObject) entry.getSource()) + (occurences<=1?"":" ["+occurences+ " " + occurenceMsg + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		} else if (object instanceof Resource) {
			Resource file = (Resource)object;
			int nbMatches = modelSearchResult.getMatchesNumberForFile(file);

			String messages = Messages.getString("ModelExtensibleSearchResultPage.matchText1") + nbMatches +  Messages.getString("ModelExtensibleSearchResultPage.matchTex2") + (nbMatches>1?Messages.getString("ModelExtensibleSearchResultPage.matchTex3"):Messages.getString("ModelExtensibleSearchResultPage.matchTex4"))+ Messages.getString("ModelExtensibleSearchResultPage.matchTex5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	        if (file.getURI().isPlatformResource()) {
	           return file.getURI().toPlatformString(true) + messages;
	        }  
	          
	        return file.getURI().toFileString() + messages;
		}
		return super.getText(object);
	}
	@Override
	public Image getImage(Object object) {
		if (object instanceof IModelResultOccurence) {
			return ((IModelResultOccurence) object).getImage();
		} else if (object instanceof ICompositeModelResultEntry) {
			return ((ICompositeModelResultEntry) object).getImage();
		} else if (object instanceof IModelResultEntry) {
			return super.getImage(((IModelResultEntry) object).getSource());
		} else if (object instanceof Resource) {
			return modelSearchResult.getImage();
		}
		return super.getImage(object);
	}
	@Override
	public String getColumnText(Object object, int columnIndex) {
		if (object instanceof IModelResultOccurence) {
			IModelResultOccurence mOccurence = (IModelResultOccurence) object;
			return mOccurence.getTypedElement().getName() + " : '" + mOccurence.getValuation() + "'";
		} else if (object instanceof ICompositeModelResultEntry) {
			return ((ICompositeModelResultEntry) object).getLabel();
		} else if (object instanceof IModelResultEntry) {
			IModelResultEntry entry = (IModelResultEntry) object;
			int occurences = modelSearchResult.getMatches(entry.getResults());
			String occurenceMsg = Messages.getString("ModelExtensibleSearchResultPage.Occurence1") + (occurences>1?Messages.getString("ModelExtensibleSearchResultPage.Occurence2"):""); //$NON-NLS-1$ //$NON-NLS-2$ 
			return super.getColumnText((EObject) entry.getSource(), columnIndex) + (entry.wasMatchedAtleastOnce()?"":" ["+occurences+ " " + occurenceMsg + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		} else if (object instanceof Resource) {
			Resource file = (Resource)object;
			int nbMatches = modelSearchResult.getMatchesNumberForFile(file);

			String messages = Messages.getString("ModelExtensibleSearchResultPage.matchText1") + nbMatches +  Messages.getString("ModelExtensibleSearchResultPage.matchTex2") + (nbMatches>1?Messages.getString("ModelExtensibleSearchResultPage.matchTex3"):Messages.getString("ModelExtensibleSearchResultPage.matchTex4"))+ Messages.getString("ModelExtensibleSearchResultPage.matchTex5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	        if (file.getURI().isPlatformResource()) {
	           return file.getURI().toPlatformString(true) + messages;
	        }  
	          
	        return file.getURI().toFileString() + messages;
		}
		return super.getColumnText(object, columnIndex);
	}
	
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		if (object instanceof IModelResultOccurence) {
			return ((IModelResultOccurence) object).getImage();
		} else if (object instanceof ICompositeModelResultEntry) {
			return ((ICompositeModelResultEntry) object).getImage();
		} else if (object instanceof IModelResultEntry) {
			return super.getColumnImage(((IModelResultEntry) object).getSource(), columnIndex);
		} else if (object instanceof Resource) {
			return modelSearchResult.getImage();
		}
		return super.getColumnImage(object, columnIndex);
	}

	public IModelSearchResult getModelSearchResult() {
		return modelSearchResult;
	}

	public void setModelSearchResult(IModelSearchResult modelSearchResult) {
		this.modelSearchResult = modelSearchResult;
	}
}
