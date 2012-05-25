package org.microba.core.mapper;

import org.microba.core.binding.Binding;
import org.microba.core.injection.InjectionContext;
import org.microba.core.provider.ClassProvider;
import org.microba.core.provider.InstanceProvider;
import org.microba.core.provider.ProviderProvider;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class DefaultBindingMapper implements BindingMapper {
    private final InjectionContext injectionContext;

    public DefaultBindingMapper(InjectionContext injectionContext) {
        this.injectionContext = injectionContext;
    }

    public <T> Provider<T> map(Binding<T> configuration) {
        if (configuration.getInstance() != null) {
            return new InstanceProvider<T>(configuration.getInstance());
        }

        Provider<T> provider;

        if (configuration.getProvider() != null) {
            ClassProvider<Provider<T>> classProvider = ClassProvider.forClass(injectionContext, configuration.getProvider());
            provider = new ProviderProvider<T>(classProvider);
        } else if (configuration.getTarget() != null) {
            provider = ClassProvider.forClass(injectionContext, configuration.getTarget());
        } else {
            provider = ClassProvider.forClass(injectionContext, configuration.getBindee());
        }

        return provider;
    }
}
