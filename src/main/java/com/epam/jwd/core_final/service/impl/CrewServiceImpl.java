package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.dao.CrewMemberDao;
import com.epam.jwd.core_final.dao.impl.DaoFactory;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DAOException;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.service.CrewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {
    private final CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDao();

    @Override
    public List<CrewMember> findAllCrewMembers() throws ServiceException{
        List<CrewMember> membersList = new ArrayList<>();
        String membersString;
        try {
            membersString = crewMemberDao.getMembers();
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }

        String [] membersArray = membersString.split(";");
        for(int i = 0; i < membersArray.length; i++){
            String [] dataEnter = membersArray[i].split(",");
            CrewMember crewMember = new CrewMember(Role.resolveRoleById(Integer.parseInt(dataEnter[0])),
                    dataEnter[1], Rank.resolveRankById(Integer.parseInt(dataEnter[2])));
            if(dataEnter[3].equals("true")){
                crewMember.setFlightMission(true);
            }
            if(dataEnter[4].equals("false")){
                crewMember.notReadyForNextMissions();
            }
            membersList.add(crewMember);
        }
        return membersList;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) throws ServiceException {
        try {
            List<CrewMember> memberList = findAllCrewMembers();
            List<CrewMember> membersListByCriteria = memberList
                    .stream()
                    .filter(c -> c.getName().equals(criteria.getName()))
                    .collect(Collectors.toList());

            return membersListByCriteria;
        }catch (ServiceException e){
            throw  e;
        }
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) throws ServiceException{
        try {
            if (isExistCrewMember(crewMember)) {
                crewMemberDao.updateCrewMemberDetails(crewMember);
                return crewMember;
            }
        }catch (RuntimeException | DAOException | ServiceException e) {
            throw new ServiceException(e.getMessage());
        }
        throw new ServiceException("There is no such crewMember exist. Firstly create crewMember");
    }

    @Override
    public CrewMember createCrewMember(CrewMember spaceship) throws RuntimeException {
            try {
                if(!isExistCrewMember(spaceship)) {
                    crewMemberDao.addCrewMember(spaceship);
                    return spaceship;
                }
           } catch (ServiceException | DAOException e) {
                throw new RuntimeException(e.getMessage());
            }
        throw new RuntimeException("CrewMember is already exist!");
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) throws ServiceException {
        List<CrewMember> crewMemberList;
        Optional<CrewMember> crewMemberOptional;
        try {
            crewMemberList = findAllCrewMembersByCriteria(criteria);
            crewMemberOptional = Optional.of(crewMemberList.get(0));
        }catch (NullPointerException | ServiceException e){
            throw e;
        }
        return crewMemberOptional;
    }

    @Override
    public List<CrewMember> findCrewMemberForMission(Spaceship spaceship) throws ServiceException{
        List<CrewMember> existCrewMemberList;
        try{
            existCrewMemberList = findAllCrewMembers();
        }catch (ServiceException e){
            throw e;
        }

        List<CrewMember> plannedCrewMemberList = new ArrayList<>();
        int count = 0;
        for(Map.Entry<Role, Short> pair : spaceship.getCrew().entrySet()){
            for(CrewMember crewMember : existCrewMemberList){
                if(pair.getKey().getId().equals(crewMember.getRole().getId()) && (crewMember.getFlightMission() == false
                && (crewMember.getReadyForNextMissions() == true)) &&
                count < pair.getValue()){
                    plannedCrewMemberList.add(crewMember);
                    count ++;
                }
            }
            if(((count == pair.getValue())? count : 0)== 0){
                throw new ServiceException("There is no available crew member! Try later!");
            }
            count = 0;
        }
        return plannedCrewMemberList;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        if(crewMember.getFlightMission() == false){
            crewMember.setFlightMission(true);
        }else {
            crewMember.setFlightMission(false);
        }
        try {
            updateCrewMemberDetails(crewMember);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isExistCrewMember(CrewMember crewMember) throws ServiceException{
        Criteria<CrewMember> crewMemberCriteria = new CrewMemberCriteria();
        crewMemberCriteria.setName(crewMember.getName());
        try {
            List<CrewMember> crewMemberList = findAllCrewMembersByCriteria(crewMemberCriteria);
            if(crewMemberList.isEmpty()){
                return false;
            }
        }catch (ServiceException e){
            throw e;
        }

        return true;
    }
}
