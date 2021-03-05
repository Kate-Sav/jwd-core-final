package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class FlightMission implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String response = "Press:" + "\n" + "'1' - to get information about all flight missions" + "\n" +
                "'2;name' - to get information about concrete FlightMission"+"\n" +
                "'3;name;start date;departure planet;arrival planet' - to create a FlightMission, " +
                "where date enter as '01.01.20201 12:00:00'" + "\n" +
                "'4;name' - to add FlightMission information in json file";

        return response;
    }
}
