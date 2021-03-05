package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.UnknownEntityException;

public class WrongRequest implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        throw new UnknownEntityException("Wrong request!");
    }
}
