package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.AxeCRUD;
import com.bootcamp.entities.Axe;
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
public class AxeService implements DatabaseConstants{

    AxeCRUD axeCRUD;

    @PostConstruct
    public void init(){
        axeCRUD = new AxeCRUD();
    }

    public Axe create(Axe axe) throws Exception {
        axe.setDateMiseAJour(System.currentTimeMillis());
       axeCRUD.create(axe);
      return axe;
    }

    public boolean update(Axe axe) throws SQLException {
        axeCRUD.update(axe);
        return true;
    }

    public boolean delete(int id) throws SQLException {
        Axe axe = read(id);
        axeCRUD.delete(axe);
        return true;
    }

    public Axe read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Axe> axes = axeCRUD.read(criterias);

        return axes.get(0);
    }

    public Axe getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Axe> axes = axeCRUD.read(criterias);

        return axes.get(0);
    }


    public List<Axe> readAll(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Axe> axes = null;
        if(criterias == null && fields == null)
           axes =  axeCRUD.read();
        else if(criterias!= null && fields==null)
            axes = axeCRUD.read(criterias);
        else if(criterias== null && fields!=null)
            axes = axeCRUD.read(fields);
        else
            axes = axeCRUD.read(criterias, fields);

        return axes;
    }
    
    public int getCountAxes() throws SQLException{
        return axeCRUD.read().size();
    }

    public boolean exist(Axe axe) throws Exception{
        if(getByName(axe.getNom())!=null)
            return true;
        return false;
    }

    public boolean exist(int id) throws Exception{
        if(read(id)!=null)
            return true;
        return false;
    }

}
