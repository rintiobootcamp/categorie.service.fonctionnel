package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.SecteurCRUD;
import com.bootcamp.entities.Secteur;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class SecteurService implements DatabaseConstants {

    /**
     * Insert the given sector in the database
     *
     * @param secteur
     * @return sector
     * @throws SQLException
     */
    public Secteur create(Secteur secteur) throws SQLException {
        secteur.setDateCreation(System.currentTimeMillis());
        secteur.setDateMiseAJour(System.currentTimeMillis());
        SecteurCRUD.create(secteur);
        return secteur;
    }

    /**
     * Update the given sector in the database
     *
     * @param secteur
     * @return
     * @throws SQLException
     */
    public boolean update(Secteur secteur) throws SQLException {
        secteur.setDateMiseAJour(System.currentTimeMillis());
        SecteurCRUD.update(secteur);
        return true;
    }

    /**
     * Delete a sector in the database by its id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        Secteur secteur = read(id);
        SecteurCRUD.delete(secteur);
        return true;
    }

    /**
     * Get a sector by its database
     *
     * @param id
     * @return sector
     * @throws SQLException
     */
    public Secteur read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Secteur> secteurs = SecteurCRUD.read(criterias);

        return secteurs.get(0);
    }

    /**
     * Get all the sectors in the database matching the given request
     *
     * @param request
     * @return sectors list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<Secteur> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Secteur> secteurs = null;
        if (criterias == null && fields == null) {
            secteurs = SecteurCRUD.read();
        } else if (criterias != null && fields == null) {
            secteurs = SecteurCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            secteurs = SecteurCRUD.read(fields);
        } else {
            secteurs = SecteurCRUD.read(criterias, fields);
        }

        return secteurs;
    }

    /**
     * Get a sector by its name
     *
     * @param nom
     * @return sector
     * @throws SQLException
     */
    public Secteur getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Secteur> secteurs = SecteurCRUD.read(criterias);

        return secteurs.get(0);
    }

    /**
     * Check if the given sector exist in the database
     *
     * @param secteur
     * @return
     * @throws Exception
     */
    public boolean exist(Secteur secteur) throws Exception {
        if (getByName(secteur.getNom()) != null) {
            return true;
        }
        return false;
    }

    /**
     * Check if the given sector exist in the database
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
