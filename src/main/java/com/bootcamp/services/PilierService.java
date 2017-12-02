package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.AxeCRUD;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.entities.Pilier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class PilierService implements DatabaseConstants {

    PilierCRUD pilierCRUD;

    @PostConstruct
    public void init(){
        pilierCRUD = new PilierCRUD();
    }

    public Pilier read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Pilier> piliers = pilierCRUD.read(criterias);

        return piliers.get(0);
    }

    public List<Pilier> getAll() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        return PilierCRUD.read();
    }

    public Pilier create(Pilier pilier) throws SQLException {
        PilierCRUD.create(pilier);
        return pilier;
    }


}
