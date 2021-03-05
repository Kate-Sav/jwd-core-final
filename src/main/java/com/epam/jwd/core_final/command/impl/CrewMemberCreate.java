package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

public class CrewMemberCreate implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String[] splitRequest = request.split(";");
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        Role role;
        Rank rank;
        try {
            role = Role.valueOf(splitRequest[1].toUpperCase());
            rank = Rank.valueOf(splitRequest[3].toUpperCase());
        }catch (IllegalArgumentException e){
            return "WARN! Check role or rank! CrewMember was not created!";
        }
        try {
            CrewMember crewMember = crewService.createCrewMember(new CrewMember(role,splitRequest[2], rank));
            return "CrewMember was created successfully!";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }
}
