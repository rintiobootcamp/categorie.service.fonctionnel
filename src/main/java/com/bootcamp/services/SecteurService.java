package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.crud.SecteurCRUD;
import com.bootcamp.entities.Secteur;
import com.bootcamp.helpers.SecteurHelper;
import com.bootcamp.pivots.SecteurWS;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class SecteurService implements DatabaseConstants {
    List<Secteur> secteurs = null;

    @PostConstruct
    public void init(){
        try {
            this.secteurs = SecteurCRUD.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(fixedRate = 750000)
    public void getAllProjetInit(){
        try {
            this.secteurs = SecteurCRUD.read();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        int id = secteur.getId();
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Secteur secteurToUpDate = SecteurCRUD.read(criterias).get(0);
        secteur.setDateCreation(secteurToUpDate.getDateCreation());
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
        SecteurHelper helper = new SecteurHelper();
        Secteur secteur = helper.convertSecteurWSToSecteur(read(id));
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
    public SecteurWS read(int id) throws SQLException {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Secteur> secteurs = SecteurCRUD.read(criterias);
        Secteur secteur = this.secteurs.stream().filter(t->t.getId()==id).findFirst().get();
        SecteurHelper helper = new SecteurHelper();
        return helper.convertSecteurToSecteurWS(secteur);
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
    public List<SecteurWS> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Secteur> secteurs;
        if (criterias == null && fields == null) {
            secteurs = this.secteurs;
        } else if (criterias != null && fields == null) {
            secteurs = SecteurCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            secteurs = SecteurCRUD.read(fields);
        } else {
            secteurs = SecteurCRUD.read(criterias, fields);
        }

        SecteurHelper helper = new SecteurHelper();
        return helper.getListSecteurWS(secteurs);
    }

    /**
     * Get a sector by its name
     *
     * @param nom
     * @return sector
     * @throws SQLException
     */
    public SecteurWS getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Secteur> secteurs = SecteurCRUD.read(criterias);

        SecteurHelper helper = new SecteurHelper();
        return helper.convertSecteurToSecteurWS(secteurs.get(0));
    }

    /**
     * Check if the given sector exist in the database
     *
     * @param secteur
     * @return
     * @throws Exception
     */
    public boolean exist(Secteur secteur) throws Exception {
        return getByName(secteur.getNom()) != null;
    }

    /**
     * Check if the given sector exist in the database
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean exist(int id) throws Exception {
        return read(id) != null;
    }

}
