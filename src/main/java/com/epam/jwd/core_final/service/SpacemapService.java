package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.domain.Planet;

import java.util.List;

public interface SpacemapService {
    Planet getRandomPlanet();

    // Dijkstra ?
    int getDistanceBetweenPlanets(Planet first, Planet second);

    //added by me
    List<Planet> findAllPlanets();

    Planet getPlanetByName(String planetName);
}
