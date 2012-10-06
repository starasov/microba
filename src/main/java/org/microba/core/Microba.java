package org.microba.core;

import org.microba.core.binding.Binder;
import org.microba.core.binding.Binding;
import org.microba.core.binding.BindingContext;
import org.microba.core.injection.*;
import org.microba.core.provider.ProviderMapper;
import org.microba.core.scope.DefaultScopeContext;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * @author starasov
 */
public class Microba {

    public static Binder createBinder() {
        return new Binder(new BindingContext());
    }

    public static InjectionContext createContext(Binder binder) {
        BindingContext bindingContext = binder.getBindingContext();

        DefaultInjectionContext injectionContext = new DefaultInjectionContext();
        DefaultScopeContext scopeContext = new DefaultScopeContext();
        
        ProviderMapper providerMapper = new ProviderMapper(injectionContext, scopeContext);
        for (Binding<?> binding : bindingContext.getBindings()) {
            Provider<?> provider = providerMapper.map(binding);
            Key<?> key = Key.forClassAndQualifier(binding.getBindee(), binding.getQualifier());

            //noinspection unchecked
            injectionContext.addProvider(key, (Provider) provider);
        }

        scopeContext.activate(Singleton.class);

        return injectionContext;
    }

    public static FieldInjector createFieldInjector(InjectionContext context) {
        return new DefaultFieldInjector(context);
    }
}
