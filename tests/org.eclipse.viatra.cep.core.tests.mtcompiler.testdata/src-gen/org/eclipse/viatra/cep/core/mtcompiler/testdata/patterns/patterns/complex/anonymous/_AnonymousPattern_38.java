package org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.patterns.complex.anonymous;

import org.eclipse.viatra.cep.core.api.patterns.ParameterizableComplexEventPattern;
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory;
import org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.patterns.complex.anonymous._AnonymousPattern_37;

@SuppressWarnings("all")
public class _AnonymousPattern_38 extends ParameterizableComplexEventPattern {
  public _AnonymousPattern_38() {
    super();
    setOperator(EventsFactory.eINSTANCE.createFOLLOWS());
    
    // contained event patterns
    addEventPatternRefrence(new _AnonymousPattern_37(), EventsFactory.eINSTANCE.createInfinite());
    setId("org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.patterns.complex.anonymous._anonymouspattern_38");
  }
}
