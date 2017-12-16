package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.entities.Pilier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class PilierService implements DatabaseConstants {

    /**
     * Get a pillar by its id
     *
     * @param id
     * @return pillar
     * @throws SQLException
     */
    public Pilier read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Pilier> piliers = PilierCRUD.read(criterias);

        return piliers.get(0);
    }

    /**
     * Get all the axes in the database
     *
     * @return axes list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<Pilier> getAll() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        return PilierCRUD.read();
    }

    /**
     * Insert the given pillar in the database
     *
     * @param pilier
     * @return pilier
     * @throws SQLException
     */
    public Pilier create(Pilier pilier) throws SQLException {
        pilier.setDateMiseAJour(System.currentTimeMillis());
        PilierCRUD.create(pilier);
        return pilier;
    }

    /**
     * Update the given pillar in the database
     *
     * @param pilier
     * @return
     * @throws SQLException
     */
    public boolean update(Pilier pilier) throws SQLException {
        PilierCRUD.update(pilier);
        return true;
    }

    /**
     * Delete a pillar in the database by its id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        Pilier pilier = read(id);
        PilierCRUD.delete(pilier);
        return true;
    }

    /**
     * Get a pillar by its name
     *
     * @param nom
     * @return pillar
     * @throws SQLException
     */
    public Pilier getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Pilier> piliers = PilierCRUD.read(criterias);

        return piliers.get(0);
    }

    /**
     * Check if the given pillar exist
     *
     * @param pilier
     * @return
     * @throws Exception
     */
    public boolean exist(Pilier pilier) throws Exception {
        if (getByName(pilier.getNom()) != null) {
            return true;
        }
        return false;
    }

    /**
     * Check if the a pillar exist by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean exist(int id) throws Exception {
        if (read(id) != null) {
            return true;
        }
        return false;
    }

}
