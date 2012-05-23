package org.microba.core.provider;

import javax.inject.Provider;

/**
 * @author starasov
 */
public class ProviderProvider<T> implements Provider<T> {
    private final Provider<Provider<T>> providerProvider;

    public ProviderProvider(Provider<Provider<T>> providerProvider) {
        this.providerProvider = providerProvider;
    }

    @Override
    public T get() {
        Provider<T> provider = providerProvider.get();
        return provider.get();
    }
}
