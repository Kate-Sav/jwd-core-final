package com.epam.jwd.core_final.command.impl;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;


import java.util.Collection;

public class CrewByName implements Command {
    @Override
    public String execute(String request, NassaContext nassaContext) {
        String [] splitRequest = request.split(";");
            Collection<CrewMember> crewMemberCollection = nassaContext.retrieveBaseEntityList(CrewMember.class);
            for(CrewMember crewMember : crewMemberCollection){
                if(crewMember.getName().equalsIgnoreCase(splitRequest[1])){
                    return crewMember.toString();
                }
            }
        return "CrewMember does not exist!";
    }
}
