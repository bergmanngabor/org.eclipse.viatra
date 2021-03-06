/*******************************************************************************
 * Copyright (c) 2010-2016, Gabor Bergmann, IncQueryLabs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gabor Bergmann - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.psystem.aggregations;

/**
 * 
 * An aggregation operator that does not store interim results beyond the final aggregated value.
 * @author Gabor Bergmann
 * @since 1.4
 */
public abstract class AbstractMemorylessAggregationOperator<Domain, AggregateResult> 
    implements IMultisetAggregationOperator<Domain, AggregateResult, AggregateResult> 
{

    @Override
    public AggregateResult getAggregate(AggregateResult result) {
        return result;
    }

}
