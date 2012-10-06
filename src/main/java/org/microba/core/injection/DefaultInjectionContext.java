package org.microba.core.injection;

import org.microba.core.MicrobaConfigurationException;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author starasov
 */
public class DefaultInjectionContext implements InjectionContext, InjectionContextControl {
    private final Map<Key<?>, Provider<?>> providers = new HashMap<Key<?>, Provider<?>>();
    private final List<Key<?>> lookupQueue = new LinkedList<Key<?>>();

    @Override
    public <T> T getInstance(Class<T> key) {
        return getInstance(Key.forClass(key));
    }

    @Override
    public synchronized <T> T getInstance(Key<T> key) {
        if (lookupQueue.contains(key)) {
            throw new IllegalStateException("Possible cyclic dependency found: " + lookupQueue);
        }

        lookupQueue.add(key);

        Provider<T> provider = getProvider(key);
        if (provider == null) {
            throw new MicrobaConfigurationException("Could not resolve " + key + " to instance.");
        }

        T t = provider.get();

        lookupQueue.remove(key);

        return t;
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> key) {
        return getProvider(Key.forClass(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Provider<T> getProvider(Key<T> key) {
        return (Provider<T>) providers.get(key);
    }

    @Override
    public <T> boolean hasProviderFor(Class<T> key) {
        return providers.containsKey(Key.forClass(key));
    }

    @Override
    public <T> void addProvider(Key<T> to, Provider<T> provider) {
        providers.put(to, provider);
    }
}
