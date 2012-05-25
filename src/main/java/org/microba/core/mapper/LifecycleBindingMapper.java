package org.microba.core.mapper;

import org.microba.core.binding.Binding;
import org.microba.core.lifecycle.LifecycleContext;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class LifecycleBindingMapper implements BindingMapper {
    private final BindingMapper bindingMapper;
    private final LifecycleContext contextControl;

    public LifecycleBindingMapper(BindingMapper bindingMapper, LifecycleContext contextControl) {
        this.bindingMapper = bindingMapper;
        this.contextControl = contextControl;
    }

    @Override
    public <T> Provider<T> map(Binding<T> configuration) {
        final Provider<T> provider = bindingMapper.map(configuration);
        if (configuration.getInstance() == null) {
            return new Provider<T>() {
                @Override
                public T get() {
                    T t = provider.get();
                    contextControl.notifyOnInstanceCreated(t);
                    return t;
                }
            };
        } else {
            return provider;
        }
    }
}
