package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.ServiceException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

public class CrewMemberUpdate implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        CrewMember crewMember;
        try {
            crewMember = crewService.updateCrewMemberDetails(new CrewMember(Role.valueOf(splitRequest[1].toUpperCase()),
                    splitRequest[2], Rank.valueOf(splitRequest[3].toUpperCase())));
        } catch (ServiceException e) {
           return e.getMessage();
        }
        return "CrewMember was updated successfully " + crewMember.toString();
    }
}
