package org.microba.core.provider;

import org.microba.core.injection.InjectionContext;

import javax.inject.Provider;
import java.lang.reflect.Constructor;

/**
 * @author starasov
 */
public class ClassProvider<T> implements Provider<T> {
    private final Class<? extends T> target;
    private final ConstructorFinder constructorFinder;
    private final ConstructorInjector<T> constructorInjector;

    ClassProvider(ConstructorFinder constructorFinder, ConstructorInjector<T> constructorInjector, Class<? extends T> target) {
        this.constructorFinder = constructorFinder;
        this.constructorInjector = constructorInjector;
        this.target = target;
    }

    public static <T> ClassProvider<T> forClass(InjectionContext injectionContext, Class<? extends T> target) {
        return new ClassProvider<T>(new ConstructorFinder(), new ConstructorInjector<T>(injectionContext), target);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        Constructor<T> constructor = constructorFinder.findConstructor((Class<T>) target);
        if (constructor != null) {
            constructor.setAccessible(true);
        }

        return constructorInjector.newInstance(constructor);
    }
}
