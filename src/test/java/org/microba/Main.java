package org.microba;

import org.microba.core.Microba;
import org.microba.core.MicrobaContext;
import org.microba.core.binding.Binder;
import org.microba.core.injection.InjectionContext;
import org.microba.core.lifecycle.LifecycleListener;

import javax.inject.Provider;
import javax.inject.Singleton;

interface Test {
    void a();
}

class TestImpl implements Test {

    TestImpl() {
        System.out.println("c!");
    }

    @Override
    public void a() {
        System.out.println("a!");
    }
}

/**
 * @author starasov
 */
public class Main {

    public static void main(String[] args) {
        miniTest();
    }

    private static void miniTest() {
        Binder binder = Microba.createBinder();

        binder.bind(TestImpl.class).in(Singleton.class);
        binder.bind(Test.class).to(TestImpl.class);

        binder.bind(String.class).toInstance("Test");

        MicrobaContext microbaContext = Microba.createContext(binder);
        microbaContext.getLifecycleContext().addListener(new LifecycleListener() {
            @Override
            public void onInstanceCreated(Object instance) {
                System.out.println("instance = " + instance);
            }
        });

        microbaContext.getScopeContext().activate(Singleton.class);

        {
            Provider<String> provider = microbaContext.getInjectionContext().getProvider(String.class);
            System.out.println("provider = " + provider);

            String test = microbaContext.getInjectionContext().getInstance(String.class);
            String test1 = microbaContext.getInjectionContext().getInstance(String.class);
            String test2 = microbaContext.getInjectionContext().getInstance(String.class);
            System.out.println("test = " + test);
        }

        {
            TestImpl testImpl = microbaContext.getInjectionContext().getInstance(TestImpl.class);
            Test test = microbaContext.getInjectionContext().getInstance(Test.class);

            System.out.println(testImpl);
            System.out.println(test);

        }
    }
}
