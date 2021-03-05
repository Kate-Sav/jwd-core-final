package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Collection;

public class SpaceShipByName  implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
        Collection<Spaceship> spaceShipCollection = nassaContext.retrieveBaseEntityList(Spaceship.class);
        for(Spaceship spaceship : spaceShipCollection){
            if(spaceship.getName().equalsIgnoreCase(splitRequest[1])){
                return spaceship.toString();
            }
        }
        return "Spaceship does not exist!";
    }
}
