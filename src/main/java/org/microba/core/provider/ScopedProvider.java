package org.microba.core.provider;

import org.microba.core.scope.ScopeLifecycleListener;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class ScopedProvider<T> implements Provider<T>, ScopeLifecycleListener {
    private final Provider<T> wrapee;
    private T instance;

    public ScopedProvider(Provider<T> wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public synchronized T get() {
        if (instance == null) {
            instance = wrapee.get();
        }

        return instance;
    }

    @Override
    public synchronized void onActivate() {
        instance = wrapee.get();
    }

    @Override
    public synchronized void onDeactivate() {
        instance = null;
    }
}
