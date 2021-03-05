package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DAOException;
import com.epam.jwd.core_final.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MissionService{

    List<FlightMission> findAllMissions() throws ServiceException;

    List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) throws ServiceException;

    Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) throws ServiceException;

    FlightMission updateSpaceshipDetails(FlightMission flightMission) throws ServiceException;

    FlightMission createMission(FlightMission flightMission) throws ServiceException;

    boolean addFlightMissionJSON(String flightMission) throws ServiceException;
}
