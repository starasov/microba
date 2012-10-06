package org.microba.core.lifecycle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author starasov
 */
public class DefaultLifecycleContext implements LifecycleContext {
    private final List<LifecycleListener> lifecycleListeners = new LinkedList<LifecycleListener>();

    @Override
    public void addListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
    }

    @Override
    public void notifyOnInstanceCreated(Object instance) {
        for (LifecycleListener lifecycleListener : lifecycleListeners) {
            lifecycleListener.onInstanceCreated(instance);
        }
    }
}
