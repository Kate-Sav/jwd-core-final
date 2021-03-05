package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class CloseMenu implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        return "Enter:" + "\n" + "'c' - to continue" + "\n" + "'e' - to exit";
    }
}
