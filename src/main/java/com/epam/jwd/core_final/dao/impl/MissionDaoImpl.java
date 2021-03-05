package com.epam.jwd.core_final.dao.impl;

import com.epam.jwd.core_final.dao.MissionDao;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.DAOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class MissionDaoImpl implements MissionDao{
    private final File file = new File("src/main/resources/input/flightmission");
    MissionDaoImpl(){}

    @Override
    public String getFlightMission() throws DAOException{
        String flightMission = "";

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line = bufferedReader.readLine();
            while (line != null){
                if(!line.startsWith("#")){
                    flightMission = line + "\n" + flightMission;
                }
                    line = bufferedReader.readLine();
            }
        }catch (FileNotFoundException e){
            throw new DAOException("WARN: Mission information is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return flightMission;
    }

    @Override
    public boolean updateFlightMission(FlightMission flightMission) throws DAOException{
        boolean isUpdated = false;
        File tempFile = new File("src/main/resources/input/tempMission");
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
                    if (dataEnter[0].equals(flightMission.getName())) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        fw.write(flightMission.getName()+";"+
                                flightMission.getStartDate().format(formatter)+";"+
                                flightMission.getEndDate().format(formatter)+";"+
                                flightMission.getPlanetFrom().getName()+";"+flightMission.getPlanetTo().getName()+";"+
                                flightMission.getDistance()+";"+flightMission.getAssignedSpaceShip().getName()+
                                ";" +flightMission.getAssignedCrew().toString() +";"+flightMission.getMissionResult()+"\n");
                        isUpdated = true;
                    } else {
                        fw.write(line + "\n");
                    }
                }
                line = br.readLine();
            }

        }catch (FileNotFoundException e){
            throw new DAOException("WARN: Mission file is not available! Check the file!");
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
    public boolean addFlightMission(FlightMission flightMission) throws DAOException {
        boolean isAdded;
        try(FileWriter fileWriter = new FileWriter(file,true)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            fileWriter.write(flightMission.getName()+";"+flightMission.getStartDate().format(formatter)
                +";"+flightMission.getEndDate().format(formatter)+";" + flightMission.getPlanetFrom().getName() + ";" +
                    flightMission.getPlanetTo().getName() + ";" + flightMission.getDistance()+";"+
                    flightMission.getAssignedSpaceShip().getName()+";"+flightMission.getAssignedCrew().toString()+";"+
                    flightMission.getMissionResult()+"\n");
            isAdded = true;
        }catch (FileNotFoundException e){
            throw new DAOException("WARN: Mission file is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return isAdded;
    }

    @Override
    public boolean addFlightMissionJSON(FlightMission flightMission) throws DAOException {
        boolean isAdded;
        File fileJson = new File("src/main/resources/out/flightmission");
        try(FileWriter fileWriter = new FileWriter(fileJson,true)){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String resultJson = objectMapper.writeValueAsString(flightMission);
            fileWriter.write(resultJson + "\n");
            isAdded = true;
        }catch (FileNotFoundException e){
            throw new DAOException("WARN: Mission file is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return isAdded;
    }
}
