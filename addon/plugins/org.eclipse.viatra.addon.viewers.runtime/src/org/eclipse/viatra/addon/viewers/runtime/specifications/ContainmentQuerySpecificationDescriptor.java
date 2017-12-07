/*******************************************************************************
 * Copyright (c) 2010-2014, Csaba Debreceni, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Csaba Debreceni - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.addon.viewers.runtime.specifications;

import java.util.Collections;

import org.eclipse.viatra.addon.viewers.runtime.notation.NotationPackage;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.matchers.ViatraQueryRuntimeException;
import org.eclipse.viatra.query.runtime.matchers.psystem.annotations.PAnnotation;
import org.eclipse.viatra.query.runtime.matchers.psystem.annotations.ParameterReference;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.transformation.views.traceability.generic.AbstractQuerySpecificationDescriptor;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ContainmentQuerySpecificationDescriptor extends AbstractQuerySpecificationDescriptor {

    public static final String ANNOTATION_ID = "ContainsItem";
    private static final String SOURCE = "container";
    private static final String TARGET = "item";

    private final String container;
    private final String item;

    /**
     * @throws ViatraQueryRuntimeException
     */
    public ContainmentQuerySpecificationDescriptor(IQuerySpecification<?> specification, PAnnotation annotation) {
        super(specification, getTraceSource(specification, annotation), Collections.<PParameter, String> emptyMap());

        ParameterReference parameterSource = (ParameterReference) annotation.getFirstValue(SOURCE);
        String parameterSourceValue = parameterSource.getName();
        container = parameterSourceValue;

        ParameterReference parameterTarget = (ParameterReference) annotation.getFirstValue(TARGET);
        String parameterTargetValue = parameterTarget.getName();
        item = parameterTargetValue;
    }

    private static Multimap<PParameter, PParameter> getTraceSource(IQuerySpecification<?> specification,
            PAnnotation annotation) {
        Multimap<PParameter, PParameter> traces = ArrayListMultimap.create();
        ParameterReference parameterSource = (ParameterReference) annotation.getFirstValue(SOURCE);
        ParameterReference parameterTarget = (ParameterReference) annotation.getFirstValue(TARGET);

        insertToTraces(specification, traces, parameterSource.getName());
        insertToTraces(specification, traces, parameterTarget.getName());
        return traces;
    }

    private static void insertToTraces(IQuerySpecification<?> specification,
            Multimap<PParameter, PParameter> traces, String parameter) {
        String targetName = "trace<" + parameter + ">";
        PParameter var_target = new PParameter(targetName, 
                NotationPackage.eINSTANCE.getNsURI() + "||"
                + NotationPackage.eINSTANCE.getItem().getName());
        int positionOfParameter = specification.getPositionOfParameter(parameter);
        PParameter var_source = specification.getParameters().get(positionOfParameter);
        traces.put(var_target, var_source);
    }

    public String getContainer() {
        return container;
    }

    public String getItem() {
        return item;
    }
}
