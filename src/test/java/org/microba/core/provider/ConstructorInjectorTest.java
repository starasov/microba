package org.microba.core.provider;

import org.junit.Before;
import org.junit.Test;
import org.microba.core.injection.DefaultInjectionContext;
import org.microba.core.injection.Key;

import java.lang.reflect.Constructor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.microba.core.provider.ClassFixtures.*;

/**
 * @author starasov
 */
public class ConstructorInjectorTest {

    private DefaultInjectionContext injectionContext;

    @Before
    public void setUp() throws Exception {
        injectionContext = new DefaultInjectionContext();
    }

    @Test
    public void shouldCorrectlyInstantiateClassWithNoArgumentsConstructor() {
        Constructor<DefaultConstructor> constructor = findConstructor(DefaultConstructor.class);
        assertTrue(injector(DefaultConstructor.class).newInstance(constructor) instanceof DefaultConstructor);
    }

    @Test
    public void shouldCorrectlyInstantiateClassWithPrimitiveTypeArgument() {
        injectionContext.addProvider(Key.forClass(Integer.class), new InstanceProvider<Integer>(1));

        Constructor<ParametrizedConstructorWithAnnotation> constructor = findConstructor(ParametrizedConstructorWithAnnotation.class);
        ParametrizedConstructorWithAnnotation instance = injector(ParametrizedConstructorWithAnnotation.class).newInstance(constructor);

        assertThat(1, is(instance.i));
    }

    @Test
    public void shouldCorrectlyInstantiateClassWithUserTypeArgument() {
        DefaultConstructor defaultConstructor = new DefaultConstructor();
        injectionContext.addProvider(Key.forClass(DefaultConstructor.class), new InstanceProvider<DefaultConstructor>(defaultConstructor));

        Constructor<UserTypeInjection> constructor = findConstructor(UserTypeInjection.class);
        UserTypeInjection instance = injector(UserTypeInjection.class).newInstance(constructor);

        assertThat(defaultConstructor, is(instance.i));
    }

    @Test
    public void shouldCorrectlyInstantiateClassWithQualifier() {
        injectionContext.addProvider(Key.forClassAndQualifier(Integer.class, TestQualifier.class), new InstanceProvider<Integer>(1));

        Constructor<ParametrizedConstructorWithQualifier> constructor = findConstructor(ParametrizedConstructorWithQualifier.class);
        ParametrizedConstructorWithQualifier instance = injector(ParametrizedConstructorWithQualifier.class).newInstance(constructor);

        assertThat(1, is(instance.i));
    }

    @Test
    public void shouldCorrectlyInstantiateClassWithMultipleConstructorParameters() {
        injectionContext.addProvider(Key.forClass(Integer.class), new InstanceProvider<Integer>(1));
        injectionContext.addProvider(Key.forClass(String.class), new InstanceProvider<String>("s"));

        Constructor<ConstructorWithMultipleParameters> constructor = findConstructor(ConstructorWithMultipleParameters.class);
        injector(ConstructorWithMultipleParameters.class).newInstance(constructor);
    }

    private <T> ConstructorInjector<T> injector(Class<T> _) {
        return new ConstructorInjector<T>(injectionContext);
    }

    private static <T> Constructor<T> findConstructor(Class<T> targetClass) {
        ConstructorFinder finder = new ConstructorFinder();
        return finder.findConstructor(targetClass);
    }
}
