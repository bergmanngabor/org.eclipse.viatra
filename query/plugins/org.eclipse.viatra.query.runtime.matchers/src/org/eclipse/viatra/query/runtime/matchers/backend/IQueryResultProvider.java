/*******************************************************************************
 * Copyright (c) 2010-2014, Bergmann Gabor, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bergmann Gabor - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.backend;

import java.util.Collection;

import org.eclipse.viatra.query.runtime.matchers.tuple.ITuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.TupleMask;

/**
 * An internal interface of the query backend that provides results of a given query. 
 * @author Bergmann Gabor
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IQueryResultProvider {

    /**
     * Decides whether there are any matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameters
     *            array where each non-null element binds the corresponding pattern parameter to a fixed value.
     * @pre size of input array must be equal to the number of parameters.
     * @since 2.0
     */
    public boolean hasMatch(Object[] parameters);
    
    /**
     * Decides whether there are any matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameterSeedMask
     *            a mask that extracts those parameters of the query (from the entire parameter list) that should be
     *            bound to a fixed value
     * @param parameters
     *            the tuple of fixed values restricting the match set to be considered, in the same order as given in
     *            parameterSeedMask, so that for each considered match tuple,
     *            projectedParameterSeed.equals(parameterSeedMask.transform(match)) should hold
     * @since 2.0
     */
    public boolean hasMatch(TupleMask parameterSeedMask, ITuple projectedParameterSeed);
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameters
     *            array where each non-null element binds the corresponding pattern parameter to a fixed value.
     * @pre size of input array must be equal to the number of parameters.
     * @return the number of pattern matches found.
     */
    public int countMatches(Object[] parameters);
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameterSeedMask
     *            a mask that extracts those parameters of the query (from the entire parameter list) that should be
     *            bound to a fixed value
     * @param parameters
     *            the tuple of fixed values restricting the match set to be considered, in the same order as given in
     *            parameterSeedMask, so that for each considered match tuple,
     *            projectedParameterSeed.equals(parameterSeedMask.transform(match)) should hold
     * @return the number of pattern matches found.
     * @since 1.7
     */
    public int countMatches(TupleMask parameterSeedMask, ITuple projectedParameterSeed);
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     *
     * @param parameters
     *            array where each non-null element binds the corresponding pattern parameter to a fixed value.
     * @pre size of input array must be equal to the number of parameters.
     * @return a match represented in the internal {@link Tuple} representation.
     */
    public Tuple getOneArbitraryMatch(Object[] parameters);
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * 
     * @param parameterSeedMask
     *            a mask that extracts those parameters of the query (from the entire parameter list) that should be
     *            bound to a fixed value
     * @param parameters
     *            the tuple of fixed values restricting the match set to be considered, in the same order as given in
     *            parameterSeedMask, so that for each considered match tuple,
     *            projectedParameterSeed.equals(parameterSeedMask.transform(match)) should hold
     * @return a match represented in the internal {@link Tuple} representation.
     * @since 1.7
     */
    public Tuple getOneArbitraryMatch(TupleMask parameterSeedMask, ITuple parameters); 
    
    /**
     * Returns the set of all matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameters
     *            array where each non-null element binds the corresponding pattern parameter to a fixed value.
     * @pre size of input array must be equal to the number of parameters.
     * @return matches represented in the internal {@link Tuple} representation.
     */
    public Collection<? extends Tuple> getAllMatches(Object[] parameters);
    
    /**
     * Returns the set of all matches of the pattern that conform to the given fixed values of some parameters.
     *
     * @param parameterSeedMask
     *            a mask that extracts those parameters of the query (from the entire parameter list) that should be
     *            bound to a fixed value
     * @param parameters
     *            the tuple of fixed values restricting the match set to be considered, in the same order as given in
     *            parameterSeedMask, so that for each considered match tuple,
     *            projectedParameterSeed.equals(parameterSeedMask.transform(match)) should hold
     * @return matches represented in the internal {@link Tuple} representation.
     * @since 1.7
     */
    public Iterable<? extends Tuple> getAllMatches(TupleMask parameterSeedMask, ITuple parameters); 
    
    /**
     * The underlying query evaluator backend.
     */
    public IQueryBackend getQueryBackend();
    
    /**
     * Internal method that registers low-level callbacks for match appearance and disappearance.
     * 
     * <p>
     * <b>Caution: </b> This is a low-level callback that is invoked when the pattern matcher is not necessarily in a
     * consistent state yet. Importantly, no model modification permitted during the callback.
     * 
     * <p>
     * The callback can be unregistered via invoking {@link #removeUpdateListener(Object)} with the same tag.
     * 
     * @param listener
     *            the listener that will be notified of each new match that appears or disappears, starting from now.
     * @param listenerTag
     *            a tag by which to identify the listener for later removal by {@link #removeUpdateListener(Object)}. 
     * @param fireNow
     *            if true, the insertion update allback will be immediately invoked on all current matches as a one-time effect. 
     *            
     * @throws UnsupportedOperationException if this is a non-incremental backend 
     * 	(i.e. {@link IQueryBackend#isCaching()} on {@link #getQueryBackend()} returns false)
     */	
    public void addUpdateListener(final IUpdateable listener, final Object listenerTag, boolean fireNow);
    
    /**
     * Removes an existing listener previously registered with the given tag.
     *            
     * @throws UnsupportedOperationException if this is a non-incremental backend 
     * 	(i.e. {@link IQueryBackend#isCaching()} on {@link #getQueryBackend()} returns false)
     */
    public void removeUpdateListener(final Object listenerTag);

}
