package operation.queries.util;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import operation.queries.ChecklistEntryTaskCorrespondenceMatch;
import operation.queries.ChecklistEntryTaskCorrespondenceMatcher;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.runtime.matchers.psystem.PBody;
import org.eclipse.incquery.runtime.matchers.psystem.PVariable;
import org.eclipse.incquery.runtime.matchers.psystem.annotations.PAnnotation;
import org.eclipse.incquery.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.incquery.runtime.matchers.psystem.basicenumerables.TypeBinary;
import org.eclipse.incquery.runtime.matchers.psystem.basicenumerables.TypeUnary;
import org.eclipse.incquery.runtime.matchers.psystem.queries.PParameter;

/**
 * A pattern-specific query specification that can instantiate ChecklistEntryTaskCorrespondenceMatcher in a type-safe way.
 * 
 * @see ChecklistEntryTaskCorrespondenceMatcher
 * @see ChecklistEntryTaskCorrespondenceMatch
 * 
 */
@SuppressWarnings("all")
public final class ChecklistEntryTaskCorrespondenceQuerySpecification extends BaseGeneratedEMFQuerySpecification<ChecklistEntryTaskCorrespondenceMatcher> {
  /**
   * @return the singleton instance of the query specification
   * @throws IncQueryException if the pattern definition could not be loaded
   * 
   */
  public static ChecklistEntryTaskCorrespondenceQuerySpecification instance() throws IncQueryException {
    return LazyHolder.INSTANCE;
  }
  
  @Override
  protected ChecklistEntryTaskCorrespondenceMatcher instantiate(final IncQueryEngine engine) throws IncQueryException {
    return ChecklistEntryTaskCorrespondenceMatcher.on(engine);
  }
  
  @Override
  public String getFullyQualifiedName() {
    return "operation.queries.ChecklistEntryTaskCorrespondence";
  }
  
  @Override
  public List<String> getParameterNames() {
    return Arrays.asList("CLE","Task");
  }
  
  @Override
  public List<PParameter> getParameters() {
    return Arrays.asList(new PParameter("CLE", "org.eclipse.emf.ecore.EObject"),new PParameter("Task", "org.eclipse.emf.ecore.EObject"));
  }
  
  @Override
  public ChecklistEntryTaskCorrespondenceMatch newEmptyMatch() {
    return ChecklistEntryTaskCorrespondenceMatch.newEmptyMatch();
  }
  
  @Override
  public ChecklistEntryTaskCorrespondenceMatch newMatch(final Object... parameters) {
    return ChecklistEntryTaskCorrespondenceMatch.newMatch((org.eclipse.emf.ecore.EObject) parameters[0], (org.eclipse.emf.ecore.EObject) parameters[1]);
  }
  
  @Override
  public Set<PBody> doGetContainedBodies() throws IncQueryException {
    Set<PBody>  bodies = Sets.newLinkedHashSet();
    
    {
    	PBody body = new PBody(this);
    	PVariable var_CLE = body.getOrCreateVariableByName("CLE");
    	PVariable var_Task = body.getOrCreateVariableByName("Task");
    	PVariable var_TaskId = body.getOrCreateVariableByName("TaskId");
    	body.setExportedParameters(Arrays.<ExportedParameter>asList(
    		new ExportedParameter(body, var_CLE, "CLE"),
    		
    		new ExportedParameter(body, var_Task, "Task")
    	));
    new TypeUnary(body, var_Task, getClassifierLiteral("http://process/1.0", "Task"), "http://process/1.0/Task");
    new TypeBinary(body, CONTEXT, var_Task, var_TaskId, getFeatureLiteral("http://process/1.0", "ProcessElement", "id"), "http://process/1.0/ProcessElement.id");
    new TypeBinary(body, CONTEXT, var_CLE, var_TaskId, getFeatureLiteral("http://operation/1.0", "ChecklistEntry", "taskId"), "http://operation/1.0/ChecklistEntry.taskId");
    	bodies.add(body);
    }
    {
    	PAnnotation annotation = new PAnnotation("QueryBasedFeature");
    	annotation.addAttribute("feature", "task");
    	addAnnotation(annotation);
    }
    return bodies;
  }
  
  private static class LazyHolder {
    private final static ChecklistEntryTaskCorrespondenceQuerySpecification INSTANCE = make();
    
    public static ChecklistEntryTaskCorrespondenceQuerySpecification make() {
      return new ChecklistEntryTaskCorrespondenceQuerySpecification();					
    }
  }
}
