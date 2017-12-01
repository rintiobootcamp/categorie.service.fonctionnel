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

    public Pilier read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Pilier> piliers = PilierCRUD.read(criterias);

        return piliers.get(0);
    }

    public List<Pilier> getAll() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        return PilierCRUD.read();
    }

//    local.public int  create(PilierUWs pilierUWs) throws SQLException {
//         Pilier pilier = new Pilier();
//         pilier.setDescription(pilierUWs.getDescription());
//         pilier.setNom(pilierUWs.getNom());
//         pilier.setDateCreation(System.currentTimeMillis());
//         pilier.setDateMiseAJour(System.currentTimeMillis());
//         pilierCRUD.create(pilier);
//
//         return pilier.getId();
//    }
//    public void update(Pilier pilier) throws SQLException {
//        pilierCRUD.update(pilier);
//    }
//
//    public Pilier delete(int id) throws SQLException {
//        Pilier pilier = read(id);
//        pilierCRUD.delete(pilier);
//
//        return pilier;
//    }
//    private List<PilierUWs> convertPilerToPilierUWS(List<Pilier> piliers){
//        List<PilierUWs> pilierUWss = new ArrayList<>();
//        for(Pilier pilier: piliers){
//           PilierUWs pilierUWs = new PilierUWs();
//        }
//    }

}
