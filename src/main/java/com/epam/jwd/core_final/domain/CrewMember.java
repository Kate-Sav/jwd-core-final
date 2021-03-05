package com.epam.jwd.core_final.domain;

import java.io.File;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMissions;
    //Added by me
    private boolean onFlightMission;

    public CrewMember(Role role,String name, Rank rank){
        super(name);
        this.role = role;
        this.rank = rank;
        isReadyForNextMissions = true;
        onFlightMission = false;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void notReadyForNextMissions(){
        isReadyForNextMissions = false;
    }

    public void setFlightMission(boolean flightMission) {
        this.onFlightMission = flightMission;
    }

    public boolean getFlightMission() {
        return onFlightMission;
    }

    public void test(){onFlightMission = false;}

    @Override
    public String toString() {
        return "name=" + getName()+
                ", role=" + role +
                ", rank=" + rank +
                ", onFlightMission=" + onFlightMission;
    }
}
