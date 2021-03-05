package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Collection;

public class SpaceShipAll implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        Collection<Spaceship> spaceShipCollection = nassaContext.retrieveBaseEntityList(Spaceship.class);
        String response = (spaceShipCollection.size() == 0)? "Check the file with CrewMember! It is empty!":"";
        for(Spaceship spaceship : spaceShipCollection){
            response = response + spaceship.toString() + "\n";
        }

        return response;
    }
}
