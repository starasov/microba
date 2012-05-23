package org.microba.core.binding;

import java.lang.annotation.Annotation;

/**
* @author starasov
*/
public class ScopeBinder<T> {
    private final Binding<T> binding;

    public ScopeBinder(Binding<T> binding) {
        this.binding = binding;
    }

    public void in(Class<? extends Annotation> scope) {
        binding.setScope(scope);
    }

    protected Binding<T> getBinding() {
        return binding;
    }
}
