package org.microba.core.provider;

import org.junit.Before;
import org.junit.Test;
import org.microba.core.MicrobaConfigurationException;

import static org.junit.Assert.assertNotNull;
import static org.microba.core.provider.ClassFixtures.*;

/**
 * @author starasov
 */
public class ConstructorFinderTest {
    private ConstructorFinder finder;

    @Before
    public void setUp() throws Exception {
        finder = new ConstructorFinder();
    }

    @Test
    public void shouldCorrectlyFindDefaultConstructor() {
        assertNotNull(finder.findConstructor(DefaultConstructor.class));
    }

    @Test
    public void shouldCorrectlyFindEmptyConstructor() {
        assertNotNull(finder.findConstructor(ExplicitEmptyConstructor.class));
    }

    @Test
    public void shouldCorrectlyFindEmptyConstructorForMultiConstructorClass() {
        assertNotNull(finder.findConstructor(EmptyAndParametrizedConstructor.class));
    }

    @Test
    public void shouldCorrectlyFindAnnotatedWithInjectConstructor() {
        assertNotNull(finder.findConstructor(ParametrizedConstructorWithAnnotation.class));
    }

    @Test(expected = MicrobaConfigurationException.class)
    public void shouldFailWhenNoDefaultConstructorOrNoInjectMarkedConstructorFound() {
        finder.findConstructor(ParametrizedConstructorWithoutAnnotation.class);
    }

}
