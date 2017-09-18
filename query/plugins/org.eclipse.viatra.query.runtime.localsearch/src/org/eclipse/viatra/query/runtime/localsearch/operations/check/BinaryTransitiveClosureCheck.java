/*******************************************************************************
 * Copyright (c) 2010-2013, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.localsearch.operations.check;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.viatra.query.runtime.localsearch.MatchingFrame;
import org.eclipse.viatra.query.runtime.localsearch.exceptions.LocalSearchException;
import org.eclipse.viatra.query.runtime.localsearch.matcher.ISearchContext;
import org.eclipse.viatra.query.runtime.localsearch.matcher.MatcherReference;
import org.eclipse.viatra.query.runtime.localsearch.operations.CallOperationHelper;
import org.eclipse.viatra.query.runtime.localsearch.operations.IPatternMatcherOperation;
import org.eclipse.viatra.query.runtime.localsearch.operations.CallOperationHelper.PatternCall;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * Checking for a transitive closure expressed as a local search pattern matcher. The matched pattern must have two
 * parameters of the same model type.
 * 
 * @author Zoltan Ujhelyi
 * @noextend This class is not intended to be subclassed by clients.
 * 
 */
public class BinaryTransitiveClosureCheck extends CheckOperation implements IPatternMatcherOperation {

    private final CallOperationHelper helper;
    private PatternCall call;
    private final int sourcePosition;
    private final int targetPosition;
    
    /**
     * The source position will be matched in the called pattern to the first parameter; while target to the second.
     * 
     * @param calledQuery
     * @param sourcePosition
     * @param targetPosition
     * @since 1.5
     */
    public BinaryTransitiveClosureCheck(MatcherReference calledQuery, int sourcePosition, int targetPosition) {
        super();
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;

        helper = new CallOperationHelper(calledQuery, ImmutableMap.of(
                calledQuery.getQuery().getParameters().get(0), sourcePosition,
                calledQuery.getQuery().getParameters().get(1), targetPosition));
    }

    @Override
    public void onInitialize(MatchingFrame frame, ISearchContext context) throws LocalSearchException {
        super.onInitialize(frame, context);
        // Note: second parameter is NOT bound during execution, but the first is
        call = helper.createCall(context);
    }
    
    /**
     * @deprecated Use {@link #check(MatchingFrame, ISearchContext)} instead
     */
    @Deprecated
    protected boolean check(MatchingFrame frame) throws LocalSearchException {
        return check(frame, null);
    }

    @Override
    protected boolean check(MatchingFrame frame, ISearchContext context) throws LocalSearchException {
        Object targetValue = frame.get(targetPosition);
        Queue<Object> sourcesToEvaluate = new LinkedList<>();
        sourcesToEvaluate.add(frame.get(sourcePosition));
        Set<Object> sourceEvaluated = new HashSet<>();
        while (!sourcesToEvaluate.isEmpty()) {
            Object currentValue = sourcesToEvaluate.poll();
            sourceEvaluated.add(currentValue);
            final Object[] mappedFrame = new Object[]{currentValue, null};
            for (Tuple match : call.getAllMatches(mappedFrame)) {
                Object foundTarget = match.get(1);
                if (targetValue.equals(foundTarget)) {
                    return true;
                } else if (!sourceEvaluated.contains(foundTarget)) {
                    sourcesToEvaluate.add(foundTarget);
                }
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        String c = helper.toString();
        int p = c.indexOf('(');
        return "check     find "+c.substring(0, p)+"+"+c.substring(p);
    }

    @Override
    public List<Integer> getVariablePositions() {
        return Lists.asList(sourcePosition, targetPosition, new Integer[0]);
    }

}
