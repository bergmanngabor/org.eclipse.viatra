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
package org.eclipse.viatra.query.runtime.matchers.util;

/**
 * An {@link IMemory} that represents the difference between two states of a set or {@link IMultiset}, and therefore
 *  may contain values with a negative multiplicity.
 * 
 * @author Gabor Bergmann
 * @since 1.7
 */
public interface IDeltaBag<T> extends IMemory<T> {

}
