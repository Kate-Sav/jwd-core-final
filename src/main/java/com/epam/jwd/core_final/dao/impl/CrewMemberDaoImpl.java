package com.epam.jwd.core_final.dao.impl;

import com.epam.jwd.core_final.dao.CrewMemberDao;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.DAOException;

import java.io.*;

public class CrewMemberDaoImpl implements CrewMemberDao  {
    CrewMemberDaoImpl() {}

    private static File file = new File("src/main/resources/input/crew");

    @Override
    public String getMembers() throws DAOException {
        String result = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null){
                if(line.startsWith("#")){
                    line = br.readLine();
                    continue;
                }
                result = line + result;
                line = br.readLine();
            }
        }catch (FileNotFoundException e){
            throw new DAOException("WARN: CrewMember information is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public boolean updateCrewMemberDetails(CrewMember crewMember) throws DAOException {
        boolean isUpdated = false;
        File tempFile = new File("src/main/resources/input/tempFileCrew");
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
                    for (int i = 0; i < dataEnter.length; i++) {
                        String[] arr = dataEnter[i].split(",");
                        if (arr[1].equals(crewMember.getName())) {
                            fw.write(crewMember.getRole().getId() + "," + crewMember.getName() + "," +
                                    crewMember.getRank().getId() + "," + crewMember.getFlightMission() + ","+
                                    crewMember.getReadyForNextMissions() + ";");
                            isUpdated = true;
                        } else {
                            fw.write(dataEnter[i] + ";");
                        }
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
    public boolean addCrewMember(CrewMember crewMember) throws DAOException {
        boolean isAdded;
        try (FileWriter fw = new FileWriter(file, true)) {
        fw.write(crewMember.getRole().getId() + "," + crewMember.getName() + "," +
                crewMember.getRank().getId() + "," + crewMember.getFlightMission() +
                ","+crewMember.getReadyForNextMissions() + ";");
        isAdded = true;
        }catch (FileNotFoundException e){
            throw new DAOException("CrewMember file is not available! Check the file!");
        }catch (IOException e){
            throw new DAOException(e);
        }
        return isAdded;
    }
}
