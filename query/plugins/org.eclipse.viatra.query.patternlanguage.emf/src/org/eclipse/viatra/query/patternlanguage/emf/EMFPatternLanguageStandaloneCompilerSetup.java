/*******************************************************************************
 * Copyright (c) 2010-2017, Zoltan Ujhelyi, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.patternlanguage.emf;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Zoltan Ujhelyi
 * @since 2.0
 *
 */
public class EMFPatternLanguageStandaloneCompilerSetup extends EMFPatternLanguageStandaloneSetup {

    @Override
    public Injector createInjector() {
        return Guice.createInjector(new EMFPatternLanguageStandaloneCompilerModule());
    }
}
