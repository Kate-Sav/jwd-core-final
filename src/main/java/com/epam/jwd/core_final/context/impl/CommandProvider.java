package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.command.CommandName;
import com.epam.jwd.core_final.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    final private Map<CommandName, Command> commands = new HashMap<>();


    public CommandProvider(){
        commands.put(CommandName.START, new StartMenu());
        commands.put(CommandName.CLOSE, new CloseMenu());
        commands.put(CommandName.MENUWORK, new MenuWork());
        commands.put(CommandName.WRONG_REQUEST, new WrongRequest());
        commands.put(CommandName.CREW, new Crew());
        commands.put(CommandName.CREW_1, new AllCrewMembers());
        commands.put(CommandName.CREW_2, new CrewByName());
        commands.put(CommandName.CREW_3, new CrewMemberCreate());
        commands.put(CommandName.CREW_4, new CrewMemberUpdate());
        commands.put(CommandName.MISSION, new FlightMission());
        commands.put(CommandName.MISSION_1, new FlightMissionAll());
        commands.put(CommandName.MISSION_2, new FlightMissionByName());
        commands.put(CommandName.MISSION_3, new FlightMissionCreate());
        commands.put(CommandName.MISSION_4, new FlightMissionJson());
        commands.put(CommandName.SPACESHIP, new SpaceShip());
        commands.put(CommandName.SPACESHIP_1, new SpaceShipAll());
        commands.put(CommandName.SPACESHIP_2, new SpaceShipByName());
        commands.put(CommandName.SPACESHIP_3, new SpaceshipCreate());
        commands.put(CommandName.SPACESHIP_4, new SpaceShipUpdate());


    }

    public Command getCommand(String name){
        String [] splitName = name.split(";");
        CommandName commandName;
        Command command;
        try {
            commandName = CommandName.valueOf(splitName[0].toUpperCase());
            command = commands.get(commandName);
        }catch (IllegalArgumentException  | NullPointerException e){
            command = commands.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
