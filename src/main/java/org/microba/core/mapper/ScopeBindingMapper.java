package org.microba.core.mapper;

import org.microba.core.binding.Binding;
import org.microba.core.provider.ScopedProvider;
import org.microba.core.scope.ScopeContext;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class ScopeBindingMapper implements BindingMapper {
    private final BindingMapper bindingMapper;
    private final ScopeContext scopeContext;

    public ScopeBindingMapper(BindingMapper bindingMapper, ScopeContext scopeContext) {
        this.bindingMapper = bindingMapper;
        this.scopeContext = scopeContext;
    }

    @Override
    public <T> Provider<T> map(Binding<T> configuration) {
        Provider<T> provider = bindingMapper.map(configuration);

        if (configuration.getScope() != null) {
            ScopedProvider<T> scopedProvider = new ScopedProvider<T>(provider);
            scopeContext.addListener(configuration.getScope(), scopedProvider);
            return scopedProvider;
        } else {
            return provider;
        }
    }
}
