package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.FlightMission;

import java.util.Collection;

public class FlightMissionByName  implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
        Collection<FlightMission> flightMissionCollection = nassaContext.retrieveBaseEntityList(FlightMission.class);
        for(FlightMission flightMission : flightMissionCollection){
            if(flightMission.getName().equalsIgnoreCase(splitRequest[1])){
                return flightMission.toString();
            }
        }
        return "FlightMission does not exist!";
    }
}
