package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;

import java.util.Collection;

public class FlightMissionAll implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        Collection<FlightMission> flightMissions = nassaContext.retrieveBaseEntityList(FlightMission.class);
        String response = (flightMissions.size() == 0)? "Check the file with Missions! It is empty!":"";
        for(FlightMission flightMission : flightMissions){
            response = response + flightMission.toString() + "\n";
        }
        return response;
    }
}
