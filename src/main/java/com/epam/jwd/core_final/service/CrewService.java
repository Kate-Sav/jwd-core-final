package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    List<CrewMember> findAllCrewMembers() throws ServiceException;

    List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) throws ServiceException;

    Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) throws ServiceException;

    CrewMember updateCrewMemberDetails(CrewMember crewMember) throws ServiceException;

     // todo create custom exception for case, when crewMember is not able to be assigned
    void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException;

   // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // crewmember unique criteria - only name!
    CrewMember createCrewMember(CrewMember spaceship) throws RuntimeException; //ММожно сделать check Exception

    //Added by me
    List<CrewMember> findCrewMemberForMission(Spaceship spaceship) throws ServiceException;
}