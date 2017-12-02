package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@RestController("SecteurContoller")
@RequestMapping("/secteurs")
@Api(value = "Secteur API", description = "Secteur API")
public class SecteurController {
//
    @Autowired
    SecteurService secteurService;


    @Autowired
    HttpServletRequest request;

        @RequestMapping(method = RequestMethod.GET)
        @ApiVersions({"1.0"})
        @ApiOperation(value = "Read All Secteurs", notes = "Read aall the Secteurs")
        public ResponseEntity<List<Secteur>> read()throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
            List<Secteur> secteurs=secteurService.read(request);
            return new ResponseEntity<List<Secteur>>(secteurs, HttpStatus.OK);
        }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get one Secteurs", notes = "Read a particular Secteurs")
    public ResponseEntity<Secteur> getById(@PathVariable int id)throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Secteur secteur=secteurService.read(id);
        return new ResponseEntity<Secteur>(secteur, HttpStatus.OK);
    }

}
