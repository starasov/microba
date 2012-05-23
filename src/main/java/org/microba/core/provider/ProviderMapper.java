package org.microba.core.provider;

import org.microba.core.binding.Binding;
import org.microba.core.injection.InjectionContext;
import org.microba.core.scope.ScopeContext;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class ProviderMapper {
    private final InjectionContext injectionContext;
    private final ScopeContext scopeContext;

    public ProviderMapper(InjectionContext injectionContext, ScopeContext scopeContext) {
        this.injectionContext = injectionContext;
        this.scopeContext = scopeContext;
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

        if (configuration.getScope() != null) {
            ScopedProvider<T> scopedProvider = new ScopedProvider<T>(provider);
            scopeContext.addListener(configuration.getScope(), scopedProvider);
            provider = scopedProvider;
        }

        return provider;
    }
}
