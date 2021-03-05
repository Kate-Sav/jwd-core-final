package com.epam.jwd.core_final.dao.impl;

import com.epam.jwd.core_final.dao.CrewMemberDao;
import com.epam.jwd.core_final.dao.MissionDao;
import com.epam.jwd.core_final.dao.SpacemapDao;
import com.epam.jwd.core_final.dao.SpaceshipDao;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private CrewMemberDao crewMemberDao = new CrewMemberDaoImpl();
    private SpaceshipDao spaceshipDao = new SpaceshipDaoImpl();
    private MissionDao missionDao = new MissionDaoImpl();
    private SpacemapDao spacemapDao = new SpacemapDaoImpl();

    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance;
    }

    public CrewMemberDao getCrewMemberDao() {
        return crewMemberDao;
    }

    public SpaceshipDao getSpaceshipDao() {
        return spaceshipDao;
    }

    public MissionDao getMissionDao(){return  missionDao;}

    public SpacemapDao getSpacemapDao(){return  spacemapDao;}
}
