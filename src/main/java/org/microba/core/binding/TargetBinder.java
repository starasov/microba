package org.microba.core.binding;

import javax.inject.Provider;
import javax.inject.Qualifier;

/**
* @author starasov
*/
public class TargetBinder<T> extends ScopeBinder<T> {

    TargetBinder(Binding<T> binding) {
        super(binding);
    }

    public ScopeBinder to(Class<? extends T> target) {
        getBinding().setTarget(target);
        return new ScopeBinder<T>(getBinding());
    }

    public TargetBinder<T> qualifiedWith(Class<? extends Qualifier> qualifier) {
        getBinding().setQualifier(qualifier);
        return this;
    }

    public ScopeBinder toProvider(Class<Provider<T>> provider) {
        getBinding().setTargetProvider(provider);
        return new ScopeBinder<T>(getBinding());
    }

    public void toInstance(T instance) {
        getBinding().setInstance(instance);
    }
}
