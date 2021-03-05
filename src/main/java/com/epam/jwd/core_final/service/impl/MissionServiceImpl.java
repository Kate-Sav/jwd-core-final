package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.dao.MissionDao;
import com.epam.jwd.core_final.dao.impl.DaoFactory;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DAOException;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {
    private final MissionDao missionDao = DaoFactory.getInstance().getMissionDao();
    @Override
    public List<FlightMission> findAllMissions() throws ServiceException{
        List<FlightMission> flightMissionList = new ArrayList<>();
        String missionString;
        try {
            missionString = missionDao.getFlightMission();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        String [] missionArray = missionString.split("\n");
        for(int i = 0; i < missionArray.length; i++){
            String [] dataEnter = missionArray[i].split(";");
            SpacemapService spacemapService = new SpacemapServiceImpl();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            FlightMission flightMission = new FlightMission(dataEnter[0], LocalDateTime.parse(dataEnter[1],formatter),
                    spacemapService.getPlanetByName(dataEnter[3]),spacemapService.getPlanetByName(dataEnter[4]));
            flightMission.setMissionResult(MissionResult.valueOf(dataEnter[8]));
            flightMissionList.add(flightMission);

            }
        return flightMissionList;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) throws ServiceException {
        try {
            List<FlightMission> flightMissionList = findAllMissions();
            List<FlightMission> flightMissionByCriteria = flightMissionList
                    .stream()
                    .filter(c -> c.getName().equals(criteria.getName()))
                    .collect(Collectors.toList());

            return flightMissionByCriteria;
        }catch (ServiceException e){
            throw e;
        }
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) throws ServiceException {
        List<FlightMission> flightMissionList;
        Optional<FlightMission> flightMissionOptional = null;
        try {
            flightMissionList = findAllMissionsByCriteria(criteria);
            if(flightMissionList.size() > 0) {
                flightMissionOptional = Optional.of(flightMissionList.get(0));
            }
        } catch (NullPointerException | ServiceException e) {
            throw  e;
        }

        return flightMissionOptional;
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) throws ServiceException{
        try {
            if (isExistFlightMission(flightMission)) {
                missionDao.updateFlightMission(flightMission);
                return flightMission;
            }
        }catch (RuntimeException | DAOException | ServiceException e){
            throw new ServiceException(e.getMessage());
        }
        throw new UnknownEntityException("There is no such flightMission exist. Firstly create flightMission");
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws ServiceException {
        SpaceshipService spaceshipService;
        List<CrewMember> crewMemberList = null;
        CrewService crewService = null;
        Spaceship spaceship;
        if(!isExistFlightMission(flightMission)){
            try {
                spaceshipService = ServiceFactory.getInstance().getSpaceshipService();
                spaceship = spaceshipService.findSpaceshipForMission(flightMission);
                if (spaceship != null) {
                    crewService = ServiceFactory.getInstance().getCrewService();
                    crewMemberList = crewService.findCrewMemberForMission(spaceship);
                }
            }catch (ServiceException e) {
                throw new ServiceException(e.getMessage());
            }
                if(crewMemberList != null){
                    spaceshipService.assignSpaceshipOnMission(spaceship);
                    flightMission.setSpaceship(spaceship);
                    flightMission.setAssignedCrew(crewMemberList);
                    for(CrewMember crewMember : crewMemberList){
                        crewService.assignCrewMemberOnMission(crewMember);
                    }
                    flightMission.setMissionResult(MissionResult.PLANNED);
                    try {
                        missionDao.addFlightMission(flightMission);
                    } catch (DAOException e) {
                        throw new ServiceException(e.getMessage());
                    }
                }
                return flightMission;
        }
        throw new UnknownEntityException("FlightMission is already exist! Check the name!");
    }

    @Override
    public boolean addFlightMissionJSON(String flightMission) throws ServiceException {
        try{
            Criteria<FlightMission> flightMissionCriteria = new FlightMissionCriteria();
            flightMissionCriteria.setName(flightMission);
            FlightMission flightMission1 = findMissionByCriteria(flightMissionCriteria).get();
            if(flightMission1 !=null){
                missionDao.addFlightMissionJSON(flightMission1);
                return true;
            }else {
                return false;
            }
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private boolean isExistFlightMission(FlightMission flightMission) throws ServiceException{
        Criteria<FlightMission> flightMissionCriteria = new FlightMissionCriteria();
        flightMissionCriteria.setName(flightMission.getName());
        try {
            Optional<FlightMission> flightMissionSuitable = findMissionByCriteria(flightMissionCriteria);
            if (flightMissionSuitable != null) {
                return true;
            }
        }catch (ServiceException e){
            throw e;
        }
        return false;
    }
}
