package operation.queries.util;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import operation.queries.DataReadByChecklistEntryMatch;
import operation.queries.DataReadByChecklistEntryMatcher;
import operation.queries.util.ChecklistEntryTaskCorrespondenceQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.runtime.matchers.psystem.PBody;
import org.eclipse.incquery.runtime.matchers.psystem.PVariable;
import org.eclipse.incquery.runtime.matchers.psystem.annotations.PAnnotation;
import org.eclipse.incquery.runtime.matchers.psystem.annotations.ParameterReference;
import org.eclipse.incquery.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.incquery.runtime.matchers.psystem.basicenumerables.PositivePatternCall;
import org.eclipse.incquery.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.incquery.runtime.matchers.tuple.FlatTuple;
import system.queries.util.DataTaskReadCorrespondenceQuerySpecification;

/**
 * A pattern-specific query specification that can instantiate DataReadByChecklistEntryMatcher in a type-safe way.
 * 
 * @see DataReadByChecklistEntryMatcher
 * @see DataReadByChecklistEntryMatch
 * 
 */
@SuppressWarnings("all")
public final class DataReadByChecklistEntryQuerySpecification extends BaseGeneratedEMFQuerySpecification<DataReadByChecklistEntryMatcher> {
  /**
   * @return the singleton instance of the query specification
   * @throws IncQueryException if the pattern definition could not be loaded
   * 
   */
  public static DataReadByChecklistEntryQuerySpecification instance() throws IncQueryException {
    return LazyHolder.INSTANCE;
  }
  
  @Override
  protected DataReadByChecklistEntryMatcher instantiate(final IncQueryEngine engine) throws IncQueryException {
    return DataReadByChecklistEntryMatcher.on(engine);
  }
  
  @Override
  public String getFullyQualifiedName() {
    return "operation.queries.DataReadByChecklistEntry";
  }
  
  @Override
  public List<String> getParameterNames() {
    return Arrays.asList("CLE","Task","Data");
  }
  
  @Override
  public List<PParameter> getParameters() {
    return Arrays.asList(new PParameter("CLE", "org.eclipse.emf.ecore.EObject"),new PParameter("Task", "org.eclipse.emf.ecore.EObject"),new PParameter("Data", "org.eclipse.emf.ecore.EObject"));
  }
  
  @Override
  public DataReadByChecklistEntryMatch newEmptyMatch() {
    return DataReadByChecklistEntryMatch.newEmptyMatch();
  }
  
  @Override
  public DataReadByChecklistEntryMatch newMatch(final Object... parameters) {
    return DataReadByChecklistEntryMatch.newMatch((org.eclipse.emf.ecore.EObject) parameters[0], (org.eclipse.emf.ecore.EObject) parameters[1], (org.eclipse.emf.ecore.EObject) parameters[2]);
  }
  
  @Override
  public Set<PBody> doGetContainedBodies() throws IncQueryException {
    Set<PBody>  bodies = Sets.newLinkedHashSet();
    
    {
    	PBody body = new PBody(this);
    	PVariable var_CLE = body.getOrCreateVariableByName("CLE");
    	PVariable var_Task = body.getOrCreateVariableByName("Task");
    	PVariable var_Data = body.getOrCreateVariableByName("Data");
    	body.setExportedParameters(Arrays.<ExportedParameter>asList(
    		new ExportedParameter(body, var_CLE, "CLE"),
    		
    		new ExportedParameter(body, var_Task, "Task"),
    		
    		new ExportedParameter(body, var_Data, "Data")
    	));
    	new PositivePatternCall(body, new FlatTuple(var_CLE, var_Task), ChecklistEntryTaskCorrespondenceQuerySpecification.instance());
    	new PositivePatternCall(body, new FlatTuple(var_Data, var_Task), DataTaskReadCorrespondenceQuerySpecification.instance());
    	bodies.add(body);
    }
    {
    	PAnnotation annotation = new PAnnotation("Constraint");
    	annotation.addAttribute("severity", "warning");
    	annotation.addAttribute("location", new ParameterReference("CLE"));
    	annotation.addAttribute("message", "Entry $CLE.name$ connected to $Data.name$ through $Task.name$");
    	addAnnotation(annotation);
    }
    return bodies;
  }
  
  private static class LazyHolder {
    private final static DataReadByChecklistEntryQuerySpecification INSTANCE = make();
    
    public static DataReadByChecklistEntryQuerySpecification make() {
      return new DataReadByChecklistEntryQuerySpecification();					
    }
  }
}
