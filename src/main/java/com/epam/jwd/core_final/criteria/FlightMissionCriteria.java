package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDateTime startDate;
    private Planet planetFrom;
    private Planet planetTo;

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setPlanetFrom(Planet planetFrom) {
        this.planetFrom = planetFrom;
    }

    public void setPlanetTo(Planet planetTo) {
        this.planetTo = planetTo;
    }

    public FlightMission getFlightMission(){
        return new FlightMission(getName(), startDate, planetFrom, planetTo);
    }
}
