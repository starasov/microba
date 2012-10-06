package org.microba.core.injection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * User: raven
 * Date: 05.10.12
 */

class FieldInjector {

    private static final String UNKNOWN_FIELD_CLASS =
            "Cannot find provider for field [%s] of type [%s] in class [%s]";

    private static final String UNASSIGNABLE_FIELD_TEXT =
            "Field [%s] cannot be assigned";

    private final InjectionContext context;

    public FieldInjector(InjectionContext context) {
        this.context = context;
    }

    public void injectMembers(Object target) {
        List<Field> injectableFields = findInjectableFields(target);

        for (Field field: injectableFields) {
            boolean accessible = field.isAccessible();
            Annotation qualifier = getFieldQualifier(field);
            if (!accessible) {
                field.setAccessible(true);
            }
            Key key = qualifier == null ? Key.forClass(field.getType()) :
                    Key.forClassAndQualifier(field.getType(), qualifier.annotationType());
            Provider<?> provider = context.getProvider(key);
            if (provider == null) {
                throw createUnknownFieldClassException(field);
            }
            try {
                field.set(target, provider.get());
                if (!accessible) {
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                throw createUnassignableFieldException(field, e);
            }
        }
    }

    private Annotation getFieldQualifier(Field field) {
        for (Annotation annotation: field.getDeclaredAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                return annotation;
            }
        }
        return null;
    }

    private RuntimeException createUnassignableFieldException(Field field, IllegalAccessException e) {
        return new IllegalArgumentException(String.format(UNASSIGNABLE_FIELD_TEXT, field.getName()), e);
    }

    private RuntimeException createUnknownFieldClassException(Field field) {
        return new IllegalArgumentException(String.format(UNKNOWN_FIELD_CLASS,
                field.getName(), field.getType().toString(), field.getDeclaringClass().getCanonicalName()));
    }

    private List<Field> findInjectableFields(Object target) {
        Field[] allFields = target.getClass().getDeclaredFields();
        List<Field> result = new ArrayList<Field>();

        for (Field field: allFields) {
            if (field.getAnnotation(Inject.class) != null) {
                result.add(field);
            }
        }
        return result;
    }
}
