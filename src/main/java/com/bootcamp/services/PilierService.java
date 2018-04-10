package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.helpers.AxeHelper;
import com.bootcamp.helpers.PilierHelper;
import com.bootcamp.pivots.PilierWS;
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
    public PilierWS read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Pilier> piliers = PilierCRUD.read(criterias);
        PilierHelper helper = new PilierHelper();

        return helper.convertPilierToPilierWS(piliers.get(0));
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
    public List<PilierWS> getAll() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        PilierHelper helper = new PilierHelper();
        return helper.getListPilierWS(PilierCRUD.read());
    }

    /**
     * Insert the given pillar in the database
     *
     * @param pilier
     * @return pilier
     * @throws SQLException
     */
    public Pilier create(Pilier pilier) throws SQLException {
        pilier.setDateCreation(System.currentTimeMillis());
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
        int id = pilier.getId();
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Pilier pilierToUpdate = PilierCRUD.read(criterias).get(0);
        pilier.setDateCreation(pilierToUpdate.getDateCreation());
        pilier.setDateMiseAJour(System.currentTimeMillis());
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
        PilierHelper helper = new PilierHelper();
        Pilier pilier = helper.convertPilierWSToPilier(read(id));
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
    public PilierWS getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Pilier> piliers = PilierCRUD.read(criterias);
        PilierHelper helper = new PilierHelper();

        return helper.convertPilierToPilierWS(piliers.get(0));
    }

    /**
     * Check if the given pillar exist
     *
     * @param pilier
     * @return
     * @throws Exception
     */
    public boolean exist(Pilier pilier) throws Exception {
        return getByName(pilier.getNom()) != null;
    }

    /**
     * Check if the a pillar exist by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean exist(int id) throws Exception {
        return read(id) != null;
    }

    /**
     * Link the given axe to the given pillar
     *
     * @param idAxe
     * @param idPilier
     * @return phase
     * @throws SQLException
     */
    public Pilier addAxe(int idAxe, int idPilier) throws Exception {
        AxeService service = new AxeService();
        AxeHelper axeHelper = new AxeHelper();
        Axe axe = axeHelper.convertAxeWSToAxe(service.read(idAxe));
        PilierHelper helper = new PilierHelper();
        Pilier pilier = helper.convertPilierWSToPilier(read(idPilier));

        pilier.getAxes().add(axe);

        this.update(pilier);
        return pilier;
    }

    /**
     * Undo the link between the given axe to the given pillar
     *
     * @param idAxe
     * @param idPilier
     * @return phase
     * @throws SQLException
     */
    public Pilier removeAxe(int idAxe, int idPilier) throws Exception {
        AxeService service = new AxeService();
        AxeHelper axeHelper = new AxeHelper();
        Axe axe = axeHelper.convertAxeWSToAxe(service.read(idAxe));
        PilierHelper helper = new PilierHelper();
        Pilier pilier = helper.convertPilierWSToPilier(read(idPilier));
        
        int index;
        for (Axe axe1 : pilier.getAxes()) {
            if (axe1.getId() == axe.getId()) {
                index = pilier.getAxes().indexOf(axe1);
                pilier.getAxes().remove(index);
                break;
            }
        }
        this.update(pilier);
        return pilier;
    }

}
