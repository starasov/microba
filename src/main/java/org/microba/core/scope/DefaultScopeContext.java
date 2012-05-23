package org.microba.core.scope;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author starasov
 */
public class DefaultScopeContext implements ScopeContext {
    private final Map<Class<? extends Annotation>, List<ScopeLifecycleListener>> listenersMapping = new HashMap<Class<? extends Annotation>, List<ScopeLifecycleListener>>();

    @Override
    public void addListener(Class<? extends Annotation> scope, ScopeLifecycleListener listener) {
        List<ScopeLifecycleListener> listeners;
        if (listenersMapping.containsKey(scope)) {
            listeners = listenersMapping.get(scope);
        } else {
            listeners = new LinkedList<ScopeLifecycleListener>();
            listenersMapping.put(scope, listeners);
        }

        listeners.add(listener);
    }

    @Override
    public void activate(Class<? extends Annotation> scope) {
        if (listenersMapping.containsKey(scope)) {
            List<ScopeLifecycleListener> listeners = listenersMapping.get(scope);
            for (ScopeLifecycleListener listener : listeners) {
                listener.onActivate();
            }
        }
    }

    @Override
    public void deactivate(Class<? extends Annotation> scope) {
        if (listenersMapping.containsKey(scope)) {
            List<ScopeLifecycleListener> listeners = listenersMapping.get(scope);
            for (ScopeLifecycleListener listener : listeners) {
                listener.onDeactivate();
            }
        }
    }
}
