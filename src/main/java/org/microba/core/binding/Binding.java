package org.microba.core.binding;

import javax.inject.Provider;
import java.lang.annotation.Annotation;

/**
 * @author starasov
 */
public class Binding<T> {
    private Class<T> bindee;

    private Class<? extends T> target;
    private Class<? extends Provider<T>> targetProvider;

    private T instance;

    private Class<? extends Annotation> scope;
    private Class<? extends Annotation> qualifier;

    public Class<T> getBindee() {
        return bindee;
    }

    public void setBindee(Class<T> bindee) {
        this.bindee = bindee;
    }

    public Class<? extends T> getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void setTarget(Class<? extends T> target) {
        this.target = target;
    }

    public Class<? extends Provider<T>> getProvider() {
        return targetProvider;
    }

    public boolean hasProvider() {
        return targetProvider != null;
    }

    public void setTargetProvider(Class<? extends Provider<T>> targetProvider) {
        this.targetProvider = targetProvider;
    }

    public T getInstance() {
        return instance;
    }

    public boolean hasInstance() {
        return instance != null;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Class<? extends Annotation> getScope() {
        return scope;
    }

    public void setScope(Class<? extends Annotation> scope) {
        this.scope = scope;
    }

    public Class<? extends Annotation> getQualifier() {
        return qualifier;
    }

    public void setQualifier(Class<? extends Annotation> qualifier) {
        this.qualifier = qualifier;
    }
}
