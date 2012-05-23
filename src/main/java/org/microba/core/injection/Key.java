package org.microba.core.injection;

import java.lang.annotation.Annotation;

/**
 * @author starasov
 */
public class Key<T> {
    private final Class<T> target;
    private final Class<? extends Annotation> qualifier;

    Key(Class<T> target, Class<? extends Annotation> qualifier) {
        this.target = target;
        this.qualifier = qualifier;
    }

    public static <T> Key<T> forClass(Class<T> target) {
        return new Key<T>(target, null);
    }

    public static <T> Key<T> forClassAndQualifier(Class<T> target, Class<? extends Annotation> qualifier) {
        return new Key<T>(target, qualifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (qualifier != null ? !qualifier.equals(key.qualifier) : key.qualifier != null) return false;
        if (!target.equals(key.target)) return false;

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Key");
        sb.append("{target=").append(target);
        sb.append(", qualifier=").append(qualifier);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }
}
