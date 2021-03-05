package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.factory.ServiceFactory;

import java.util.Collection;
import java.util.List;

public class AllCrewMembers  implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        Collection<CrewMember> crewMembers = nassaContext.retrieveBaseEntityList(CrewMember.class);
        String response = (crewMembers.size() == 0)? "Check the file with CrewMember! It is empty!":"";
        for(CrewMember crewMember : crewMembers){
            response = response + crewMember.toString() + "\n";
        }
        return response;
    }
}
