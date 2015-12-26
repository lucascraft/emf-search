/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * AbstractModelSearchQueryTextualExpressionMatchingHelper.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - added partial matching mode
 ******************************************************************************/

package org.eclipse.emf.search.ecore.regex;


/**
 * Describes all possible textual search queries to be implemented by users.
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 */
public abstract class AbstractModelSearchQueryTextualExpressionMatchingHelper implements IModelSearchQueryTextualExpressionMatchingHelper {

	/**
	 * Main search entry point. Triggers the right search given a matching strategy kind as a parameter.
	 */
	public boolean match(String text, String pattern, ModelSearchQueryTextualExpressionEnum matchingStrategy) {
		switch (matchingStrategy) {
			case CASE_SENSITIVE:
				return matchWithCaseSensitiveStrategy(text, pattern);
			case REGULAR_EXPRESSION:
				return matchWithRegularExpressionStrategy(text, pattern);
			case NORMAL_TEXT:
				return matchWithNormalTextStrategy(text, pattern);
		}
		return false;
	}

	/**
	 * Main search entry point. Triggers the right search given a matching strategy kind as a parameter.
	 */
	public boolean lookAt(String text, String pattern, ModelSearchQueryTextualExpressionEnum matchingStrategy) {
		switch (matchingStrategy) {
			case CASE_SENSITIVE:
				return lookAtWithCaseSensitiveStrategy(text, pattern);
			case REGULAR_EXPRESSION:
				return lookAtWithRegularExpressionStrategy(text, pattern);
			case NORMAL_TEXT:
				return lookAtWithNormalTextStrategy(text, pattern);
		}
		return false;
	}

	/**
	 * Case sensitive textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched entirely a given text, false otherwise
	 */
	abstract boolean matchWithCaseSensitiveStrategy(String text, String pattern);

	/**
	 * Normal textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched entirely a given text, false otherwise
	 */
	abstract boolean matchWithNormalTextStrategy(String text, String pattern);

	/**
	 * regular expression textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched entirely a given text, false otherwise
	 */
	abstract boolean matchWithRegularExpressionStrategy(String text, String pattern);
	
	
	
	/**
	 * Case sensitive textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched into given text, false otherwise
	 */
	abstract boolean lookAtWithCaseSensitiveStrategy(String text, String pattern);

	/**
	 * Normal textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched into given text, false otherwise
	 */
	abstract boolean lookAtWithNormalTextStrategy(String text, String pattern);

	/**
	 * regular expression textual search
	 * @param text a text to match the pattern into
	 * @param pattern a pattern to match a given text with
	 * @return true if pattern matched into given text, false otherwise
	 */
	abstract boolean lookAtWithRegularExpressionStrategy(String text, String pattern);
}
