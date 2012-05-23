package org.microba.core.binding;

/**
 * @author starasov
 */
public class Binder {

    private final BindingContext bindingContext;

    public Binder(BindingContext bindingContext) {
        this.bindingContext = bindingContext;
    }

    public BindingContext getBindingContext() {
        return bindingContext;
    }

    public <T> TargetBinder<T> bind(Class<T> bindee) {
        Binding<T> binding = new Binding<T>();
        bindingContext.addConfiguration(binding);
        binding.setBindee(bindee);
        return new TargetBinder<T>(binding);
    }
}
