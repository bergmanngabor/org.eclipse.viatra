/**
 * Copyright (c) 2010-2016, Peter Lunk, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Peter Lunk - initial API and implementation
 */
package org.eclipse.viatra.transformation.debug.ui.views.modelinstancebrowser;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.viatra.transformation.debug.communication.IDebuggerHostAgent;
import org.eclipse.viatra.transformation.debug.communication.IDebuggerHostAgentListener;
import org.eclipse.viatra.transformation.debug.model.TransformationStackFrame;
import org.eclipse.viatra.transformation.debug.model.TransformationThread;
import org.eclipse.viatra.transformation.debug.model.transformationstate.TransformationModelElement;
import org.eclipse.viatra.transformation.debug.model.transformationstate.TransformationState;
import org.eclipse.viatra.transformation.debug.ui.activator.TransformationDebugUIActivator;

import com.google.common.collect.Maps;

public class ModelInstanceViewer extends ViewPart implements IDebuggerHostAgentListener {
    private static final String DEBUG_VIEW = "org.eclipse.debug.ui.DebugView";

    public static final String ID = "org.eclipse.viatra.transformation.ui.debug.TransformationViewer";

    private CTabFolder tabFolder;
    private Composite composite;

    private TransformationThread currentThread;
    private Map<CTabItem, TreeViewer> tabMap = Maps.newHashMap();

    private TabbedSelectionProviderWrapper selectionProviderWrapper;
    
    
    @Override
    public void createPartControl(Composite parent) {
        ISelectionService sService = getSite().getWorkbenchWindow().getSelectionService();


        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));

        tabFolder = new CTabFolder(composite, SWT.BORDER);
        tabFolder.setBorderVisible(true);
        tabFolder.setSelectionBackground(
                Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

        selectionProviderWrapper = new TabbedSelectionProviderWrapper();
        getSite().setSelectionProvider(selectionProviderWrapper);
        tabFolder.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.item instanceof CTabItem) {
                    selectionProviderWrapper.setActiveProvider(tabMap.get(tabFolder.getSelection()));
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }

        });
        
        ISelectionListener listener = new ISelectionListener() {
            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                if (!selection.isEmpty() && selection instanceof StructuredSelection) {
                    
                    Object firstElement = ((StructuredSelection) selection).getFirstElement();
                    try {
                        if (firstElement instanceof TransformationThread) {
                            currentThread = (TransformationThread) firstElement;
                            maintainTabs();
                            currentThread.getHostAgent().registerDebuggerHostAgentListener(ModelInstanceViewer.this);
                        } else if(firstElement instanceof TransformationStackFrame){
                            TransformationThread thread = (TransformationThread) ((TransformationStackFrame) firstElement).getThread();
                            currentThread =  thread;
                            maintainTabs();
                            currentThread.getHostAgent().registerDebuggerHostAgentListener(ModelInstanceViewer.this);
                        }
                    } catch (Exception e) {
                        TransformationDebugUIActivator.getDefault().logException(e.getMessage(), e);
                        ErrorDialog.openError(composite.getShell(), "An error has occured", e.getMessage(),
                                new Status(IStatus.ERROR, TransformationDebugUIActivator.PLUGIN_ID, e.getMessage()));
                    }
                    
                }

            }
        };

        sService.addSelectionListener(DEBUG_VIEW, listener);
        
    }
    
    
    private void maintainTabs() {
        List<TransformationModelElement> rootElements = currentThread.getModelProvider().getRootElements();
        
        tabMap.clear();
        for (CTabItem item : tabFolder.getItems()) {
            item.getControl().dispose();
            item.dispose();
        }
        for (TransformationModelElement element : rootElements) {
            CTabItem ritem = new CTabItem(tabFolder, SWT.NONE);
            String nameAttribute = ((TransformationModelElement) element).getNameAttribute();
            ritem.setText(((TransformationModelElement) element).getTypeAttribute()
                    + ((nameAttribute == "") ? " " : (" \"" + nameAttribute + "\" ")));
            
            TreeViewer treeViewer = new TreeViewer(tabFolder, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.VIRTUAL);

            ritem.setControl(treeViewer.getTree());
            tabMap.put(ritem, treeViewer);

            treeViewer.setContentProvider(new TransformationModelElementContentProvider(currentThread.getModelProvider()));
            treeViewer.setLabelProvider(new TransformationModelElementLabelProvider());

            treeViewer.setInput(new TransformationModelElement[]{element});
            ritem.setControl(treeViewer.getControl());
        }
        if (tabFolder.getItems().length > 0) {
            tabFolder.setSelection(tabFolder.getItems()[0]);
            selectionProviderWrapper.setActiveProvider(tabMap.get(tabFolder.getSelection()));
        }

    }

    
    
    
    
    @Override
    public void transformationStateChanged(TransformationState state) {
        if(currentThread.getTransformationState().equals(state)){
            tabFolder.getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    maintainTabs();
                }
            });
        }
        
    }

    @Override
    public void terminated(IDebuggerHostAgent agent) throws CoreException {
        if(currentThread.getHostAgent().equals(agent)){
            tabFolder.getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    currentThread.getHostAgent().unRegisterDebuggerHostAgentListener(ModelInstanceViewer.this);
                    for (CTabItem item : tabFolder.getItems()) {
                        item.getControl().dispose();
                        item.dispose();
                    }
                }
            });
        }
        
    }

    @Override
    public void setFocus() {
        tabFolder.setFocus();
    }

    @Override
    public void dispose() {
        selectionProviderWrapper.dispose();
        super.dispose();
    }
    
    /**
     * Based on org.eclipse.debug.internal.ui.views.variables.VariablesView
     * @author Peter Lunk
     *
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static class TabbedSelectionProviderWrapper implements ISelectionProvider {
        private final ListenerList fListenerList;
        
        private final ISelectionChangedListener fListener;
        private ISelectionProvider fActiveProvider;
        
        private TabbedSelectionProviderWrapper() {
            fListenerList = new ListenerList(ListenerList.IDENTITY);
            fListener = new ISelectionChangedListener() {
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    fireSelectionChanged(event);
                }
            };
        }

        public void setActiveProvider(ISelectionProvider provider) {
            if (fActiveProvider != null && (fActiveProvider.equals(provider)  || this.equals(provider))) {
                return;
            }
            if (fActiveProvider != null) {
                fActiveProvider.removeSelectionChangedListener(fListener);
            }
            if (provider != null) {
                provider.addSelectionChangedListener(fListener);
            }
            fActiveProvider = provider;
            fireSelectionChanged(new SelectionChangedEvent(this, getSelection()));
        }

        private void fireSelectionChanged(SelectionChangedEvent event) {
            Object[] listeners = fListenerList.getListeners();
            for (int i = 0; i < listeners.length; i++) {
                ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
                listener.selectionChanged(event);
            }
        }
        
        private void dispose() {
            fListenerList.clear();
            setActiveProvider(null);
        }

        @Override
        public void addSelectionChangedListener(ISelectionChangedListener listener) {
            fListenerList.add(listener);            
        }

        @Override
        public ISelection getSelection() {
            if (fActiveProvider != null) {
                return fActiveProvider.getSelection();
            }
            return StructuredSelection.EMPTY;
        }

        @Override
        public void removeSelectionChangedListener(ISelectionChangedListener listener) {
            fListenerList.remove(listener);
        }

        @Override
        public void setSelection(ISelection selection) {
            if (fActiveProvider != null) {
                fActiveProvider.setSelection(selection);
            }
        }
    }
    
    
    
}
