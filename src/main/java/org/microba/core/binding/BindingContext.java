package org.microba.core.binding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starasov
 */
public class BindingContext {
    private final List<Binding<?>> configurations = new ArrayList<Binding<?>>();

    public List<Binding<?>> getBindings() {
        return configurations;
    }

    void addConfiguration(Binding<?> configuration) {
        configurations.add(configuration);
    }
}
