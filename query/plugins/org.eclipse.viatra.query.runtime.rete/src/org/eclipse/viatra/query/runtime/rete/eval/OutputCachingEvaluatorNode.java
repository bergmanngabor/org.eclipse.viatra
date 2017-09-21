/*******************************************************************************
 * Copyright (c) 2010-2013, Bergmann Gabor, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bergmann Gabor - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.rete.eval;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.viatra.query.runtime.matchers.psystem.IExpressionEvaluator;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.util.Clearable;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory;
import org.eclipse.viatra.query.runtime.rete.network.Direction;
import org.eclipse.viatra.query.runtime.rete.network.ReteContainer;

/**
 * @author Bergmann Gabor
 *
 */
public class OutputCachingEvaluatorNode extends AbstractEvaluatorNode implements Clearable {
    
    /**
     * @deprecated use {@link EvaluationCore}
     */
    @Deprecated
    public OutputCachingEvaluatorNode(ReteContainer reteContainer,
            Logger logger, IExpressionEvaluator evaluator,
            Map<String, Integer> parameterPositions, int sourceTupleWidth) {
        super(reteContainer, logger, evaluator, parameterPositions, sourceTupleWidth);
        reteContainer.registerClearable(this);
    }
    
    
    /**
     * @since 1.5
     */
    public OutputCachingEvaluatorNode(ReteContainer reteContainer, EvaluatorCore core) {
        super(reteContainer, core);
        reteContainer.registerClearable(this);
    }



    Map<Tuple, Tuple> outputCache = CollectionsFactory.createMap();
    //Map<Tuple, SoftReference<Object>> opportunisticCacheResults = new WeakHashMap<Tuple, SoftReference<Object>>();
    
    @Override
    public void clear() {
        outputCache.clear();
    }

    @Override
    public void pullInto(Collection<Tuple> collector) {
        for (Tuple output : outputCache.values()) {
            collector.add(output);
        }
    }

    @Override
    public void update(Direction direction, Tuple updateElement) {
        switch (direction) {
            case INSERT:
                final Tuple insertedOutput = core.performEvaluation(updateElement);
                if (insertedOutput != null) {
                    Tuple previous = outputCache.put(updateElement, insertedOutput);
                    if (previous != null) {
                        throw new IllegalStateException(
                                String.format(
                                        "Duplicate insertion of tuple %s into node %s", 
                                        updateElement, this));
                    }
                    propagateUpdate(direction, insertedOutput);
                }
                break;
            case REVOKE:
                final Tuple revokedOutput = outputCache.remove(updateElement);
                if (revokedOutput != null) { // may be null if no result was yielded
                    propagateUpdate(direction, revokedOutput);
                }				
        }
    }


    /**
     * @deprecated use {@link EvaluationCore}
     */
    @Deprecated
    @Override
    protected Tuple tupleFromResult(Tuple incoming, Object evaluationresult) {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * @deprecated use {@link EvaluationCore}
     */
    @Deprecated
    @Override
    protected String logNodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    // TODO
//    @Override
//    public ProjectionIndexer constructIndex(TupleMask mask) {
//        if (Options.employTrivialIndexers) {
//            if (mask.isPermutationOf(sourceTupleWidth))
//                return getPermutationIndexer(mask);
//            if (nullMask.equals(mask))
//                return getNullIndexer();
//        }
//        return super.constructIndex(mask);
//    }
    
}
