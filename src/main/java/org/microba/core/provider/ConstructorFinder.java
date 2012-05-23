package org.microba.core.provider;

import org.microba.core.MicrobaConfigurationException;

import javax.inject.Inject;
import java.lang.reflect.Constructor;

/**
 * @author starasov
 */
class ConstructorFinder {
    public <T> Constructor<T> findConstructor(Class<T> targetClass) {
        try {
            Constructor[] constructors = targetClass.getDeclaredConstructors();
            for (Constructor<T> constructor : constructors) {
                if (constructor.getAnnotation(Inject.class) != null) {
                    return constructor;
                }
            }

            return targetClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new MicrobaConfigurationException("Can't find annotated with @Inject constructor for " + targetClass.getSimpleName(), e);
        }
    }
}
