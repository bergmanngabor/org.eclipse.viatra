package org.eclipse.incquery.uml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.emf.EMFScope;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.uml.derivedfeatures.AssociationEndTypeMatcher;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class TestUmlDerivedFeatures {

	private static final UMLFactory FACTORY = UMLFactory.eINSTANCE;

	private static IncQueryEngine getEngine(Resource resource) throws IncQueryException {
		return IncQueryEngine.on(new EMFScope(resource));
	}
	
	private static Resource createResource() {
		return new ResourceSetImpl().createResource(URI.createURI("test"));
	}

	@Test
	public void associationEndType() throws IncQueryException {
		Resource resource = createResource();
		Association association = FACTORY.createAssociation();
		resource.getContents().add(association);
		Property memberEnd = FACTORY.createProperty();
		resource.getContents().add(memberEnd);
		association.getMemberEnds().add(memberEnd);
		Type endType = FACTORY.createClass();
		resource.getContents().add(endType);
		memberEnd.setType(endType);
		AssociationEndTypeMatcher matcher = AssociationEndTypeMatcher.on(getEngine(resource));
		assertEquals(ImmutableSet.of(endType), matcher.getAllValuesOftype());
	}

	@Test
	public void listsWellbehaving() {
		Activity source = FACTORY.createActivity();
		final AtomicBoolean added = new AtomicBoolean(false);
		final AtomicBoolean removed = new AtomicBoolean(false);
		final ActivityPartition activityPartition = FACTORY.createActivityPartition();
		source.eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == UMLPackage.Literals.ACTIVITY__GROUP) {
					if (msg.getEventType() == Notification.ADD && msg.getNewValue() == activityPartition) {
						added.set(true);
					}
					if (msg.getEventType() == Notification.REMOVE && msg.getOldValue() == activityPartition) {
						removed.set(true);
					}
				}
			}
		});
		source.getGroups().add(activityPartition);
		assertTrue(added.get());
		source.getGroups().remove(activityPartition);
		assertTrue(removed.get());
	}
}
