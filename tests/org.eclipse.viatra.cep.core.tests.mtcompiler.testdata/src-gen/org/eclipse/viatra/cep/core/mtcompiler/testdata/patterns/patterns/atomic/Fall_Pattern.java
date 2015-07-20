package org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.patterns.atomic;

import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;
import org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.events.Fall_Event;

@SuppressWarnings("all")
public class Fall_Pattern extends AtomicEventPatternImpl {
  public Fall_Pattern() {
    super();
    setType(Fall_Event.class.getCanonicalName());
    setId("org.eclipse.viatra.cep.core.mtcompiler.testdata.patterns.patterns.atomic.fall_pattern");
  }
  
  public boolean evaluateCheckExpression() {
    return true;
  }
}
