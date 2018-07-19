package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.helpers.AxeHelper;
import com.bootcamp.helpers.PilierHelper;
import com.bootcamp.pivots.PilierWS;
import com.rintio.elastic.client.ElasticClient;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class PilierService implements DatabaseConstants {
//    List<Pilier> piliers = null;
//
//    @Scheduled(fixedRate = 750000)
//    public void getAllProjetInit(){
//        try {
//            this.piliers = PilierCRUD.read();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    ElasticClient elasticClient;
    public PilierService(){
        elasticClient = new ElasticClient();
    }

    /**
     * Get a pillar by its id
     *
     * @param id
     * @return pillar
     * @throws SQLException
     */
    public PilierWS read(int id) throws SQLException,Exception {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Pilier pilier = getAllPilier().stream().filter(t->t.getId()==id).findFirst().get();
        PilierHelper helper = new PilierHelper();
        return helper.convertPilierToPilierWS(pilier);
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
    public List<PilierWS> getAll() throws SQLException, Exception,IllegalAccessException, DatabaseException, InvocationTargetException {
        PilierHelper helper = new PilierHelper();
        return helper.getListPilierWS(getAllPilier());
    }

    public List<Pilier> getAllPilier() throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("piliers");
        ModelMapper modelMapper = new ModelMapper();
        List<Pilier> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Pilier.class));
        }
        return rest;
    }

    public void createPilierIndex(Pilier pilier) throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
        elasticClient.creerIndexObject("piliers","pilier",pilier,pilier.getId());

    }

    public boolean createAllIndexPilier()throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
        List<Pilier> piliers = PilierCRUD.read();
        for (Pilier pilier : piliers){
            elasticClient.creerIndexObjectNative("piliers","pilier",pilier,pilier.getId());
//            LOG.info("pilier "+pilier.getNom()+" created");
        }
        return true;
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
    public boolean delete(int id) throws SQLException,Exception{
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
