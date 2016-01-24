/*******************************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * ModelSearchResultEvent.java
 * 
 * Contributors: 
 * 		Lucas Bigeardel (Anyware Technologies) - initial API and implementation
 * 		Lucas Bigeardel - javadoc
 ******************************************************************************/

package org.eclipse.papyrus.infra.emf.search.core.results;

import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.SearchResultEvent;

/**
 * A ModelExtensibleSearchResultEvent event is thrown whenever a new Model Search Result computation finishes
 * 
 * @author <a href="mailto:lucas.bigeardel@gmail.com">lucas.bigeardel@gmail.com</a>
 *
 */
public final class ModelSearchResultEvent extends SearchResultEvent {
    private static final long serialVersionUID = 416711166772223056L;
    
    /**
     * Constructor
     * 
     * @param searchResult search result which originated the event
     */
    public ModelSearchResultEvent(ISearchResult searchResult){
        super(searchResult);
    }
}
