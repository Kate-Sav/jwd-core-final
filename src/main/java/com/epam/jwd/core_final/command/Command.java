package com.epam.jwd.core_final.command;

import com.epam.jwd.core_final.context.impl.NassaContext;

public interface Command {
    String execute(String request, NassaContext nassaContext);
}
