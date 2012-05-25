package org.microba.core.injection;

import javax.inject.Provider;

/**
 * @author starasov
 */
public interface InjectionContextControl {
    <T> void addProvider(Key<T> to, Provider<T> provider);
}
