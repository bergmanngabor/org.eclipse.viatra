/**
 */
package org.eclipse.incquery.snapshot.EIQSnapshot.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import org.eclipse.incquery.snapshot.EIQSnapshot.util.EIQSnapshotAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EIQSnapshotItemProviderAdapterFactory extends EIQSnapshotAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EIQSnapshotItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.MatchSetRecord} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatchSetRecordItemProvider matchSetRecordItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.MatchSetRecord}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMatchSetRecordAdapter() {
		if (matchSetRecordItemProvider == null) {
			matchSetRecordItemProvider = new MatchSetRecordItemProvider(this);
		}

		return matchSetRecordItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.MatchRecord} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatchRecordItemProvider matchRecordItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.MatchRecord}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMatchRecordAdapter() {
		if (matchRecordItemProvider == null) {
			matchRecordItemProvider = new MatchRecordItemProvider(this);
		}

		return matchRecordItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.EMFSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EMFSubstitutionItemProvider emfSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.EMFSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEMFSubstitutionAdapter() {
		if (emfSubstitutionItemProvider == null) {
			emfSubstitutionItemProvider = new EMFSubstitutionItemProvider(this);
		}

		return emfSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.IntSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntSubstitutionItemProvider intSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.IntSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createIntSubstitutionAdapter() {
		if (intSubstitutionItemProvider == null) {
			intSubstitutionItemProvider = new IntSubstitutionItemProvider(this);
		}

		return intSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.LongSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LongSubstitutionItemProvider longSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.LongSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLongSubstitutionAdapter() {
		if (longSubstitutionItemProvider == null) {
			longSubstitutionItemProvider = new LongSubstitutionItemProvider(this);
		}

		return longSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.DoubleSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DoubleSubstitutionItemProvider doubleSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.DoubleSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDoubleSubstitutionAdapter() {
		if (doubleSubstitutionItemProvider == null) {
			doubleSubstitutionItemProvider = new DoubleSubstitutionItemProvider(this);
		}

		return doubleSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.FloatSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FloatSubstitutionItemProvider floatSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.FloatSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createFloatSubstitutionAdapter() {
		if (floatSubstitutionItemProvider == null) {
			floatSubstitutionItemProvider = new FloatSubstitutionItemProvider(this);
		}

		return floatSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.BooleanSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanSubstitutionItemProvider booleanSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.BooleanSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBooleanSubstitutionAdapter() {
		if (booleanSubstitutionItemProvider == null) {
			booleanSubstitutionItemProvider = new BooleanSubstitutionItemProvider(this);
		}

		return booleanSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.StringSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringSubstitutionItemProvider stringSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.StringSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStringSubstitutionAdapter() {
		if (stringSubstitutionItemProvider == null) {
			stringSubstitutionItemProvider = new StringSubstitutionItemProvider(this);
		}

		return stringSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.DateSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DateSubstitutionItemProvider dateSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.DateSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDateSubstitutionAdapter() {
		if (dateSubstitutionItemProvider == null) {
			dateSubstitutionItemProvider = new DateSubstitutionItemProvider(this);
		}

		return dateSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.EnumSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumSubstitutionItemProvider enumSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.EnumSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEnumSubstitutionAdapter() {
		if (enumSubstitutionItemProvider == null) {
			enumSubstitutionItemProvider = new EnumSubstitutionItemProvider(this);
		}

		return enumSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.MiscellaneousSubstitution} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MiscellaneousSubstitutionItemProvider miscellaneousSubstitutionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.MiscellaneousSubstitution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMiscellaneousSubstitutionAdapter() {
		if (miscellaneousSubstitutionItemProvider == null) {
			miscellaneousSubstitutionItemProvider = new MiscellaneousSubstitutionItemProvider(this);
		}

		return miscellaneousSubstitutionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.incquery.snapshot.EIQSnapshot.IncQuerySnapshot} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncQuerySnapshotItemProvider incQuerySnapshotItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.incquery.snapshot.EIQSnapshot.IncQuerySnapshot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createIncQuerySnapshotAdapter() {
		if (incQuerySnapshotItemProvider == null) {
			incQuerySnapshotItemProvider = new IncQuerySnapshotItemProvider(this);
		}

		return incQuerySnapshotItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (matchSetRecordItemProvider != null) matchSetRecordItemProvider.dispose();
		if (matchRecordItemProvider != null) matchRecordItemProvider.dispose();
		if (emfSubstitutionItemProvider != null) emfSubstitutionItemProvider.dispose();
		if (intSubstitutionItemProvider != null) intSubstitutionItemProvider.dispose();
		if (longSubstitutionItemProvider != null) longSubstitutionItemProvider.dispose();
		if (doubleSubstitutionItemProvider != null) doubleSubstitutionItemProvider.dispose();
		if (floatSubstitutionItemProvider != null) floatSubstitutionItemProvider.dispose();
		if (booleanSubstitutionItemProvider != null) booleanSubstitutionItemProvider.dispose();
		if (stringSubstitutionItemProvider != null) stringSubstitutionItemProvider.dispose();
		if (dateSubstitutionItemProvider != null) dateSubstitutionItemProvider.dispose();
		if (enumSubstitutionItemProvider != null) enumSubstitutionItemProvider.dispose();
		if (miscellaneousSubstitutionItemProvider != null) miscellaneousSubstitutionItemProvider.dispose();
		if (incQuerySnapshotItemProvider != null) incQuerySnapshotItemProvider.dispose();
	}

}
