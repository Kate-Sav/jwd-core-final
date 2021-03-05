package com.epam.jwd.core_final.dao;


import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DAOException;

public interface SpaceshipDao {
    String getSpaceship() throws DAOException;
    boolean updateSpaceship(Spaceship spaceship) throws DAOException;
    boolean addSpaceship(Spaceship spaceship) throws DAOException;
}
