package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Secteur;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
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
        pilier.setDateMiseAJour(System.currentTimeMillis());
        PilierCRUD.create(pilier);
        return pilier;
    }

    public boolean update(Pilier pilier) throws SQLException {
        pilierCRUD.update(pilier);
        return true;
    }

    public boolean delete(int id) throws SQLException {
        Pilier pilier = read(id);
        pilierCRUD.delete(pilier);
        return true;
    }

    public Pilier getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Pilier> piliers = pilierCRUD.read(criterias);

        return piliers.get(0);
    }

    public boolean exist(Pilier  pilier) throws Exception{
        if(getByName(pilier.getNom())!=null)
            return true;
        return false;
    }

    public boolean exist(int id) throws Exception{
        if(read(id)!=null)
            return true;
        return false;
    }


}
