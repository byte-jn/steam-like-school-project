package org.example.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.di.AppModule;

public final class AppInjector {

    private static final Injector INJECTOR = Guice.createInjector(new AppModule());

    private AppInjector() { }

    public static Injector get() {
        return INJECTOR;
    }
}
