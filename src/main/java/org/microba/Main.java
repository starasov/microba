package org.microba;

import org.microba.core.Microba;
import org.microba.core.binding.Binder;
import org.microba.core.injection.InjectionContext;

import javax.inject.Provider;


/**
 * @author starasov
 */
public class Main {

    public static void main(String[] args) {
        miniTest();
    }

    private static void miniTest() {
        Binder binder = Microba.createBinder();
        binder.bind(String.class).toInstance("Test");

        InjectionContext injectionContext = Microba.createContext(binder);
        {
            Provider<String> provider = injectionContext.getProvider(String.class);
            System.out.println("provider = " + provider);
        }
    }
}
