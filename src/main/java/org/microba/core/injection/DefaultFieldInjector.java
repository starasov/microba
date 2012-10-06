package org.microba.core.injection;

import javax.inject.Inject;
import javax.inject.Provider;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * User: raven
 * Date: 05.10.12
 */

public class DefaultFieldInjector implements FieldInjector {

    private static final String UNKNOWN_FIELD_CLASS =
            "Cannot find provider for field %s of type %s";

    private static final String UNASSIGNABLE_FIELD_TEXT =
            "Field %s cannot be assigned";

    private final InjectionContext context;

    public DefaultFieldInjector(InjectionContext context) {
        this.context = context;
    }

    @Override
    public void injectMembers(Object target) {
        List<Field> injectableFields = findInjectableFields(target);

        for (Field field: injectableFields) {
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            Provider<?> provider = context.getProvider(field.getType());
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

    private RuntimeException createUnassignableFieldException(Field field, IllegalAccessException e) {
        return new IllegalArgumentException(String.format(UNASSIGNABLE_FIELD_TEXT, field.getName(),
                field.getType().toString()), e);
    }

    private RuntimeException createUnknownFieldClassException(Field field) {
        return new IllegalArgumentException(String.format(UNKNOWN_FIELD_CLASS, field.getName()));
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
