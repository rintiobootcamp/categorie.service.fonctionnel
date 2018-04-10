package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Secteur;
import com.bootcamp.pivots.SecteurWS;
import com.bootcamp.services.SecteurService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@RestController("SecteurContoller")
@RequestMapping("/secteurs")
@Api(value = "Secteur API", description = "Secteur API")
@CrossOrigin(origins = "*")
public class SecteurController {
//

    @Autowired
    SecteurService secteurService;

    @Autowired
    HttpServletRequest request;

    /**
     * Insert the given sector in the database
     *
     * @param secteur
     * @return secteur
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a secteur", notes = "Create a secteur")
    public ResponseEntity<Secteur> create(@RequestBody @Valid Secteur secteur) throws SQLException {
        secteur = secteurService.create(secteur);
        return new ResponseEntity<>(secteur, HttpStatus.OK);
    }

    /**
     * Get all the sectors of the database
     *
     * @return sectors list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read All Secteurs", notes = "Read aall the Secteurs")
    public ResponseEntity<List<SecteurWS>> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        List<SecteurWS> secteurs = secteurService.read(request);
        return new ResponseEntity<>(secteurs, HttpStatus.OK);
    }

    /**
     * Update the given sector in the database
     *
     * @param secteur
     * @return sector
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a secteur", notes = "update a secteur")

    public ResponseEntity<Boolean> update(@RequestBody Secteur secteur) throws SQLException {
        boolean done =  secteurService.update(secteur);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Get a sector by its id
     *
     * @param id
     * @return sector
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get one Secteurs", notes = "Read a particular Secteurs")
    public ResponseEntity<SecteurWS> getById(@PathVariable int id) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        SecteurWS secteur = secteurService.read(id);
        return new ResponseEntity<>(secteur, HttpStatus.OK);
    }

    /**
     * Delete a sector by its id
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
    @ApiOperation(value = "delete Secteurs", notes = "delete a particular Secteurs")
    public ResponseEntity<Boolean> delete(@PathVariable int id) throws Exception, IllegalAccessException, DatabaseException, InvocationTargetException {
        if (secteurService.exist(id));
        boolean done = secteurService.delete(id);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

}
