package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.dao.SpaceshipDao;
import com.epam.jwd.core_final.dao.impl.DaoFactory;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DAOException;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.*;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService{
    private final SpaceshipDao spaceshipDao = DaoFactory.getInstance().getSpaceshipDao();

    @Override
    public List<Spaceship> findAllSpaceships() throws ServiceException{
        List<Spaceship> spaceshipList = new ArrayList<>();
        String spaceshipString;
        try {
            spaceshipString = spaceshipDao.getSpaceship();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        String [] membersArray = spaceshipString.split("\n");
        for(int i = 0; i < membersArray.length; i++){
            String [] dataEnter = membersArray[i].split(";");
            Map<Role, Short> map = Arrays.stream(dataEnter[2].replace("{", "").replace("}", "").split(","))
                    .map(arrayData-> arrayData.split(":"))
                    .collect(Collectors.toMap(d->
                            (Role.resolveRoleById(Integer.parseInt(d[0]))), d-> Short.parseShort(d[1])));
            Spaceship spaceship = new Spaceship(dataEnter[0],Long.parseLong(dataEnter[1]),new TreeMap<>(map));
            if(dataEnter[3].equals("true")){
                spaceship.setFlightMission(true);
            }
            if(dataEnter[4].equals("false")){
                spaceship.notReadyForNextMissions();
            }
            spaceshipList.add(spaceship);
        }
        return spaceshipList;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) throws ServiceException{
        try {
            List<Spaceship> spaceshipList = findAllSpaceships();
            List<Spaceship> spaceshipListByCriteria = spaceshipList
                    .stream()
                    .filter(c -> c.getName().equals(criteria.getName()))
                    .collect(Collectors.toList());
            // Maybe throw NoSuchElementException, if there is no such member???
            return spaceshipListByCriteria;
        }catch (ServiceException e){
            throw e;
        }
    }

    //Add Exception - Mb NullPointerExc
    @Override
    public Spaceship findSpaceshipForMission(FlightMission flightMission) throws ServiceException{
        List<Spaceship> spaceshipList;
        try {
            spaceshipList = findAllSpaceships();
        } catch (ServiceException e) {
            throw e;
        }
        List<Spaceship> spaceshipListSuitable = new ArrayList<>();
        Spaceship spaceship;
        for(Spaceship spaceshipFromList : spaceshipList){
            if(spaceshipFromList.getReadyForNextMissions() == true &&
                    spaceshipFromList.getFlightMission() == false && spaceshipFromList.getFlightDistance()
                    >= flightMission.getDistance()){
                spaceshipListSuitable.add(spaceshipFromList);
            }
        }
        try {
            spaceship = spaceshipListSuitable.get(0);
            for (Spaceship spaceshipFromList : spaceshipListSuitable) {
                if (spaceshipFromList.getFlightDistance() < spaceship.getFlightDistance()) {
                    spaceship = spaceshipFromList;
                }
            }
        }catch (IndexOutOfBoundsException | NullPointerException e){
            throw new ServiceException("There is no available spaceship! Try later!");
        }
        return spaceship;
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) throws ServiceException{
        try {
            if (isExistSpaceship(spaceship)) {
                spaceshipDao.updateSpaceship(spaceship);
                return spaceship;
            }
        }catch (RuntimeException | DAOException | ServiceException e){
            throw new ServiceException(e.getMessage());
        }
        throw new UnknownEntityException("There is no such spaceship exist. Firstly create spaceship");
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        try {
            if (!isExistSpaceship(spaceship)) {
                spaceshipDao.addSpaceship(spaceship);
                return spaceship;
            }
        }catch (DAOException | ServiceException e){
            throw new RuntimeException(e);
        }
        throw new UnknownEntityException("Spaceship is already exist!");
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) throws ServiceException {
        List<Spaceship> spaceshipListList;
        Optional<Spaceship> spaceshipOptional;
        try {
            spaceshipListList = findAllSpaceshipsByCriteria(criteria);
            spaceshipOptional = Optional.of(spaceshipListList.get(0));
        }catch (NullPointerException | ServiceException e){
            throw e;
        }
        return spaceshipOptional;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship)throws RuntimeException {
        if(spaceship.getFlightMission() == false){
            spaceship.setFlightMission(true);
        }else {
            spaceship.setFlightMission(false);
        }
        try {
            updateSpaceshipDetails(spaceship);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isExistSpaceship(Spaceship spaceship) throws ServiceException{
        Criteria<Spaceship> spaceshipCriteria = new SpaceshipCriteria();
        spaceshipCriteria.setName(spaceship.getName());
        try {
            List<Spaceship> spaceshipList = findAllSpaceshipsByCriteria(spaceshipCriteria);
            if (spaceshipList.isEmpty()) {
                return false;
            }
        }catch (ServiceException e){
            throw e;
        }
        return true;
    }
}
