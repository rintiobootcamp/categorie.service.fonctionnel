package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Pilier;
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
    public ResponseEntity<Pilier> create(@RequestBody @Valid Pilier pilier) throws SQLException {
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
    public ResponseEntity<Pilier> read(@PathVariable int id) throws SQLException {

        HttpStatus httpStatus = null;

        Pilier pilier = pilierService.read(id);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<Pilier>(pilier, httpStatus);
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
    public ResponseEntity<List<Pilier>> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {

        HttpStatus httpStatus = null;

        List<Pilier> piliers = pilierService.getAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Pilier>>(piliers, httpStatus);
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
    public ResponseEntity<Boolean> update(@RequestBody @Valid Pilier pilier) throws SQLException {
        boolean done = pilierService.update(pilier);
        return new ResponseEntity<Boolean>(done, HttpStatus.OK);
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
}
