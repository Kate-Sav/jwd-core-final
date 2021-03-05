package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.UnknownEntityException;

public class MenuWork implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
        if(splitRequest[1].equals("c"))
            return "c";
        else if(splitRequest[1].equals("e"))
            return "e";

        throw new UnknownEntityException("Wrong request!");
    }
}
