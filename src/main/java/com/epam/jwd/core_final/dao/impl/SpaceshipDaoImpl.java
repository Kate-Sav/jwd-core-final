package com.epam.jwd.core_final.dao.impl;

import com.epam.jwd.core_final.dao.SpaceshipDao;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DAOException;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;


public class SpaceshipDaoImpl implements SpaceshipDao {
    private static File file = new File("src/main/resources/input/spaceships");
    SpaceshipDaoImpl(){}

    @Override
    public String getSpaceship() throws DAOException {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null){
                if(!line.startsWith("#")){
                    result = line + "\n" + result;
                }
                line = br.readLine();
            }

        }catch (FileNotFoundException e){
            throw new DAOException("WARN: Spaceship information is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public boolean updateSpaceship(Spaceship spaceship) throws DAOException{
        boolean isUpdated = false;
        File tempFile = new File("src/main/resources/input/tempFileSpaceship");
        FileWriter fw = null;
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            fw = new FileWriter(tempFile, true);
            String line = br.readLine();
            String [] dataEnter;

            while (line != null){
                if(line.startsWith("#")){
                    fw.write(line + "\n");
                } else {
                    dataEnter = line.split(";");
                        if (dataEnter[0].equals(spaceship.getName())) {
                            Map<Role, Short> mapCrew = new TreeMap<>(spaceship.getCrew());
                            String crew = mapCrew.keySet().stream()
                                    .map(key -> key.getId() + ":" + mapCrew.get(key))
                                    .collect(Collectors.joining(",", "{", "}"));
                            fw.write(spaceship.getName() + ";" + spaceship.getFlightDistance()+ ";"
                                    + crew + ";" + spaceship.getFlightMission() + ";" +
                                    spaceship.getReadyForNextMissions() + "\n");
                            isUpdated = true;
                        } else {
                            fw.write(line + "\n");
                        }
                }
                line = br.readLine();
            }

        }catch (FileNotFoundException e){
            throw new DAOException("CrewMember file is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }finally {
            if(fw!= null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        file.delete();
        tempFile.renameTo(file);
        return isUpdated;
    }

    @Override
    public boolean addSpaceship(Spaceship spaceship) throws DAOException{
        boolean isAdded;
        try (FileWriter fw = new FileWriter(file, true)) {
            Map<Role, Short> mapCrew = new TreeMap<>(spaceship.getCrew());
            String crew = mapCrew.keySet().stream()
                    .map(key -> key.getId() + ":" + mapCrew.get(key))
                    .collect(Collectors.joining(",", "{", "}"));
            fw.write(spaceship.getName() + ";" + spaceship.getFlightDistance() + ";" +
                    crew + ";" + spaceship.getFlightMission() +";" + spaceship.getReadyForNextMissions() + "\n");
            isAdded = true;
        }catch (FileNotFoundException e){
            throw new DAOException("CrewMember file is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return isAdded;
    }

}
