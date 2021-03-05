package com.epam.jwd.core_final.dao.impl;

import com.epam.jwd.core_final.dao.SpacemapDao;

import java.io.*;

public class SpacemapDaoImpl implements SpacemapDao {
    private final File file = new File("src/main/resources/input/spacemap");
    SpacemapDaoImpl(){}

    @Override
    public String getSpacemap() {
        String spacemap = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null){
                spacemap = spacemap + line + "\n";
                line = bufferedReader.readLine();
            }
        }catch (FileNotFoundException e){

        } catch (IOException e){

        }
        return spacemap;
    }
}
