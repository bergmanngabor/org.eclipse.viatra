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

package org.eclipse.viatra.query.patternlanguage.emf.tests.basic

import com.google.inject.Inject
import com.google.inject.Injector
import org.eclipse.viatra.query.patternlanguage.emf.tests.EMFPatternLanguageInjectorProvider
import org.eclipse.viatra.query.patternlanguage.validation.IssueCodes
import org.eclipse.viatra.query.patternlanguage.emf.eMFPatternLanguage.PatternModel
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidatorTester
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.viatra.query.patternlanguage.emf.tests.util.AbstractValidatorTest
import org.eclipse.viatra.query.patternlanguage.emf.validation.EMFIssueCodes
import org.eclipse.viatra.query.patternlanguage.emf.validation.EMFPatternLanguageValidator

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(EMFPatternLanguageInjectorProvider))
class ConstraintValidationTest extends AbstractValidatorTest {
    @Inject
    ParseHelper<PatternModel> parseHelper
    @Inject
    EMFPatternLanguageValidator validator
    @Inject
    Injector injector
    
    ValidatorTester<EMFPatternLanguageValidator> tester
    
    @Before
    def void initialize() {
        tester = new ValidatorTester(validator, injector)
    }
    @Test
    def intConstantCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            pattern constantCompareTest(A : Pattern) = {
                Pattern(A);
                1 == 2;
            }
        ')
        tester.validate(model).assertAll(getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT), getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT))
    }
    @Test
    def stringDoubleConstantCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            pattern constantCompareTest(A : Pattern) = {
                Pattern(A);
                1.2 == "String";
            }
        ')
        tester.validate(model).assertAll(getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT), 
            getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT),
            getErrorCode(EMFIssueCodes::LITERAL_OR_COMPUTATION_TYPE_MISMATCH_IN_COMPARE)
        )
    }
    @Test
    def enumIntConstantCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            pattern constantCompareTest(A : Pattern) = {
                Pattern(A);
                false == 2;
            }
        ')
        tester.validate(model).assertAll(getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT), 
            getWarningCode(IssueCodes::CONSTANT_COMPARE_CONSTRAINT),
            getErrorCode(EMFIssueCodes::LITERAL_OR_COMPUTATION_TYPE_MISMATCH_IN_COMPARE)
        )
    }
    @Test
    def rightVariableCompareValidation() {
        val model = parseHelper.parse('''
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/emf/2002/Ecore"
            pattern constantCompareTest(A : EClass) = {
                EClass(A);
                1 == Name2;
                ETypedElement.lowerValue(_, Name2);
            }
        ''')
        tester.validate(model).assertWarning(EMFIssueCodes::CARTESIAN_STRICT_WARNING)
    }
    @Test
    def leftVariableCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            pattern constantCompareTest(Name) = {
                Name == "Test";
                StringValue.value(Name2, Name);	// Name2 should be a single variable, e.g. _Name2
                StringValue(Name2);				// Then this line can be deleted.
            }
        ')
        tester.validate(model).assertAll(
            getInfoCode(EMFIssueCodes::MISSING_PARAMETER_TYPE)
        )
    }
    @Test
    def leftNewVariableCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"
            pattern constantCompareTest(A : Pattern) = {
                Pattern(A);
                Name2 == "Test";
                StringValue.value(Name3, Name2);	// Name3 should be a single variable, e.g. _Name3
                StringValue(Name3);					// Then this line can be deleted.
            }
        ')
        tester.validate(model).assertWarning(EMFIssueCodes::CARTESIAN_STRICT_WARNING)
    }
    @Test
    def bothVariableCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/viatra/query/patternlanguage/PatternLanguage"

            pattern constantCompareTest(Name : Pattern) = {
                Name == Name2;
                Pattern(Name2);
            }
        ')
        tester.validate(model).assertOK
    }
    @Test
    def selfCompareValidation() {
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            pattern constantCompareTest(Name) = {
                Name == Name;
            }
        ')
        tester.validate(model).assertAll(
            getErrorCode(IssueCodes::SYMBOLIC_VARIABLE_NO_POSITIVE_REFERENCE),
            getWarningCode(IssueCodes::SELF_COMPARE_CONSTRAINT),
            getWarningCode(IssueCodes::SELF_COMPARE_CONSTRAINT)
        )
    }
    @Test
     def referenceIsNotRepresentable(){
        val model = parseHelper.parse('
            package org.eclipse.viatra.query.patternlanguage.emf.tests
            import "http://www.eclipse.org/emf/2002/Ecore"

            pattern IntAndClassPattern(X : EInt, Y : EClass) {
              EInt(X);
              EClass(Y);
              EClass.eAttributes.upperBound(Y,X);
            }
        ')
        tester.validate(model).assertWarning(EMFIssueCodes::FEATURE_NOT_REPRESENTABLE)
    }
}