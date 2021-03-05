package com.epam.jwd.core_final.domain;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    private Long id;
    private String name;
    private File file;

    protected AbstractBaseEntity(String name){
        this.name = name;
    }

    @Override
    public Long getId() {
        // todo
        return id;
    }


    @Override
    public String getName() {
        // todo
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
