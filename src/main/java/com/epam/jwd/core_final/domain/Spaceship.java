package com.epam.jwd.core_final.domain;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    //todo
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;
    //Added by me
    private boolean flightMission;

    public Spaceship(String name, Long flightDistance, Map<Role, Short> crew){
        super(name);
        this.flightDistance = flightDistance;
        this.crew = crew;
        isReadyForNextMissions = true;
        flightMission = false;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightMission(boolean mission) {
        flightMission = mission;
    }

    public boolean getFlightMission() {
        return flightMission;
    }

    public void notReadyForNextMissions(){
        isReadyForNextMissions = false;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    @Override
    public String toString() {
        return  "name=" + getName() +
                ", crew=" + crew.toString() +
                ", flightDistance=" + flightDistance +
                ", onFlightMission=" + flightMission;
    }
}
