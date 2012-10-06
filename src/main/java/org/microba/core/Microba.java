package org.microba.core;

import org.microba.core.binding.Binder;
import org.microba.core.binding.Binding;
import org.microba.core.binding.BindingContext;
import org.microba.core.injection.*;
import org.microba.core.lifecycle.DefaultLifecycleContext;
import org.microba.core.mapper.BindingMapper;
import org.microba.core.mapper.DefaultBindingMapper;
import org.microba.core.mapper.LifecycleBindingMapper;
import org.microba.core.mapper.ScopeBindingMapper;
import org.microba.core.scope.DefaultScopeContext;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class Microba {

    public static Binder createBinder() {
        return new Binder(new BindingContext());
    }

    public static MicrobaContext createContext(Binder binder) {
        MicrobaContext microbaContext = createDefaultContext();
        BindingMapper bindingMapper = createMapper(microbaContext);
        InjectionContextControl injectionContextControl = (InjectionContextControl) microbaContext.getInjectionContext();

        for (Binding<?> binding : binder.getBindingContext().getBindings()) {
            Provider<?> provider = bindingMapper.map(binding);
            Key<?> key = Key.forClassAndQualifier(binding.getBindee(), binding.getQualifier());

            //noinspection unchecked
            injectionContextControl.addProvider(key, (Provider) provider);
        }

        return microbaContext;
    }

    static MicrobaContext createDefaultContext() {
        DefaultInjectionContext injectionContext = new DefaultInjectionContext();
        DefaultScopeContext scopeContext = new DefaultScopeContext();
        DefaultLifecycleContext lifecycleContext = new DefaultLifecycleContext();

        return new MicrobaContext(injectionContext, scopeContext, lifecycleContext);
    }

    static BindingMapper createMapper(MicrobaContext microbaContext) {
        DefaultBindingMapper defaultBindingMapper = new DefaultBindingMapper(microbaContext.getInjectionContext());
        LifecycleBindingMapper lifecycleBindingMapper = new LifecycleBindingMapper(defaultBindingMapper, microbaContext.getLifecycleContext());
        return new ScopeBindingMapper(lifecycleBindingMapper, microbaContext.getScopeContext());
    }

    public static FieldInjector createFieldInjector(MicrobaContext context) {
        return new DefaultFieldInjector(context);
    }    
}
