package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

public class FlightMissionJson implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String[] splitRequest = request.split(";");
        MissionService missionService = ServiceFactory.getInstance().getMissionService();
        try {
            if(missionService.addFlightMissionJSON(splitRequest[1])){
                return "FlightMission information was added to file successfully!";
            }
        } catch (ServiceException e) {
            return e.getMessage();
        }
        return "FlightMission does not exist! Information was not added to file!";
    }
}
