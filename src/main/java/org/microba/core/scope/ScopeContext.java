package org.microba.core.scope;

import java.lang.annotation.Annotation;

/**
 * @author starasov
 */
public interface ScopeContext {
    void addListener(Class<? extends Annotation> scope, ScopeLifecycleListener listener);
    void activate(Class<? extends Annotation> scope);
    void deactivate(Class<? extends Annotation> scope);
}
