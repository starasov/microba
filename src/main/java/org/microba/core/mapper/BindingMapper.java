package org.microba.core.mapper;

import org.microba.core.binding.Binding;

import javax.inject.Provider;

/**
 * @author starasov
 */
public interface BindingMapper {
    <T> Provider<T> map(Binding<T> configuration);
}
