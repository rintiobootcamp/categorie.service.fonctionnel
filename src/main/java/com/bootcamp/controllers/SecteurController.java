package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Secteur;
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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("SecteurController")
@RequestMapping("/secteurs")
@Api(value = "Secteur API", description = "Secteur API")
public class SecteurController {

    @Autowired
    SecteurService secteurService;

    @Autowired
    HttpServletRequest request;
       
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a secteur", notes = "Create a secteur")
    public ResponseEntity<Secteur> create(@RequestBody @Valid Secteur secteur) throws SQLException {
        secteur = secteurService.create(secteur);
        return new ResponseEntity<>(secteur, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
    public ResponseEntity<Secteur> read(@PathVariable(name = "id") int id) throws SQLException {

        Secteur secteur = secteurService.read(id);
        return new ResponseEntity<Secteur>(secteur, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
    public ResponseEntity<List<Secteur>> read() throws InvocationTargetException, SQLException, DatabaseException, IllegalAccessException {
        List<Secteur> secteurs = new ArrayList<>();
        HttpStatus httpStatus = null;

        secteurs = secteurService.read(request);

        return new ResponseEntity<List<Secteur>>(secteurs, HttpStatus.OK);
    }
}
