package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;

public class Crew implements Command {

    @Override
    public String execute(String request, NassaContext nassaContext) {
        String response = "Press:" + "\n" + "'1' - to get information about all crew members" + "\n" +
                "'2;name' - to get information about concrete CrewMember"+"\n" +
                "'3;role;name;rank' - to create a CrewMember" + "\n" +
                "'4;role;name;rank' - to update a CrewMember";

        return response;
    }
}
