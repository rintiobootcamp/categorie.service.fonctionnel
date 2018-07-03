package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.AxeCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Secteur;
import com.bootcamp.helpers.AxeHelper;
import com.bootcamp.helpers.SecteurHelper;
import com.bootcamp.pivots.AxeWS;
import com.rintio.elastic.client.ElasticClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class AxeService {
    ElasticClient elasticClient;
//    @PostConstruct
    public void AxeService(){
        elasticClient = new ElasticClient();
    }
//
//    List<Axe> axes = null;
//
//    @Scheduled(fixedRate = 750000)
//    public void getAllProjetInit(){
//        try {
//            this.axes = AxeCRUD.read();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * Insert the given axe in the database
     *
     * @param axe
     * @return axe
     * @throws Exception
     */
    public Axe create(Axe axe) throws Exception {
        axe.setDateCreation(System.currentTimeMillis());
        axe.setDateMiseAJour(System.currentTimeMillis());
        AxeCRUD.create(axe);
        return axe;
    }

    /**
     * Update the given axe in the database
     *
     * @param axe
     * @return
     * @throws SQLException
     */
    public boolean update(Axe axe) throws SQLException {
//        int id = axe.getId();
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        Axe axesToUpdate = AxeCRUD.read(criterias).get(0);
//        axe.setDateCreation(axesToUpdate.getDateCreation());
        axe.setDateMiseAJour(System.currentTimeMillis());
        return AxeCRUD.update(axe);
    }

    /**
     * Delete the given axe in the database
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(int id) throws Exception {
        AxeHelper helper = new AxeHelper();
        Axe axe = helper.convertAxeWSToAxe(read(id));
        AxeCRUD.delete(axe);
        return true;
    }

    /**
     * Get a axe by its id
     *
     * @param id
     * @return axe
     * @throws SQLException
     */
    public AxeWS read(int id) throws Exception {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Axe> axes = AxeCRUD.read(criterias);
        AxeHelper helper = new AxeHelper();
        Axe axe = getAllAxes().stream().filter(t->t.getId()==id).findFirst().get();

        return helper.convertAxeToAxeWS(axe);
    }

    /**
     * Get a axe by its name
     *
     * @param nom
     * @return axe
     * @throws SQLException
     */
    public AxeWS getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Axe> axes = AxeCRUD.read(criterias);
        AxeHelper helper = new AxeHelper();
        return helper.convertAxeToAxeWS(axes.get(0));
    }

    /**
     * Get all the axes in the database matching the given request
     *
     * @param request
     * @return axes list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<AxeWS> readAll(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException,Exception, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Axe> axes;
        if (criterias == null && fields == null) {
            axes = getAllAxes();
        } else if (criterias != null && fields == null) {
            axes = AxeCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            axes = AxeCRUD.read(fields);
        } else {
            axes = AxeCRUD.read(criterias, fields);
        }

        AxeHelper helper = new AxeHelper();
        return helper.getListAxeWS(axes);
    }

    public List<Axe> getAllAxes() throws Exception{
         elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("axes");
        ModelMapper modelMapper = new ModelMapper();
        List<Axe> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Axe.class));

        }
        return rest;
    }

    public void createAxeIndex(Axe axe) throws Exception{
         elasticClient = new ElasticClient();
        elasticClient.creerIndexObject("axes","axe",axe,axe.getId());

    }


    /**
     * Count all the axes of the database
     *
     * @return count
     * @throws SQLException
     */
    public int getCountAxes() throws SQLException {
        return AxeCRUD.read().size();
    }

    /**
     * Check if an axe exist in the database
     *
     * @param axe
     * @return
     * @throws Exception
     */
    public boolean exist(Axe axe) throws Exception {
        return getByName(axe.getNom()) != null;
    }

    /**
     * Check if an axe exist
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean exist(int id) throws Exception {
        return read(id) != null;
    }

    /**
     * Link the given axe to the given sector
     *
     * @param idSecteur
     * @param idAxe
     * @return phase
     * @throws SQLException
     */
    public Axe addSecteur(int idSecteur, int idAxe) throws Exception {
        SecteurService service = new SecteurService();
        AxeHelper helper = new AxeHelper();
        Axe axe = helper.convertAxeWSToAxe(read(idAxe));
        
        SecteurHelper secteurHelper = new SecteurHelper();
        Secteur secteur = secteurHelper.convertSecteurWSToSecteur(service.read(idSecteur));
        axe.getSecteurs().add(secteur);
        this.update(axe);
        return axe;
    }

    /**
     * Undo the link between the given axe to the given sector
     *
     * @param idSecteur
     * @param idAxe
     * @return phase
     * @throws SQLException
     */
    public Axe removeSecteur(int idSecteur, int idAxe) throws Exception {
        SecteurService service = new SecteurService();
        AxeHelper helper = new AxeHelper();
        Axe axe = helper.convertAxeWSToAxe(read(idAxe));

        SecteurHelper secteurHelper = new SecteurHelper();
        Secteur secteur = secteurHelper.convertSecteurWSToSecteur(service.read(idSecteur));
        int index;
        for (Secteur secteur1 : axe.getSecteurs()) {
            if (secteur1.getId() == secteur.getId()) {
                index = axe.getSecteurs().indexOf(secteur1);
                axe.getSecteurs().remove(index);
                break;
            }
        }
        this.update(axe);
        return axe;
    }
}
