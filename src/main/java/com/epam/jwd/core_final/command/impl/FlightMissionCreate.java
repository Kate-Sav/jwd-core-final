package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class FlightMissionCreate implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        SpacemapService spacemapService = new SpacemapServiceImpl();
        FlightMission flightMission = new FlightMission(splitRequest[1], LocalDateTime.parse(splitRequest[2], formatter),
                    spacemapService.getPlanetByName(splitRequest[3]), spacemapService.getPlanetByName(splitRequest[4]));
        MissionService missionService = ServiceFactory.getInstance().getMissionService();
        Criteria<FlightMission> missionCriteria = new FlightMissionCriteria();
        missionCriteria.setName(flightMission.getName());
        try {
            if(missionService.findMissionByCriteria(missionCriteria)!= null){
                return "FlightMission is already exist! Check the name!";
            }
        } catch (ServiceException e) {
        }
        Thread thread = new Thread(flightMission);
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "You can check whether the mission was created";
    }
}
