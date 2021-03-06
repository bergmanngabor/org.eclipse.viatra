/*******************************************************************************
 * Copyright (c) 2010-2017, Gabor Bergmann, IncQueryLabs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gabor Bergmann - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.emf.types;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.emf.helper.ViatraQueryRuntimeHelper;

/**
 * Instance tuples are of form (x), where x is an eObject instance of the given eClass or one of its subclasses <b>regardless whether it is within the scope</b>.
 * 
 * @author Bergmann Gabor
 * @since 1.6 
 */
public class EClassUnscopedTransitiveInstancesKey extends BaseEMFTypeKey<EClass> {

    public EClassUnscopedTransitiveInstancesKey(EClass emfKey) {
        super(emfKey);
    }

    @Override
    public String getPrettyPrintableName() {
        return "(unscoped) "+ViatraQueryRuntimeHelper.prettyPrintEMFType(wrappedKey);
    }

    @Override
    public String getStringID() {
        return "eClass(unscoped)#"+ ViatraQueryRuntimeHelper.prettyPrintEMFType(wrappedKey);
    }

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public boolean isEnumerable() {
        return false;
    }

}
