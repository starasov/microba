package org.microba.core.injection;

import javax.inject.Provider;

/**
 * @author starasov
 */
public interface InjectionContext {
    <T> T getInstance(Class<T> key);
    <T> T getInstance(Key<T> key);

    <T> Provider<T> getProvider(Class<T> key);
    <T> Provider<T> getProvider(Key<T> key);

    <T> boolean hasProviderFor(Class<T> key);
}