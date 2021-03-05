package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

import java.util.Map;
import java.util.TreeMap;

public class SpaceShipUpdate implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String[] splitRequest = request.split(";");
        String[] splitCrew = splitRequest[3].split(",");
        Map<Role, Short> mapCrew = new TreeMap<>();
        for (String split : splitCrew) {
            String[] splitString = split.split(":");
            mapCrew.put(Role.valueOf(splitString[0]), Short.parseShort(splitString[1]));
        }
        SpaceshipService spaceshipService = ServiceFactory.getInstance().getSpaceshipService();
        try {
            Spaceship spaceship = spaceshipService.updateSpaceshipDetails(new Spaceship(splitRequest[1],
                    Long.parseLong(splitRequest[2]), mapCrew));
            return "Spaceship was updated successfully! " + spaceship.toString();
        } catch (ServiceException | RuntimeException e) {
            return e.getMessage();
        }
    }
}
