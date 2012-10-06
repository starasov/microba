package org.microba.core.binding;

import javax.inject.Provider;
import java.lang.annotation.Annotation;

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

    public TargetBinder<T> qualifiedWith(Class<? extends Annotation> qualifier) {
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
