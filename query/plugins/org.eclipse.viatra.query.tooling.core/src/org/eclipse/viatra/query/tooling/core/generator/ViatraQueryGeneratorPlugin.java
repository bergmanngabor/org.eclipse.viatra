/*******************************************************************************
 * Copyright (c) 2010-2012, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.query.tooling.core.generator;

import org.osgi.framework.BundleContext;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ViatraQueryGeneratorPlugin extends AbstractUIPlugin {

    private static BundleContext context;
    public static ViatraQueryGeneratorPlugin INSTANCE;
    public static final String BUNDLE_ID = "org.eclipse.viatra.query.tooling.core";

    public static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        INSTANCE = this;
        ViatraQueryGeneratorPlugin.context = bundleContext;
        super.start(bundleContext);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        super.stop(bundleContext);
        ViatraQueryGeneratorPlugin.context = null;
        INSTANCE = null;
    }


}
