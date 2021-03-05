package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.dao.SpacemapDao;
import com.epam.jwd.core_final.dao.impl.DaoFactory;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpacemapServiceImpl implements SpacemapService {
    private final SpacemapDao spacemapDao = DaoFactory.getInstance().getSpacemapDao();

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        double distance = Math.sqrt(Math.pow((second.getCoordinate().getX() - first.getCoordinate().getX()), 2) +
                Math.pow((second.getCoordinate().getY()-first.getCoordinate().getY()),2));
        return (int)distance;
    }

    @Override
    public List<Planet> findAllPlanets() {
        List<Planet> planetList = new ArrayList<>();
        String planetString = spacemapDao.getSpacemap();
        String [] planetArr = planetString.split("\n");
        for(int i = 0; i < planetArr.length;i++){
            String [] dataEnter = planetArr[i].split(",");
            for(int j = 0; j < dataEnter.length; j++){
                if(!dataEnter[j].equals("null")){
                    planetList.add(new Planet(dataEnter[j], i, j));
                }
            }
        }
        return planetList;
    }

    @Override
    public Planet getRandomPlanet() {
        List<Planet> planetList = findAllPlanets();
        Random rand = new Random();
        Planet planet = planetList.get(rand.nextInt(planetList.size()));
        return planet;
    }

    @Override
    public Planet getPlanetByName(String planetName) {
        List<Planet> planetList = findAllPlanets()
                .stream()
                .filter(planet -> planet.getName().equals(planetName))
                .collect(Collectors.toList());

        return planetList.get(0);
    }
}
