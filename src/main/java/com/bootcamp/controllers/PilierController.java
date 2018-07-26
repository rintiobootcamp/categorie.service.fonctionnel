package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Pilier;
import com.bootcamp.pivots.PilierWS;
import com.bootcamp.services.PilierService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Bello
 */
@RestController("PilierController")
@RequestMapping("/piliers")
@Api(value = "Pilier API", description = "Pilier API")
@CrossOrigin(origins = "*")
public class PilierController {

    @Autowired
    PilierService pilierService;
    @Autowired
    HttpServletRequest request;
    @RequestMapping(value = "/elasticdata",method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create Elasticsearch indesxes", notes = "Create Elasticsearch indesxes")
    public ResponseEntity<String> createIndexs() throws Exception {
        String retour = "NOT DONE";
        if (pilierService.createAllIndexPilier())
            retour = "DONE";
        return new ResponseEntity<>(retour, HttpStatus.OK);
    }

    /**
     * Insert a pillar in the database
     *
     * @param pilier
     * @return pilier
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a pilier", notes = "Create a pilier")
    public ResponseEntity<Pilier> create(@RequestBody @Valid Pilier pilier) throws Exception {
        pilier = pilierService.create(pilier);
        return new ResponseEntity<>(pilier, HttpStatus.OK);
    }

    /**
     * Get a pillar by its id
     *
     * @param id
     * @return pillar
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a pilier", notes = "Read a pilier")
    public ResponseEntity<PilierWS> read(@PathVariable int id) throws Exception {

        HttpStatus httpStatus ;

        PilierWS pilier = pilierService.read(id);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(pilier, httpStatus);
    }

    /**
     * Get all the pillars of the database
     *
     * @return pillars list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "liste des  pilars", notes = "liste des piliers")
    public ResponseEntity<List<PilierWS>> read() throws SQLException, Exception, DatabaseException, InvocationTargetException {

        HttpStatus httpStatus ;

        List<PilierWS> piliers = pilierService.getAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(piliers, httpStatus);
    }

    /**
     * Update the given pillar
     *
     * @param pilier
     * @return pilier
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a pilier", notes = "update a pilier")
    public ResponseEntity<Boolean> update(@RequestBody @Valid Pilier pilier) throws Exception {
        boolean done = pilierService.update(pilier);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Delete a pillar by its id
     *
     * @param id
     * @return
     * @throws Exception
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete Piliers", notes = "delete a particular Piliers")
    public ResponseEntity<Boolean> delete(@PathVariable int id) throws Exception, IllegalAccessException, DatabaseException, InvocationTargetException {
        if (pilierService.exist(id));
        boolean done = pilierService.delete(id);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Link the given axe and the given pillar
     *
     * @param idPilier
     * @param idAxe
     * @return pillar
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/addAxe/{idPilier}/{idAxe}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Add a axe to a pillar", notes = "Add a axe to a pillar")
    public ResponseEntity<Pilier> addAxeToPillier(@PathVariable("idPilier") int idPilier, @PathVariable("idAxe") int idAxe) throws Exception {
        Pilier pilier = pilierService.addAxe(idAxe, idPilier);
        return new ResponseEntity<>(pilier, HttpStatus.OK);
    }

    /**
     * Undo the link between the given axe and the given pillar
     *
     * @param idPilier
     * @param idAxe
     * @return pillar
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/removeAxe/{idPilier}/{idAxe}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Remove an axe from a piliar", notes = "Remove an axe from a piliar")
    public ResponseEntity<Pilier> removeAxeToPillier(@PathVariable("idPilier") int idPilier, @PathVariable("idAxe") int idAxe) throws Exception {
        Pilier pilier = pilierService.removeAxe(idAxe, idPilier);
        return new ResponseEntity<>(pilier, HttpStatus.OK);
    }
}
