package org.eclipse.emf.search.tests.utils;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.search.core.results.IModelResultEntry;
import org.eclipse.emf.search.core.results.IModelSearchResult;
import org.eclipse.emf.search.ecore.results.EcoreModelSearchResultEntry;

public class ModelSearchResultUtils {

	public static void dumpResultsAsTextualHierarchy(IModelSearchResult results) {
        System.err.println(results.getQuery().getLabel() + " " + results.getTotalMatches());

		for (IModelResultEntry entry : results.getResultsFlatenned()) {
			if (entry instanceof EcoreModelSearchResultEntry) {
				EcoreModelSearchResultEntry resultEntry = (EcoreModelSearchResultEntry) entry;
				System.out.println("[" + ((Resource)resultEntry.getTarget()).getURI().toFileString() + "] " +resultEntry.getModelResultFullyQualifiedName2());
			}
		}
	}
}
