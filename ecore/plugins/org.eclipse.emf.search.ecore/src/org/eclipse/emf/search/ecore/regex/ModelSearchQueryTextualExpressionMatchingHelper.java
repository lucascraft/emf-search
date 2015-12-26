/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchQueryTextualExpressionMatchingHelper.java
 * 
 * Contributors:
 *		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 *		Lucas Bigeardel - added partial matching strategy
 ******************************************************************************/

package org.eclipse.emf.search.ecore.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.search.ecore.Activator;
import org.eclipse.emf.search.ecore.l10n.Messages;

/**
 * Helper wrapping different textual search kinds such as:
 * <p>
 * <li>Sensitive Case
 * <li>Normal Text
 * <li>Regular Expression
 * </p>
 * <br>
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchQueryTextualExpressionMatchingHelper extends
		AbstractModelSearchQueryTextualExpressionMatchingHelper {
	enum MatchingStrategy {
		PERFECT, PARTIAL
	}

	private static ModelSearchQueryTextualExpressionMatchingHelper instance;
	
	public static ModelSearchQueryTextualExpressionMatchingHelper getInstance() {
		return (instance == null)?(instance = new ModelSearchQueryTextualExpressionMatchingHelper()):instance;
	}
	
	@Override
	boolean lookAtWithCaseSensitiveStrategy(String text, String pattern) {
		return evaluate(text, pattern, true, false, MatchingStrategy.PARTIAL);
	}
	@Override
	boolean lookAtWithNormalTextStrategy(String text, String pattern) {
		return evaluate(text, pattern, false, false, MatchingStrategy.PARTIAL);
	}
	@Override
	boolean lookAtWithRegularExpressionStrategy(String text, String pattern) {
		return evaluate(text, pattern, false, true, MatchingStrategy.PARTIAL);
	}
	
	@Override
	boolean matchWithCaseSensitiveStrategy(String text, String pattern) {
		return evaluate(text, pattern, true, false, MatchingStrategy.PERFECT);
	}
	@Override
	boolean matchWithNormalTextStrategy(String text, String pattern) {
		return evaluate(text, pattern, false, false, MatchingStrategy.PERFECT);
	}
	@Override
	boolean matchWithRegularExpressionStrategy(String text, String pattern) {
		return evaluate(text, pattern, false, true, MatchingStrategy.PERFECT);
	}
	
	private boolean evaluate(String text, String pattern, boolean isCaseSensitive, boolean isRegex, MatchingStrategy strategy) {
		Pattern p;
		text = text==null?"":text;
		try {
			p = createPattern(pattern, isCaseSensitive, isRegex);
		} catch (PatternSyntaxException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.getString("ModelSearchQueryTextualExpressionMatchingHelper.InvalidPatternMessageError0"), e)); //$NON-NLS-1$
			return false;
		}
		 Matcher m = p.matcher(text);
		 
		 switch(strategy) {
		 	case PARTIAL:
		 		return m.lookingAt();
		 	case PERFECT:
		 		return m.matches();
		 }
		 
		 return false;
	}
	
	/**
	 * Creates a
	 * <code>Pattern<code> object from the pattern string used in Method Search.
	 * 
	 * @param pattern
	 *            a search pattern string
	 * @param isCaseSensitive
	 *            if <code>true</code>, create a case insensitive pattern
	 * @param isRegexSearch
	 *            if <code>true</code>, treat the pattern as a regular expression pattern
	 * @return a new <code>Pattern<code> object
	 * @throws <code>PatternSyntaxException</code> if the specified pattern has a syntax error
	 */
	public static Pattern createPattern(String pattern,
			boolean isCaseSensitive, boolean isRegexSearch)
			throws PatternSyntaxException {
		if (!isRegexSearch) {
			pattern = toRegexPattern(pattern);
		}

		if (!isCaseSensitive) {
			return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
					| Pattern.UNICODE_CASE | Pattern.MULTILINE);
		}

		return Pattern.compile(pattern, Pattern.MULTILINE);
	}

	/**
	 * Converts a pattern string with '*' and '?' into a regular expression
	 * pattern string.
	 * 
	 * @param pattern
	 *            a pattern string
	 * @return a regular expression pattern string
	 */
	private static String toRegexPattern(String pattern) {
		int patternLength = pattern.length();
		StringBuffer result = new StringBuffer(patternLength);

		boolean escaped = false;
		boolean quoting = false;

		for (int i = 0; i < patternLength; i++) {
			char ch = pattern.charAt(i);
			switch (ch) {
				case '*':
				{
					if (!escaped) {
						if (quoting) {
							result.append("\\E"); //$NON-NLS-1$
							quoting = false;
						}
					}
					result.append(".*"); //$NON-NLS-1$
					escaped = false;
					break;
				}
				case '?':
				{
					if (!escaped) {
						if (quoting) {
							result.append("\\E"); //$NON-NLS-1$
							quoting = false;
						}
					}
					result.append("."); //$NON-NLS-1$
					escaped = false;
					break;
				}
				case '\\':
				{
					if (!escaped) {
						escaped = true;
					} else {
						escaped = false;
						if (quoting) {
							result.append("\\E"); //$NON-NLS-1$
							quoting = false;
						}
						result.append("\\\\"); //$NON-NLS-1$
					}
					break;
				}
				default:
				{
					if (!quoting) {
						result.append("\\Q"); //$NON-NLS-1$
						quoting = true;
					}
					if (escaped && ch != '*' && ch != '?' && ch != '\\') {
						result.append('\\');
					}
					result.append(ch);
					escaped = (ch == '\\');
				}
			}
		}

		if (quoting) {
			result.append("\\E"); //$NON-NLS-1$
		}

		return result.toString();
	}
}
