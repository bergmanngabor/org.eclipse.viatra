/*******************************************************************************
 * Copyright (c) 2004-2014, Istvan David, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Istvan David - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.cep.vepl.formatting

import com.google.inject.Inject
import org.eclipse.viatra.cep.vepl.services.VeplGrammarAccess
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter
import org.eclipse.xtext.formatting.impl.FormattingConfig

// import com.google.inject.Inject;
// import org.eclipse.viatra.cep.vepl.services.VeplGrammarAccess
/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#formatting
 * on how and when to use it 
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
class VeplFormatter extends AbstractDeclarativeFormatter {

	@Inject extension VeplGrammarAccess grammar

	override protected void configureFormatting(FormattingConfig c) {

		for (k : grammar.findKeywords(",")) {
			c.setNoSpace.before(k)
			c.setSpace.after(k)
		}

		for (k : grammar.findKeywords(":")) {
			c.setNoSpace.before(k)
			c.setSpace.after(k)
		}

		for (pair : grammar.findKeywordPairs("(", ")")) {
			c.setNoSpace().before(pair.getFirst());
			c.setNoSpace().after(pair.getFirst());
			c.setNoSpace().before(pair.getSecond());
		}

		val packageKeyword = grammar.packagedModelAccess.packageKeyword_0
		c.setNoLinewrap.before(packageKeyword)

		val usages = grammar.packagedModelAccess.usagesUsageParserRuleCall_2_0
		c.setLinewrap(2).before(usages)
		c.setLinewrap(2).after(usages)

		for (k : grammar.findKeywords("uses")) {
			c.setLinewrap.before(k)
		}

		for (k : grammar.findKeywords("uses-patterns")) {
			c.setLinewrap.before(k)
		}

		//separate logical blocks with an empty line
		c.setLinewrap(2).after(grammar.eventPatternAccess.rule)
		c.setLinewrap(2).after(grammar.ruleAccess.rule)
		c.setLinewrap(2).after(grammar.sourceAccess.rule)

		//handle line breaks and indentation in patterns' and rules' bodies
		c.lineBreakAndIncrementIndentation(grammar.atomicEventPatternAccess.leftCurlyBracketKeyword_5)
		c.lineBreakAndIncrementIndentation(grammar.IQPatternEventPatternAccess.leftCurlyBracketKeyword_5)
		c.lineBreakAndIncrementIndentation(grammar.complexEventPatternAccess.leftCurlyBracketKeyword_5)
		c.lineBreakAndIncrementIndentation(grammar.ruleAccess.leftCurlyBracketKeyword_2)
		c.lineBreakAndIncrementIndentation(grammar.sourceAccess.leftCurlyBracketKeyword_2)

		c.lineBreakAndDecrementIndentation(grammar.atomicEventPatternAccess.rightCurlyBracketKeyword_8)
		c.lineBreakAndDecrementIndentation(grammar.IQPatternEventPatternAccess.rightCurlyBracketKeyword_12)
		c.lineBreakAndDecrementIndentation(grammar.complexEventPatternAccess.rightCurlyBracketKeyword_8)
		c.lineBreakAndDecrementIndentation(grammar.ruleAccess.rightCurlyBracketKeyword_9)
		c.lineBreakAndDecrementIndentation(grammar.sourceAccess.rightCurlyBracketKeyword_4)

		//handle line breaks in ATOMIC bodies
		c.setLinewrap().after(grammar.atomicEventPatternAccess.sourceAssignment_6_2)
		c.setLinewrap().after(grammar.atomicEventPatternAccess.staticBindingsAssignment_7_1)

		c.lineBreakAndIncrementIndentation(grammar.XBlockExpressionAccess.leftCurlyBracketKeyword_1)
		c.lineBreakAndDecrementIndentation(grammar.XBlockExpressionAccess.rightCurlyBracketKeyword_3)

		//handle line breaks in IQ bodies
		c.setLinewrap().after(grammar.IQPatternEventPatternAccess.iqPatternRefAssignment_8)
		c.setLinewrap().after(grammar.IQPatternEventPatternAccess.iqChangeTypeAssignment_11)

		//handle line breaks in COMPLEX bodies
		c.setLinewrap().after(grammar.complexEventPatternAccess.priorityAssignment_6_2)
		c.setLinewrap().after(grammar.complexEventExpressionAccess.augmentedExpressionParserRuleCall_1)
		c.setLinewrap().after(grammar.complexEventExpressionAccess.plainExpressionParserRuleCall_0)

		//handle line breaks in RULE bodies
		c.setLinewrap().before(grammar.ruleAccess.actionKeyword_8_0)
		c.setLinewrap().before(grammar.ruleAccess.actionHandlerKeyword_7_0)

		//time windows and multiplicity
		c.setNoSpace.before(grammar.timewindowAccess.leftSquareBracketKeyword_0)
		c.setNoSpace.after(grammar.timewindowAccess.leftSquareBracketKeyword_0)
		c.setNoSpace.before(grammar.timewindowAccess.rightSquareBracketKeyword_2)

		c.setNoSpace.before(grammar.multiplicityAccess.leftCurlyBracketKeyword_0)
		c.setNoSpace.after(grammar.multiplicityAccess.leftCurlyBracketKeyword_0)
		c.setNoSpace.before(grammar.multiplicityAccess.rightCurlyBracketKeyword_2)
		c.setNoSpace.after(grammar.multiplicityAccess.rightCurlyBracketKeyword_2)

		//complex event operators
		c.setSpace.before(grammar.complexEventOperatorAccess.rule)
		c.setSpace.after(grammar.complexEventOperatorAccess.rule)
		c.setNoLinewrap.before(grammar.complexEventOperatorAccess.rule)
		c.setNoLinewrap.after(grammar.complexEventOperatorAccess.rule)
	}

	def private setSpace(FormattingConfig c) {
		c.setSpace(" ")
	}

	def private lineBreakAndIncrementIndentation(FormattingConfig c, Keyword keyword) {
		c.setNoSpace.before(keyword)
		c.setLinewrap().after(keyword)
		c.setIndentationIncrement.after(keyword)
	}

	def private lineBreakAndDecrementIndentation(FormattingConfig c, Keyword keyword) {
		c.setLinewrap().before(keyword)
		c.setIndentationDecrement.before(keyword)
	}
}