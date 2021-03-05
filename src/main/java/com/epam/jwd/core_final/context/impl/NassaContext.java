package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.util.ArrayList;
import java.util.Collection;

// todo
public class NassaContext extends Thread implements ApplicationContext {
    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                this.init();
                Thread.sleep(1000);
            } catch (InvalidStateException e) {
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            } catch (InterruptedException e){
                break;
            }
        }
    }

    // no getters/setters for them
    private  Collection<CrewMember> crewMembers = new ArrayList<>();
    private  Collection<Spaceship> spaceships = new ArrayList<>();
    private  Collection<Planet> planetMap = new ArrayList<>();
    private Collection<FlightMission> flightMissions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
    if(tClass.equals(CrewMember.class)){
        return (Collection<T>) crewMembers;
    } else if(tClass.equals(Spaceship.class)) {
        return (Collection<T>) spaceships;
    } else if(tClass.equals(Planet.class)){
        return (Collection<T>) planetMap;
    } else {
        return (Collection<T>) flightMissions;
    }
 }

    /**
     * You have to read input files, populate collections
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        CrewService crewService = new CrewServiceImpl();
        SpaceshipService spaceshipService = new SpaceshipServiceImpl();
        SpacemapService spacemapService = new SpacemapServiceImpl();
        MissionService missionService = new MissionServiceImpl();
        try {
            crewMembers = crewService.findAllCrewMembers();
            spaceships = spaceshipService.findAllSpaceships();
            planetMap = spacemapService.findAllPlanets();
            flightMissions = missionService.findAllMissions();
        }catch (ServiceException e){
            throw new InvalidStateException(e.getMessage());
        }finally {
            try {
                crewMembers = crewService.findAllCrewMembers();
                spaceships = spaceshipService.findAllSpaceships();
                planetMap = spacemapService.findAllPlanets();
                flightMissions = missionService.findAllMissions();
            } catch (ServiceException e) {
            }

        }


    }
}
