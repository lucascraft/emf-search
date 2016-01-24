/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreTextualModelSearchQueryArea.java
 * 
 * Contributors:
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - UI, Target Meta Elements listener support
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.ecore.ui.areas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetMetaElementSelectionEvent;
import org.eclipse.papyrus.infra.emf.search.core.engine.TargetSelectionEnum;
import org.eclipse.papyrus.infra.emf.search.ecore.engine.EcoreModelSearchQuery;
import org.eclipse.papyrus.infra.emf.search.ecore.regex.ModelSearchQueryTextualExpressionMatchingHelper;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.Activator;
import org.eclipse.papyrus.infra.emf.search.ecore.ui.l10n.Messages;
import org.eclipse.papyrus.infra.emf.search.ui.areas.AbstractModelSearchQueryArea;
import org.eclipse.papyrus.infra.emf.search.ui.pages.AbstractModelSearchPage;
import org.eclipse.papyrus.infra.emf.search.ui.services.QueryTabDescriptor;
import org.eclipse.papyrus.infra.emf.search.ui.utils.ModelSearchImages;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;

/**
 * Defines UI for Ecore models Text based queries.
 * 
 * Different Modes are supported :
 * <p>
 * <li>Textual Search
 * <li>Regular Expression Search
 * <li>Case Sensitive Search
 * </p>
 * <br>
 * <p>
 * <b>Textual Search</b>
 * <ol>Textual Search allowing to use *, ? characters.</ol>
 * </p>
 * 
 * <p><b>Regular Expression Search</b></p>
 * <ol>Regular Expression Search fully support Java Regex Patterns. @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html">http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html</a></ol>
 * 
 * <p>
 * <b>Case Sensitive Search</b>
 * <ol>Textual Search matching strictly according to case.</ol>
 * </p>
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class EcoreTextualModelSearchQueryArea extends AbstractModelSearchQueryArea implements ISelectionProvider{
	// Messages
	private final static String EXPLANATORY_SEARCH_JOKERS_TEXT = Messages.getString("EcoreTextModelSearchQueryArea.searchExplanatoryMessage"); //$NON-NLS-1$
	private final static String REGEX_EXPRESSION_ERROR_MESSAGE = Messages.getString("EcoreTextModelSearchQueryArea.invaliRegExpErrorMessage"); //$NON-NLS-1$
	private final static String REGEX_EXPLANATION_LINK_MESSAGE = Messages.getString("EcoreTextModelSearchQueryArea.queryHelpHyperlinkMessage"); //$NON-NLS-1$
	
	// Dialog settings
	private static final String ECORE_TEXT_MODEL_SEARCH_AREA_DIALOG_SECTION_ID  = "EcoreTextModelSearchQueryArea"; //$NON-NLS-1$
	private static final String QUERY_LAST_SEARCHES_LIST_DIALOG_SETTINGS_ID = "queryLastSearchesList"; //$NON-NLS-1$
	private static final String REGEX_CHECKBOX_DIALOG_SETTINGS_ID = "regExCheckbox"; //$NON-NLS-1$
	private static final String CASE_SENSISTIVE_CHECKBOX_DIALOG_SETTINGS_ID = "caseSensitiveCheckbox"; //$NON-NLS-1$
	
	private static String SETTINGS_EXT = ".settings"; //$NON-NLS-1$
	
	// Search queries queue size
	private static int QUERY_LAST_SEARCHES_LIST_CAPACITY = 12;
	
	// Controls
	private AbstractModelSearchPage searchPage;
	private Group qGrp;
	private Combo searchQueryTextCombo;
	private Label searchQueryLabel, searchQueryExplanatoryLabel;
	private Link javaRegexPatternUrlLink;
	private Button searchCaseSensitiveCheckBox, searchRegularExpressionCheckBox;
	private List<String> lastQueriesList; 
	private boolean hasAValidRegex = true;
	private Composite container;
	
	public EcoreTextualModelSearchQueryArea(Composite parent, AbstractModelSearchPage page, int style) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		searchPage = page;
		createControl(container);
		lastQueriesList = new ArrayList<String>(QUERY_LAST_SEARCHES_LIST_CAPACITY);
	}
	
	//
	// Validate whether a regex is valid or not according to PatternException catching mechanism
	//
	private boolean validateRegex() {
		if (searchRegularExpressionCheckBox.getSelection()) {
			try {
				ModelSearchQueryTextualExpressionMatchingHelper.createPattern(searchQueryTextCombo.getText(), searchCaseSensitiveCheckBox.getSelection(), true);
			} catch (Exception e) {
				searchQueryExplanatoryLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				searchQueryExplanatoryLabel.setText(REGEX_EXPRESSION_ERROR_MESSAGE);
				hasAValidRegex = false;
				validateStatus();
				return hasAValidRegex;
			}
			searchQueryExplanatoryLabel.setText(""); //$NON-NLS-1$
		} else {
			searchQueryExplanatoryLabel.setText(EXPLANATORY_SEARCH_JOKERS_TEXT);
		}
		searchQueryExplanatoryLabel.setForeground(getControl().getForeground());
		hasAValidRegex = true;
		return validateStatus();
	}
	
	//
	// Create graphical content for current area
	//
	private void createControl(Composite parent) {
		qGrp = new Group(parent, SWT.NONE);
		qGrp.setLayout(new GridLayout(3, false));
		qGrp.setLayoutData(new GridData(GridData.FILL_BOTH));
		qGrp.setText(Messages.getString("EcoreTextModelSearchQueryArea.textQueryGroupTitle")); //$NON-NLS-1$
		
		searchQueryLabel = new Label(qGrp, SWT.NONE);
		searchQueryLabel.setText(Messages.getString("EcoreTextModelSearchQueryArea.textQueryMessage"));
		searchQueryLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.NONE, false, false, 3, 1));
		
		searchQueryTextCombo = new Combo(qGrp, SWT.DROP_DOWN);
		searchQueryTextCombo.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));
		searchQueryTextCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				validateRegex();
			}
		});		
		
		searchCaseSensitiveCheckBox = new Button(qGrp, SWT.CHECK);
		searchCaseSensitiveCheckBox.setText(Messages.getString("EcoreTextModelSearchQueryArea.caseSensitiveMessage")); //$NON-NLS-1$
		searchCaseSensitiveCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false, 1, 1));
		searchCaseSensitiveCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				searchQueryTextCombo.forceFocus();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		searchQueryExplanatoryLabel = new Label(qGrp, SWT.WRAP);
		searchQueryExplanatoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));
		searchQueryExplanatoryLabel.setText(EXPLANATORY_SEARCH_JOKERS_TEXT);
		
		searchRegularExpressionCheckBox = new Button(qGrp, SWT.CHECK);
		searchRegularExpressionCheckBox.setText(Messages.getString("EcoreTextModelSearchQueryArea.regExpMessage")); //$NON-NLS-1$
		searchRegularExpressionCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false, 1, 1));
		searchRegularExpressionCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				searchQueryExplanatoryLabel.setText(!searchRegularExpressionCheckBox.getSelection()?EXPLANATORY_SEARCH_JOKERS_TEXT:""); //$NON-NLS-1$
				searchCaseSensitiveCheckBox.setEnabled(!searchRegularExpressionCheckBox.getSelection());
				validateRegex();
				searchQueryTextCombo.forceFocus();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		javaRegexPatternUrlLink = new Link(qGrp, SWT.NONE);
		javaRegexPatternUrlLink.setText(REGEX_EXPLANATION_LINK_MESSAGE);
		javaRegexPatternUrlLink.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent evt) {
				try {
					PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
				} catch (Exception e) {
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("EcoreTextModelSearchQueryArea.webBrowserErrorMessage"), e)); //$NON-NLS-1$
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(qGrp, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(searchQueryLabel, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(searchQueryTextCombo, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(searchCaseSensitiveCheckBox, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(searchQueryExplanatoryLabel, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(searchRegularExpressionCheckBox, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(javaRegexPatternUrlLink, "org.eclipse.papyrus.infra.emf.search.ecore.ui.EcoreTextModelSearchQueryArea"); //$NON-NLS-1$
		
		validateRegex();
	}
	
	public Control getControl() {
		return container;
	}
	
	public String getQueryExpression() {
		return searchQueryTextCombo.getText();
	}
	
	//
	// General dialog settings loading
	//
	public void loadDialogSettings() {
		//Dialog settings for Query Tab
    	IDialogSettings settings = Activator.getDefault().getDialogSettings();
  
		//
		// BEGIN: https://bugs.eclipse.org/bugs/show_bug.cgi?id=231988
		//
		String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString(); //$NON-NLS-1$  //$NON-NLS-2$
		File settingsFile = new File(settingsPath);
		if (settingsFile.exists()) {
			try {
				settings.load(settingsPath);
	        } catch (IOException e) {
				Activator.getDefault().getLog().log(
						new Status(
								IStatus.INFO,
								Activator.PLUGIN_ID,
								0,
								Messages
										.getString("EcoreTextModelSearchQueryArea.dialogSettingsLoadErrorMessage"), e)); //$NON-NLS-1$
	        }
		}
		//
		// END
		//
		
        IDialogSettings ecoreTextQuerySectionDialogSettings;
        
        if ((ecoreTextQuerySectionDialogSettings = settings.getSection(ECORE_TEXT_MODEL_SEARCH_AREA_DIALOG_SECTION_ID))==null) {
        	ecoreTextQuerySectionDialogSettings = settings.addNewSection(ECORE_TEXT_MODEL_SEARCH_AREA_DIALOG_SECTION_ID);
        }

    	String[] lastQueries = ecoreTextQuerySectionDialogSettings.getArray(QUERY_LAST_SEARCHES_LIST_DIALOG_SETTINGS_ID);
    	lastQueries = lastQueries==null?new String[0]:lastQueries;
    	searchQueryTextCombo.setItems(lastQueries);
    	lastQueriesList.addAll(Arrays.asList(lastQueries));
    	if (lastQueries!=null&&lastQueries.length>0) {
    		searchQueryTextCombo.select(0);
    	} else {
    		searchQueryTextCombo.setText("*"); //$NON-NLS-1$
    	}
    	searchQueryTextCombo.setFocus();
    	
    	boolean regexCheckboxSelection = ecoreTextQuerySectionDialogSettings.getBoolean(REGEX_CHECKBOX_DIALOG_SETTINGS_ID);
    	searchRegularExpressionCheckBox.setSelection(regexCheckboxSelection);
    	
    	boolean caseSensitiveCheckboxSelection = ecoreTextQuerySectionDialogSettings.getBoolean(CASE_SENSISTIVE_CHECKBOX_DIALOG_SETTINGS_ID);
    	searchCaseSensitiveCheckBox.setSelection(caseSensitiveCheckboxSelection);
    	searchCaseSensitiveCheckBox.setEnabled(!regexCheckboxSelection);
    	searchQueryExplanatoryLabel.setText(!regexCheckboxSelection?EXPLANATORY_SEARCH_JOKERS_TEXT:""); //$NON-NLS-1$
		searchQueryTextCombo.setFocus();
	}

	//
	// General dialog settings storage
	//
	public void storeDialogSettings() {
		//Dialog settings for Query Tab
    	IDialogSettings settings = Activator.getDefault().getDialogSettings();
    	
        IDialogSettings ecoreTextQuerySectionDialogSettings;
        
        if ((ecoreTextQuerySectionDialogSettings = settings.getSection(ECORE_TEXT_MODEL_SEARCH_AREA_DIALOG_SECTION_ID))==null) {
        	ecoreTextQuerySectionDialogSettings = settings.addNewSection(ECORE_TEXT_MODEL_SEARCH_AREA_DIALOG_SECTION_ID);
        }
        
    	ecoreTextQuerySectionDialogSettings.put(QUERY_LAST_SEARCHES_LIST_DIALOG_SETTINGS_ID, lastQueriesList.toArray(new String[0]));
    	ecoreTextQuerySectionDialogSettings.put(REGEX_CHECKBOX_DIALOG_SETTINGS_ID, searchRegularExpressionCheckBox.getSelection());
    	ecoreTextQuerySectionDialogSettings.put(CASE_SENSISTIVE_CHECKBOX_DIALOG_SETTINGS_ID, searchCaseSensitiveCheckBox.getSelection());
 
		String settingsPath = Activator.getDefault().getStateLocation().append(getDataMap().get("SETTINGS_PREFIX") + "_" + getClass().getSimpleName() + SETTINGS_EXT).toOSString(); //$NON-NLS-1$ //$NON-NLS-2$
		File settingsFile = new File(settingsPath);
		if (!settingsFile.exists()||settingsFile.canWrite()) {
			try {
				settings.save(settingsPath);
			} catch (IOException e) {
	           	Activator.getDefault().getLog().log(
	           			new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
      					Messages.getString("EcoreTextModelSearchQueryArea.dialogSettingsSaveErrorMessage"), e)); //$NON-NLS-1$
			}
	    }
	}
	
	//
	// Query preparation, called before triggering the search
	//
	public void prepare() {
		String queryText = searchQueryTextCombo.getText();
		if (queryText != null && !queryText.equals("")) { //$NON-NLS-1$
			if (lastQueriesList.size()>=QUERY_LAST_SEARCHES_LIST_CAPACITY) {
				lastQueriesList.remove(lastQueriesList.size()-1);
			}
			lastQueriesList.add(0, queryText);
		}
		getDataMap().put(EcoreModelSearchQuery.CASE_SENSITIVE_SEARCH, new Boolean(!searchCaseSensitiveCheckBox.isEnabled()?false:searchCaseSensitiveCheckBox.getSelection()));
		getDataMap().put(EcoreModelSearchQuery.REGEX_SEARCH, new Boolean(searchRegularExpressionCheckBox.getSelection()));
	}
	
	//
	// Validate area status according to inputs, specially regex
	//
	public boolean validateStatus() {
		boolean status = hasAValidRegex && !"".equals(getQueryExpression()); //$NON-NLS-1$
		searchPage.getContainer().setPerformActionEnabled(status);
		
		TabItem qTabItem = searchPage.getCurrentlySelectedQueryTabItem();
		if (qTabItem!=null) {
			QueryTabDescriptor qTabDescriptor = searchPage.getCurrentlySelectedQueryDescriptor(); 
			qTabItem.setImage((!status)?ModelSearchImages.getErrorImage():qTabDescriptor.getImage());
		}
		return hasAValidRegex;
	}

	public void handleTargetMetaModel(String targetMetaModelID) {
		notifyListeners(new TargetMetaElementSelectionEvent(this, new StructuredSelection(new Object[0]), targetMetaModelID, TargetSelectionEnum.NONE));
	}

	public EObject getTargetMetaElement() {
		return null; // Do Nothing : in this case, textual query edition is not target metamodel dependant
	}

	public Collection<EObject> getTargetMetaElements() {
		return new ArrayList<EObject>(); // Do Nothing : in this case, textual query edition is not target metamodel dependant
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) { }
	
	public ISelection getSelection() {
		return new StructuredSelection();
	}
	
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
	}
	
	public void setSelection(ISelection selection) { }
}
