package org.microba.core.provider;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class InstanceProvider<T> implements Provider<T> {
    private final T instance;

    public InstanceProvider(T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return instance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("InstanceProvider");
        sb.append("{instance=").append(instance);
        sb.append('}');
        return sb.toString();
    }
}
