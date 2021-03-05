package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class StartMenu implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        return "Enter:" + "\n" + "'Crew' - to get menu for Crew" + "\n" + "'Spaceship' - to get menu for SpaceShip"
                + "\n" + "'Mission' - to get menu for Mission";
    }
}
