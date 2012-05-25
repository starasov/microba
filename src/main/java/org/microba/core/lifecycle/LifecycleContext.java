package org.microba.core.lifecycle;

/**
 * @author starasov
 */
public interface LifecycleContext {
    void addListener(LifecycleListener listener);
    void notifyOnInstanceCreated(Object instance);
}
