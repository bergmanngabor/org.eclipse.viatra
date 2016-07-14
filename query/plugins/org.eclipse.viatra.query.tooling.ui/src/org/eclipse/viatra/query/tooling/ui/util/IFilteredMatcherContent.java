/*******************************************************************************
 * Copyright (c) 2010-2016, Abel Hegedus, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Abel Hegedus - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.tooling.ui.util;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;

/**
 * Common interface for user interface objects that provide access to a matcher 
 * and a filter set for the matcher.
 * 
 * @author Abel Hegedus
 * @since 1.4
 *
 */
public interface IFilteredMatcherContent {

    ViatraQueryMatcher<?> getMatcher();
    
    IPatternMatch getFilterMatch();
    
    IFilteredMatcherCollection getParent();
}
