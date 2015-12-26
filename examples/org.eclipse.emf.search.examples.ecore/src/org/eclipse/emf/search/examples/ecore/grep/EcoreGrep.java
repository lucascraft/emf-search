/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * EcoreGrep.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - add javadoc + EPL update
 ******************************************************************************/

package org.eclipse.emf.search.examples.ecore.grep;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.engine.IModelSearchQuery;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.scope.IModelSearchScope;
import org.eclipse.emf.search.ecore.engine.EcoreTextualEngine;
import org.eclipse.emf.search.ecore.helper.builder.EcoreTextualModelSearchQueryBuilderHelper;
import org.eclipse.emf.search.ecore.regex.ModelSearchQueryTextualExpressionEnum;
import org.eclipse.emf.search.ecore.results.EcoreModelSearchResultEntry;
import org.eclipse.emf.search.ecore.scope.ModelSearchScopeFactory;

/**
 * EcoreGrep is a standalone Ecore meta-tool inspired from already known UNIX grep and/or find command.
 * 
 * It basically gets model search query parameters from the command line and reuse Search APIs in a standalone mode. 
 *
 * File system visitors have been created to be able run Eclipse search queries on Resource coming from outside workspace.
 *  
 * Usage example :
 * ===============
 * 
 * EcoreGrep 
 *    -participants EReference, EClass 
 *    -pattern CASE 
 *    -dir /Users/lb/Documents/workspace-EMFT-Search-3.4M3/org.eclipse.emf.test.common, /Users/lb/Documents/workspace-EMFT-Search-3.4M3/org.eclipse.emf.ecore
 *    -expr "Add*"
 *    
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public class EcoreGrep {
	private final static String PATTERN_OPT_ID = "pattern";
	private final static String REGEX_PATTERN_OPT = "REGEX";
	private final static String CASE_PATTERN_OPT = "CASE";
	private final static String NORMAL_PATTERN_OPT = "NORMAL";
	
	private final static String DIR_OPT_ID = "dir";
	private final static String PARTICIPANT_OPT_ID = "participants";
	private final static String EXPR_OPT_ID = "expr";
	
	public final static EcoreGrep INSTANCE = new EcoreGrep();
	
	private EcoreGrep(){}
	
	private ModelSearchQueryTextualExpressionEnum computeQueryExpressionKind(String pattern) {
		if (REGEX_PATTERN_OPT.equals(pattern)) {
			return ModelSearchQueryTextualExpressionEnum.REGULAR_EXPRESSION;
		} else if (CASE_PATTERN_OPT.equals(pattern)) {
			return ModelSearchQueryTextualExpressionEnum.CASE_SENSITIVE;
		} else if (NORMAL_PATTERN_OPT.equals(pattern)) {
			return ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT;
		} 
		return ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT;
	}
	
	/*
	 * Usage example :
	 * ===============
	 * 
	 * EcoreGrep 
	 *    -participants EReference, EClass 
	 *    -pattern CASE 
	 *    -dir /Users/lb/Documents/workspace-EMFT-Search-3.4M3/org.eclipse.emf.test.common, /Users/lb/Documents/workspace-EMFT-Search-3.4M3/org.eclipse.emf.ecore
	 *    -expr "Add*"
	 *    
	 */
	public static void main(String[] args) {
		INSTANCE.runQuery(args);
	}

	private void runQueryOverHttp(String[] args) {
		runTextualGlobalRegexModelSearchRuntimeQueryOverHttp(
				"*", 
				Arrays.asList(new EClassifier[] { EcorePackage.Literals.ECLASS }), 
				ModelSearchQueryTextualExpressionEnum.NORMAL_TEXT, 
			Arrays.asList(args)
		);
	}
	
	private void runQuery(String[] args) {
		GnuParser p = new GnuParser();
		
		Options options = initOptions();
		
		try {
			CommandLine cl = p.parse(options, args, true);
			
			ModelSearchQueryTextualExpressionEnum pattern = computeQueryExpressionKind(cl.getOptionValue(PATTERN_OPT_ID));
			
			List<File> fLst = new ArrayList<File>();
			
			for (String fName : cl.getOptionValues(DIR_OPT_ID)) {
				fLst.add(new File(fName));
			}
			
			String expr = cl.getOptionValue(EXPR_OPT_ID)==null?NORMAL_PATTERN_OPT:cl.getOptionValue(EXPR_OPT_ID);
			
			List<EClassifier> cLst = new ArrayList<EClassifier>();
			if (!cl.hasOption(PARTICIPANT_OPT_ID)) {
				cLst.addAll(EcorePackage.eINSTANCE.getEClassifiers());
			} else {
				for (String cName : cl.getOptionValues(PARTICIPANT_OPT_ID)) {
					cLst.add(EcorePackage.eINSTANCE.getEClassifier(cName));
				}
			}

			runTextualGlobalRegexModelSearchRuntimeQuery(expr, cLst, pattern, fLst);
		} catch (ParseException e)  {
			new HelpFormatter().printHelp("EcoreGrep", options);
			return;
		}
	}
	
	private Options initOptions() {
		Option patternOpt = new Option(PATTERN_OPT_ID, true, "pattern kind for Ecore query among 'REGEX', 'NORMAL', 'CASE' (Regular Expresion, Normal *,? Compliant Pattern, Case Sensitive) ");
		patternOpt.addValue("REGEX");
		patternOpt.addValue("NORMAL");
		patternOpt.addValue("CASE");
		patternOpt.setArgs(1);

		Option exprOpt = new Option(EXPR_OPT_ID, true, "expression");
		exprOpt.setArgs(1);
		exprOpt.setRequired(true);

		Option dirOpt = new Option(DIR_OPT_ID, true, "directory list");
		dirOpt.setArgs(Option.UNLIMITED_VALUES);
		dirOpt.setRequired(true);
		
		Option participantOpt = new Option(PARTICIPANT_OPT_ID, true, "meta-element participant list");
		participantOpt.setArgs(Option.UNLIMITED_VALUES);
		
		Options options = new Options();
		options.addOption(dirOpt);
		options.addOption(exprOpt);
		options.addOption(patternOpt);
		options.addOption(participantOpt);
		
		return options;
	}
		
	public void runModelSearchRuntimeQuery(IModelSearchQuery query) {
		query.runWithoutNotifications();
		
		for (IModelResultEntry entry : query.getModelSearchResult().getResultsFlatenned()) {
			if (entry instanceof EcoreModelSearchResultEntry) {
				EcoreModelSearchResultEntry resultEntry = (EcoreModelSearchResultEntry) entry;
				System.out.println("[" + ((Resource)resultEntry.getTarget()).getURI().toFileString() + "] " +resultEntry.getModelResultFullyQualifiedName2());
			}
		}
	}

	public void runTextualGlobalRegexModelSearchRuntimeQuery(String pattern, List<EClassifier> participants, ModelSearchQueryTextualExpressionEnum textualExpression, List<File> dirList) {
		IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchFileSystemDirectoryScope(EcoreTextualEngine.ID, dirList.toArray(new File[0]));
		// Default query : NORMAL
		IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
		switch (textualExpression) {
			case REGULAR_EXPRESSION:
				query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredRegexModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
				break;
			case CASE_SENSITIVE:
				query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
				break;
		}
		runModelSearchRuntimeQuery(query);
	}
	
	public void runTextualGlobalRegexModelSearchRuntimeQueryOverHttp(String pattern, List<EClassifier> participants, ModelSearchQueryTextualExpressionEnum textualExpression, List<String> urlList) {
		IModelSearchScope<Object, Resource> scope = ModelSearchScopeFactory.INSTANCE.createModelSearchHttpScope(EcoreTextualEngine.ID, urlList.toArray(new String[0]));
		// Default query : NORMAL
		IModelSearchQuery query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredTextualModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
		switch (textualExpression) {
			case REGULAR_EXPRESSION:
				query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredRegexModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
				break;
			case CASE_SENSITIVE:
				query = new EcoreTextualModelSearchQueryBuilderHelper().buildFilteredCaseSensitiveModelSearchQuery(pattern, participants, scope, EcorePackage.eNS_URI);
				break;
		}
		runModelSearchRuntimeQuery(query);
	}
}
