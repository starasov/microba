package org.microba.core.provider;

import org.microba.core.MicrobaConfigurationException;
import org.microba.core.injection.InjectionContext;
import org.microba.core.injection.Key;

import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author starasov
 */
class ConstructorInjector<T> {
    private final static Map<Class<?>, Class<?>> PRIMITIVE_TRANSFORMATIONS = new HashMap<Class<?>, Class<?>>();
    static {
        PRIMITIVE_TRANSFORMATIONS.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_TRANSFORMATIONS.put(Byte.TYPE, Byte.class);
        PRIMITIVE_TRANSFORMATIONS.put(Short.TYPE, Short.class);
        PRIMITIVE_TRANSFORMATIONS.put(Character.TYPE, Character.class);
        PRIMITIVE_TRANSFORMATIONS.put(Integer.TYPE, Integer.class);
        PRIMITIVE_TRANSFORMATIONS.put(Long.TYPE, Long.class);
        PRIMITIVE_TRANSFORMATIONS.put(Float.TYPE, Float.class);
        PRIMITIVE_TRANSFORMATIONS.put(Double.TYPE, Double.class);
    }

    private final InjectionContext injectionContext;

    public ConstructorInjector(InjectionContext injectionContext) {
        this.injectionContext = injectionContext;
    }

    public T newInstance(Constructor<T> constructor) {
        try {
            Object[] parameters = lookupConstructorParameters(constructor);
            return constructor.newInstance(parameters);
        } catch (InstantiationException e) {
            throw newConfigurationException(e, constructor);
        } catch (IllegalAccessException e) {
            throw newConfigurationException(e, constructor);
        } catch (InvocationTargetException e) {
            throw newConfigurationException(e, constructor);
        } catch (MicrobaConfigurationException e) {
            throw newConfigurationException(e, constructor);
        }
    }

    private static MicrobaConfigurationException newConfigurationException(Throwable cause, Constructor<?> constructor) {
        String simpleName = constructor.getDeclaringClass().getSimpleName();
        String constructorName = constructor.toGenericString();
        throw new MicrobaConfigurationException("Unable to instantiate '" + simpleName + "' using " + constructorName, cause);
    }

    private Object[] lookupConstructorParameters(Constructor<T> constructor) {
        Class<?>[] parameterTypes = transformPrimitiveParametersIntoBoxed(constructor.getParameterTypes());
        Annotation[][] declaredAnnotations = constructor.getParameterAnnotations();

        List<Object> parameters = new ArrayList<Object>();
        for (int i = 0; i < parameterTypes.length; i++) {
            Object instance = lookupInstanceFor(parameterTypes[i], declaredAnnotations[i]);
            parameters.add(instance);
        }

        return parameters.toArray(new Object[0]);
    }

    private Object lookupInstanceFor(Class<?> parameterType, Annotation[] declaredAnnotation) {
        Class<? extends Annotation> qualifierClass = lookupQualifier(declaredAnnotation);
        Key<?> key = Key.forClassAndQualifier(parameterType, qualifierClass);
        return injectionContext.getInstance(key);
    }

    private Class<? extends Annotation> lookupQualifier(Annotation[] declaredAnnotation) {
        if (declaredAnnotation.length == 0) {
            return null;
        }

        Annotation annotation = declaredAnnotation[0];
        Annotation [] declaredAnnotations = annotation.annotationType().getDeclaredAnnotations();
        for (Annotation a : declaredAnnotations) {
            if (a.annotationType().equals(Qualifier.class)) {
                return annotation.annotationType();
            }
        }

        return null;
    }

    private Class<?>[] transformPrimitiveParametersIntoBoxed(Class<?>[] parameterTypes) {
        Class<?>[] transformedTypes = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (PRIMITIVE_TRANSFORMATIONS.containsKey(parameterType)) {
                transformedTypes[i] = PRIMITIVE_TRANSFORMATIONS.get(parameterType);
            } else {
                transformedTypes[i] = parameterType;
            }
        }

        return transformedTypes;
    }
}
