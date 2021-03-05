package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class SpaceShip implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String response = "Press:" + "\n" + "'1' - to get information about all spaceships" + "\n" +
                "'2;name' - to get information about concrete Spaceship"+"\n" +
                "'3;name;flight distance;number of each crew member role' - to create a Spaceship, " +
                "where 'number of each crew member role' enter as:'Mission_Specialist:2,Pilot:3 and so on'" + "\n" +
                "'4;name;flight distance;number of each crew member role' - to update a Spaceship, " +
                "where 'number of each crew member role' enter as:'Mission_Specialist:2,Pilot:3 and so on'";
        return response;
    }
}
