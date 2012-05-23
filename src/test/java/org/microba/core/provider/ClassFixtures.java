package org.microba.core.provider;

import javax.inject.Inject;

/**
 * @author starasov
 */
public class ClassFixtures {
    static class DefaultConstructor {
    }

    static class EmptyAndParametrizedConstructor {
        EmptyAndParametrizedConstructor() {
        }

        EmptyAndParametrizedConstructor(int i) {
        }
    }

    static class ExplicitEmptyConstructor {
        ExplicitEmptyConstructor() {
        }
    }

    static class ParametrizedConstructorWithoutAnnotation {
        ParametrizedConstructorWithoutAnnotation(int i) {
        }
    }

    static class ParametrizedConstructorWithAnnotation {
        final int i;

        @Inject
        ParametrizedConstructorWithAnnotation(int i) {
            this.i = i;
        }
    }

    static class ParametrizedConstructorWithQualifier {
        final int i;

        @Inject
        ParametrizedConstructorWithQualifier(@TestQualifier int i) {
            this.i = i;
        }
    }

    static class UserTypeInjection {
        final DefaultConstructor i;

        @Inject
        UserTypeInjection(DefaultConstructor i) {
            this.i = i;
        }
    }

    static class ConstructorWithMultipleParameters {
        final int i;
        final String s;

        @Inject
        ConstructorWithMultipleParameters(int i, String s) {
            this.i = i;
            this.s = s;
        }
    }

}
