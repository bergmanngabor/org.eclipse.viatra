/*******************************************************************************
 * Copyright (c) 2004-2015, Istvan David, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Istvan David - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.cep.core.experimental.mtcompiler.rules

import com.google.common.base.Preconditions
import org.eclipse.viatra.cep.core.experimental.mtcompiler.AndPatternMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.ComplexAndTransitionMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.ComplexFollowsTransitionMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.ComplexNotTransitionMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.ComplexOrTransitionMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.FollowsPatternMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.NonUnfoldedNotTransitionMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.NotPatternMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.OrPatternMatcher
import org.eclipse.viatra.cep.core.experimental.mtcompiler.builders.BuilderPrimitives
import org.eclipse.viatra.cep.core.experimental.mtcompiler.builders.ComplexMappingUtils
import org.eclipse.viatra.cep.core.metamodels.automaton.Automaton
import org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel
import org.eclipse.viatra.cep.core.metamodels.automaton.NegativeTransition
import org.eclipse.viatra.cep.core.metamodels.automaton.Transition
import org.eclipse.viatra.cep.core.metamodels.automaton.TypedTransition
import org.eclipse.viatra.cep.core.metamodels.events.AND
import org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern
import org.eclipse.viatra.cep.core.metamodels.events.FOLLOWS
import org.eclipse.viatra.cep.core.metamodels.events.OR
import org.eclipse.viatra.cep.core.metamodels.trace.TraceModel

class ComplexMappingRules extends MappingRules {

	val extension ComplexMappingUtils complexMappingUtils
	val extension BuilderPrimitives builderPrimitives

	new(InternalModel internalModel, TraceModel traceModel) {
		super(internalModel, traceModel)
		complexMappingUtils = new ComplexMappingUtils(traceModel)
		builderPrimitives = new BuilderPrimitives(traceModel)
	}

	override getAllRules() {
		return #[
			notPattern2AutomatonRule,
			notUnfoldRule,
			negativeTransitionUnfoldRule,
			followsPattern2AutomatonRule,
			orPattern2AutomatonRule,
			followUnfoldRule,
			orUnfoldRule,
			andPattern2AutomatonRule,
			andUnfoldRule
		]
	}

	/**
	 * Transformation rule to compile {@link ComplexEventPattern}s with a {@link FOLLOWS} operator to {@link Automaton}.
	 */
	val followsPattern2AutomatonRule = createRule(FollowsPatternMatcher::querySpecification) [
		val mappedAutomaton = checkForMappedAutomaton(eventPattern)
		if (mappedAutomaton != null) {
			createTrace(eventPattern, mappedAutomaton)
			return
		}

		var automaton = eventPattern.initializeAutomaton
		val finalState = createFinalState
		automaton.states += finalState

		automaton.buildFollowsPath(eventPattern, automaton.initialState, finalState)

		createTrace(eventPattern, automaton)
	]

	/**
	 * Transformation rule to unfold {@link Transition}s in an {@link Automaton} guarded by a
	 * {@link ComplexEventPattern} with a {@link FOLLOWS} operator as a type.
	 */
	val followUnfoldRule = createRule(ComplexFollowsTransitionMatcher::querySpecification) [
		val referredEventPattern = transition.guards.head.eventType as ComplexEventPattern

		automaton.unfoldFollowsPath(referredEventPattern, transition)

		removeTransition(transition)
	]

	/**
	 * Transformation rule to compile {@link ComplexEventPattern}s with an {@link OR} operator to {@link Automaton}.
	 */
	val orPattern2AutomatonRule = createRule(OrPatternMatcher::querySpecification) [
		val mappedAutomaton = checkForMappedAutomaton(eventPattern)
		if (mappedAutomaton != null) {
			createTrace(eventPattern, mappedAutomaton)
			return
		}

		var automaton = eventPattern.initializeAutomaton
		val finalState = createFinalState
		automaton.states += finalState

		automaton.buildOrPath(eventPattern, automaton.initialState, finalState)

		createTrace(eventPattern, automaton)
	]

	/**
	 * Transformation rule to unfold {@link Transition}s in an {@link Automaton} guarded by a
	 * {@link ComplexEventPattern} with an {@link OR} operator as a type.
	 */
	val orUnfoldRule = createRule(ComplexOrTransitionMatcher::querySpecification) [
		val referredEventPattern = transition.guards.head.eventType as ComplexEventPattern

		automaton.unfoldOrPath(referredEventPattern, transition)

		removeTransition(transition)
	]

	/**
	 * Transformation rule to compile {@link ComplexEventPattern}s with an {@link AND} operator to {@link Automaton}.
	 */
	val andPattern2AutomatonRule = createRule(AndPatternMatcher::querySpecification) [
		val mappedAutomaton = checkForMappedAutomaton(eventPattern)
		if (mappedAutomaton != null) {
			createTrace(eventPattern, mappedAutomaton)
			return
		}

		var automaton = eventPattern.initializeAutomaton
		val finalState = createFinalState
		automaton.states += finalState

		automaton.buildAndPath(eventPattern, automaton.initialState, finalState)

		createTrace(eventPattern, automaton)
	]

	/**
	 * Transformation rule to unfold {@link Transition}s in an {@link Automaton} guarded by a
	 * {@link ComplexEventPattern} with an {@link AND} operator as a type.
	 */
	val andUnfoldRule = createRule(ComplexAndTransitionMatcher::querySpecification) [
		val referredEventPattern = transition.guards.head.eventType as ComplexEventPattern

		automaton.unfoldAndPath(referredEventPattern, transition)

		removeTransition(transition)
	]

	/**
	 * Transformation rule to compile {@link ComplexEventPattern}s with a {@link NOT} operator to {@link Automaton}.
	 */
	val notPattern2AutomatonRule = createRule(NotPatternMatcher::querySpecification) [
		val mappedAutomaton = checkForMappedAutomaton(eventPattern)
		if (mappedAutomaton != null) {
			createTrace(eventPattern, mappedAutomaton)
			return
		}

		var automaton = eventPattern.initializeAutomaton
		val finalState = createFinalState
		automaton.states += finalState

		Preconditions::checkArgument(eventPattern.containedEventPatterns.size == 1)

		automaton.buildNotPath(eventPattern.containedEventPatterns.head.eventPattern, automaton.initialState,
			finalState)

		createTrace(eventPattern, automaton)
	]

	/**
	 * Transformation rule to unfold {@link TypedTransition}s in an {@link Automaton} guarded with a {@link NOT}
	 * operator.
	 */
	val notUnfoldRule = createRule(NonUnfoldedNotTransitionMatcher::querySpecification) [
		Preconditions::checkArgument(eventPattern.containedEventPatterns.size == 1)

		automaton.buildNotPath(
			eventPattern.containedEventPatterns.head.eventPattern,
			transition.preState,
			transition.postState
		)

		removeTransition(transition)
	]

	/**
	 * Transformation rule to unfold {@link NegativeTransition}s in an {@link Automaton} guarded by a
	 * {@link ComplexEventPattern}.
	 */
	val negativeTransitionUnfoldRule = createRule(ComplexNotTransitionMatcher::querySpecification) [
		automaton.unfoldNotPath(eventPattern, transition as NegativeTransition)

		removeTransition(transition)
	]
}