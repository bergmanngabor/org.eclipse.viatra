/**
 * Copyright (c) 2010-2016, Peter Lunk, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Peter Lunk - initial API and implementation
 */
package org.eclipse.viatra.transformation.debug.ui.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.viatra.transformation.debug.ui.activator.TransformationDebugUIActivator;
import org.eclipse.xtend.ide.XtendExecutableExtensionFactory;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class DebugExecutableExtensionFactory extends XtendExecutableExtensionFactory {

    @Override
    protected Bundle getBundle() {
        return Platform.getBundle(TransformationDebugUIActivator.PLUGIN_ID);
    }

}
