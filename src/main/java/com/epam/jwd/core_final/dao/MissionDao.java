package com.epam.jwd.core_final.dao;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DAOException;

public interface MissionDao {
    String getFlightMission() throws DAOException;
    boolean updateFlightMission(FlightMission flightMission) throws DAOException;
    boolean addFlightMission(FlightMission flightMission) throws DAOException;
    boolean addFlightMissionJSON(FlightMission flightMission) throws DAOException;
}
