package com.epam.jwd.core_final.service.factory;

import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final CrewService crewService = new CrewServiceImpl();
    private final SpaceshipService spaceshipService = new SpaceshipServiceImpl();
    private final MissionService missionService = new MissionServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public CrewService getCrewService (){
        return crewService;
    }

    public SpaceshipService getSpaceshipService(){
        return spaceshipService;
    }

    public MissionService getMissionService(){
        return missionService;
    }


}
