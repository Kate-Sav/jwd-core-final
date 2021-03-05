package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.SpacemapService;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Expected fields:
         * <p>
 * missions name {@link String}
        * start date {@link java.time.LocalDate}
        * end date {@link java.time.LocalDate}
        * distance {@link Long} - missions distance
        * assignedSpaceShift {@link Spaceship} - not defined by default
        * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
        * missionResult {@link MissionResult}
        * from {@link Planet}
        * to {@link Planet}
        */
public class FlightMission extends AbstractBaseEntity implements Runnable{
    // todo
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;
    private Planet planetFrom;
    private Planet planetTo;

    private SpacemapService spacemapService = new SpacemapServiceImpl();
    public FlightMission(String name, LocalDateTime startDate, Planet planetFrom, Planet planetTo) {
        super(name);
        this.startDate = startDate;
        this.planetFrom = planetFrom;
        this.planetTo = planetTo;
        this.distance = (long)spacemapService.getDistanceBetweenPlanets(planetFrom,planetTo);
        this.endDate = startDate.plusSeconds(distance);
    }

    @Override
    public void run(){
        SpaceshipService spaceshipService = ServiceFactory.getInstance().getSpaceshipService();
        MissionService missionService = ServiceFactory.getInstance().getMissionService();
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        try {
            missionService.createMission(this);
            LocalDateTime currentTime = LocalDateTime.now();
            Thread.currentThread().sleep(Math.abs(ChronoUnit.SECONDS.between(currentTime, this.startDate)) * 1000);
            Criteria<FlightMission> criteria = new FlightMissionCriteria();
            criteria.setName(this.getName());
            this.setMissionResult(MissionResult.IN_PROGRESS);
            missionService.updateSpaceshipDetails(this);
            double random = Math.random();
            if (random == 1) {
                Thread.currentThread().interrupt();
            }
            Thread.currentThread().sleep(Math.abs(ChronoUnit.SECONDS.between(this.startDate, this.endDate)) * 1000);
            this.setMissionResult(MissionResult.COMPLETED);
            missionService.updateSpaceshipDetails(this);
            spaceshipService.assignSpaceshipOnMission(this.assignedSpaceShip);
            for (CrewMember crewMember : this.assignedCrew) {
                crewService.assignCrewMemberOnMission(crewMember);
            }
        }catch (InterruptedException e){
            this.setMissionResult(MissionResult.CANCELLED);
            try {
                missionService.updateSpaceshipDetails(this);
            for(CrewMember crewMember : this.getAssignedCrew()){
                crewMember.notReadyForNextMissions();
                crewService.updateCrewMemberDetails(crewMember);
            }
            this.assignedSpaceShip.notReadyForNextMissions();
            spaceshipService.updateSpaceshipDetails(this.assignedSpaceShip);
            } catch (ServiceException serviceException) {
            }
            System.out.println("Something go wrong with " + this.getName() + "! Check the mission!");
        } catch (UnknownEntityException | ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean setSpaceship(Spaceship assignedSpaceShip) {
            if(assignedSpaceShip != null){
                this.assignedSpaceShip = assignedSpaceShip;
                return  true;
            }
            return false;
    }

    public boolean setAssignedCrew(List<CrewMember> assignedCrew){
        if(assignedCrew != null){
            this.assignedCrew = assignedCrew;
            return true;
        }
        return false;
    }


    public Long getDistance() {
        return distance;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public Planet getPlanetFrom() {
        return planetFrom;
    }

    public Planet getPlanetTo() {
        return planetTo;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    @Override
    public String toString() {
        return "name=" + getName()+
                ", start date=" + startDate +
                ", end date=" + endDate +
                ", distance=" + distance +
                ", mission result=" + missionResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightMission)) return false;
        FlightMission that = (FlightMission) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(assignedSpaceShip, that.assignedSpaceShip) &&
                Objects.equals(assignedCrew, that.assignedCrew) &&
                missionResult == that.missionResult &&
                Objects.equals(planetFrom, that.planetFrom) &&
                Objects.equals(planetTo, that.planetTo) &&
                Objects.equals(spacemapService, that.spacemapService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, distance, assignedSpaceShip, assignedCrew, missionResult, planetFrom, planetTo, spacemapService);
    }
}
