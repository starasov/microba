package org.microba.core.scope;

/**
 * @author starasov
 */
public interface ScopeLifecycleListener {
    void onActivate();
    void onDeactivate();
}
