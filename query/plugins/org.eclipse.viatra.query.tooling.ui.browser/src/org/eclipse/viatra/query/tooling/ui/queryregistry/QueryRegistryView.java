/*******************************************************************************
 * Copyright (c) 2010-2016, Abel Hegedus, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Abel Hegedus - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.tooling.ui.queryregistry;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.handlers.CollapseAllHandler;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.viatra.query.patternlanguage.emf.ui.internal.EMFPatternLanguageActivator;
import org.eclipse.viatra.query.runtime.registry.QuerySpecificationRegistry;
import org.eclipse.viatra.query.tooling.ui.queryregistry.index.XtextIndexBasedRegistryUpdater;

import com.google.inject.Injector;

/**
 * @author Abel Hegedus
 *
 */
public class QueryRegistryView extends ViewPart implements ITabbedPropertySheetPageContributor {

    public static final String ID = "org.eclipse.viatra.query.tooling.ui.queryregistry.QueryRegistryView"; //$NON-NLS-1$
    private TreeViewer queryTreeViewer;
    private QueryRegistryTreeInput queryRegistryTreeInput;
    private XtextIndexBasedRegistryUpdater updater;
    private CollapseAllHandler collapseHandler;

    public QueryRegistryView() {
        Injector injector = EMFPatternLanguageActivator.getInstance().getInjector(EMFPatternLanguageActivator.ORG_ECLIPSE_VIATRA_QUERY_PATTERNLANGUAGE_EMF_EMFPATTERNLANGUAGE);
        updater = injector.getInstance(XtextIndexBasedRegistryUpdater.class);
        updater.connectIndexToRegistry(QuerySpecificationRegistry.getInstance());

        queryRegistryTreeInput = new QueryRegistryTreeInput(QuerySpecificationRegistry.getInstance());
    }
    
    @Override
    public void dispose() {
        updater.disconnectIndexFromRegistry();
        collapseHandler.dispose();
        super.dispose();
    }

    /**
     * Create contents of the view part.
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout(SWT.HORIZONTAL));
        Composite queryRegistryContainer = new Composite(parent, SWT.NONE);
        queryRegistryContainer.setLayout(new GridLayout(1, false));
        
        initializeQueryTreeViewer(queryRegistryContainer);
    }

    private void initializeQueryTreeViewer(Composite queryRegistryContainer) {
        PatternFilter patternFilter = new PatternFilter();
        patternFilter.setIncludeLeadingWildcard(true);
        FilteredTree filteredTree = new FilteredTree(queryRegistryContainer, SWT.BORDER | SWT.MULTI, patternFilter, true);
        queryTreeViewer = filteredTree.getViewer();
        filteredTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        queryTreeViewer.setComparator(new ViewerComparator() {

            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {
                if (e1 instanceof QueryRegistryTreeSource && e2 instanceof QueryRegistryTreeSource) {
                    QueryRegistryTreeSource source1 = (QueryRegistryTreeSource) e1;
                    QueryRegistryTreeSource source2 = (QueryRegistryTreeSource) e2;
                    return source1.getSourceIdentifier().compareTo(source2.getSourceIdentifier());
                }
                return super.compare(viewer, e1, e2);
            }
            
        });
        queryTreeViewer.setLabelProvider(new QueryRegistryTreeLabelProvider());
        queryTreeViewer.setContentProvider(new QueryRegistryTreeContentProvider());
        getSite().setSelectionProvider(queryTreeViewer);
        
        queryTreeViewer.setInput(queryRegistryTreeInput);
        
        IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
        collapseHandler = new CollapseAllHandler(queryTreeViewer);
        handlerService.activateHandler(CollapseAllHandler.COMMAND_ID, collapseHandler);
        
        // Create pop-up menu over the tree viewer
        MenuManager menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager mgr) {
                mgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
            }
        });
        Control control = queryTreeViewer.getControl();
        control.setMenu(menuManager.createContextMenu(control));
        getSite().registerContextMenu(ID,menuManager, queryTreeViewer);
    }

    @Override
    public void setFocus() {
        // Set the focus
        queryTreeViewer.getControl().setFocus();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            return adapter.cast(new TabbedPropertySheetPage(this));
        }
        return super.getAdapter(adapter);
    }

    @Override
    public String getContributorId() {
        return getSite().getId();
    }

    public void resetView() {
        updater.disconnectIndexFromRegistry();
        updater.connectIndexToRegistry(QuerySpecificationRegistry.getInstance());
    }
    
    public void collapseAll() {
        queryTreeViewer.collapseAll();
    }
}