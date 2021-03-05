package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException, InterruptedException {
        final Supplier<ApplicationContext> applicationContextSupplier = () -> new NassaContext();// todo
        ApplicationMenu applicationMenu = applicationContextSupplier::get;
        final NassaContext nassaContext = new NassaContext();

        applicationMenu.printAvailableOptions();


        return applicationContextSupplier::get;
    }
}
