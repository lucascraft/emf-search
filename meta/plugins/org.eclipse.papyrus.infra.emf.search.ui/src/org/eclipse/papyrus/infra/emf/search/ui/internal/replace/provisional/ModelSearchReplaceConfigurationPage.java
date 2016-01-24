/*******************************************************************************
 * Copyright (c) 2008 Lucas Bigeardel and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchReplaceConfigurationPage.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel - initial API and implementation
 * 
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ui.internal.replace.provisional;

import java.util.ArrayList;

import org.eclipse.papyrus.infra.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.core.internal.replace.provisional.AbstractTextualModelSearchReplace;
import org.eclipse.papyrus.infra.emf.search.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ui.l10n.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Replace configuration wizard page aiming to configure whether a transformation will apply
 * to initial participant elements or not. 
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 * @since 0.7.0
 *
 */
public class ModelSearchReplaceConfigurationPage extends UserInputWizardPage {

	private static final String SETTINGS_GROUP= "ReplaceDialog2"; //$NON-NLS-1$
	private static final String SETTINGS_REPLACE_WITH= "replace_with"; //$NON-NLS-1$
	
	private final AbstractTextualModelSearchReplace fReplaceRefactoring;

	private Combo textField;
	private Label fStatusLabel;

	public ModelSearchReplaceConfigurationPage(AbstractTextualModelSearchReplace refactoring) {
		super("ModelSearchReplaceConfigurationPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ModelSearchReplaceConfigurationPage.Title")); //$NON-NLS-1$
		setDescription(Messages.getString("ModelSearchReplaceConfigurationPage.Description")); //$NON-NLS-1$
		fReplaceRefactoring= refactoring;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
    public void createControl(Composite parent) {
    	
    	Composite result= new Composite(parent, SWT.NONE);
    	GridLayout layout= new GridLayout(2, false);
		result.setLayout(layout);
		
		Label description= new Label(result, SWT.NONE);
		
		IModelSearchQuery query= fReplaceRefactoring.getModelSearchQuery();

		int numberOfMatches= query.getModelSearchResult().getTotalMatches();
		int numberOfFiles= query.getModelSearchResult().getRootResultHierarchies().keySet().size();
		
		String[] arguments= { String.valueOf(numberOfMatches), String.valueOf(numberOfFiles) };
		if (numberOfMatches > 1 && numberOfFiles > 1) {
			description.setText(Messages.getString("ModelSearchReplaceConfigurationPage.0") + arguments[0] + Messages.getString("ModelSearchReplaceConfigurationPage.1") + arguments[1] + Messages.getString("ModelSearchReplaceConfigurationPage.2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} else if (numberOfMatches == 1) {
			description.setText(Messages.getString("ModelSearchReplaceConfigurationPage.3")); //$NON-NLS-1$
		} else {
			description.setText(arguments[0] +Messages.getString("ModelSearchReplaceConfigurationPage.4")); //$NON-NLS-1$
		}
		description.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));
		
		
		Label label1= new Label(result, SWT.NONE);
		label1.setText(Messages.getString("ModelSearchReplaceConfigurationPage.5")); //$NON-NLS-1$
		
		Text clabel= new Text(result, SWT.BORDER | SWT.READ_ONLY);
		clabel.setText(query.getQueryExpression());
		GridData gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint= convertWidthInCharsToPixels(50);
		clabel.setLayoutData(gd);
		
		Label label2= new Label(result, SWT.NONE);
		label2.setText(Messages.getString("ModelSearchReplaceConfigurationPage.6")); //$NON-NLS-1$
		
		textField= new Combo(result, SWT.DROP_DOWN);
		gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint= convertWidthInCharsToPixels(50);
		textField.setLayoutData(gd);
		textField.setFocus();
		
		IDialogSettings settings= Activator.getDefault().getDialogSettings().getSection(SETTINGS_GROUP);
		if (settings != null) {
			String[] previousReplaceWith= settings.getArray(SETTINGS_REPLACE_WITH);
			if (previousReplaceWith != null) {
				textField.setItems(previousReplaceWith);
				textField.select(0);
			}
		}
		
		fStatusLabel= new Label(result, SWT.NULL);
		gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment= SWT.BOTTOM;
		gd.horizontalSpan= 2;
		fStatusLabel.setLayoutData(gd);

		setControl(result);
		
		Dialog.applyDialogFont(result);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#performFinish()
     */
    protected boolean performFinish() {
		initializeRefactoring();
		storeSettings();
		return super.performFinish();
	}

    
    /* (non-Javadoc)
     * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#getNextPage()
     */
	public IWizardPage getNextPage() {
		initializeRefactoring();
		storeSettings();
		return super.getNextPage();
	}
	
	private void storeSettings() {
		String[] items= textField.getItems();
		ArrayList<String> history= new ArrayList<String>();
		history.add(textField.getText());
		int historySize= Math.min(items.length, 6);
		for (int i= 0; i < historySize; i++) {
			String curr= items[i];
			if (!history.contains(curr)) {
				history.add(curr);
			}
		}
		IDialogSettings settings= Activator.getDefault().getDialogSettings().addNewSection(SETTINGS_GROUP);
		settings.put(SETTINGS_REPLACE_WITH, (String[]) history.toArray(new String[history.size()]));

    }

	private void initializeRefactoring() {
		fReplaceRefactoring.setReplaceString(textField.getText());
    }
}
