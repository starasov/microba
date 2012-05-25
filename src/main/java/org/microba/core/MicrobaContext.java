package org.microba.core;

import org.microba.core.injection.InjectionContext;
import org.microba.core.lifecycle.LifecycleContext;
import org.microba.core.scope.ScopeContext;

/**
 * @author starasov
 */
public class MicrobaContext {
    private final InjectionContext injectionContext;
    private final ScopeContext scopeContext;
    private final LifecycleContext lifecycleContext;

    public MicrobaContext(InjectionContext injectionContext, ScopeContext scopeContext, LifecycleContext lifecycleContext) {
        this.injectionContext = injectionContext;
        this.scopeContext = scopeContext;
        this.lifecycleContext = lifecycleContext;
    }

    public InjectionContext getInjectionContext() {
        return injectionContext;
    }

    public ScopeContext getScopeContext() {
        return scopeContext;
    }

    public LifecycleContext getLifecycleContext() {
        return lifecycleContext;
    }
}
