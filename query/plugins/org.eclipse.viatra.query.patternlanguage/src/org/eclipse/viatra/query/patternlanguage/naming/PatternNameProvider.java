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

package org.eclipse.viatra.query.patternlanguage.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra.query.patternlanguage.helper.CorePatternLanguageHelper;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.Pattern;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.PatternBody;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.PatternModel;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;

import com.google.inject.Inject;

public class PatternNameProvider extends XbaseQualifiedNameProvider {

    @Inject
    private IQualifiedNameConverter nameConverter;

    @Override
    public QualifiedName getFullyQualifiedName(EObject obj) {
        if (obj instanceof PatternModel) {
            PatternModel model = (PatternModel) obj;
            String modelClassName = StringExtensions.toFirstUpper(CorePatternLanguageHelper.getModelFileName(model));
            String modelPackageName = model.getPackageName();
            if (modelClassName != null && !modelClassName.isEmpty() && modelPackageName != null && !modelPackageName.isEmpty()) {
                return nameConverter.toQualifiedName(model.getPackageName()).append(modelClassName);
            }
        } if (obj instanceof Pattern) {
            Pattern pattern = (Pattern) obj;
            return nameConverter.toQualifiedName(CorePatternLanguageHelper.getFullyQualifiedName(pattern));
        } else if (obj instanceof PatternBody) {
            PatternBody patternBody = (PatternBody) obj;
            Pattern pattern = (Pattern) patternBody.eContainer();
            return getFullyQualifiedName(pattern).append(Integer.toString(pattern.getBodies().indexOf(patternBody)));
        }
        return super.getFullyQualifiedName(obj);
    }

}
