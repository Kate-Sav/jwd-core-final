package com.epam.jwd.core_final.dao;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.DAOException;

public interface CrewMemberDao {

     String getMembers() throws DAOException;
     boolean updateCrewMemberDetails(CrewMember crewMember) throws DAOException;
     boolean addCrewMember(CrewMember crewMember) throws DAOException;
}
