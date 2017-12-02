package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Pilier;
import com.bootcamp.services.PilierService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


@RestController("PilierController")
@RequestMapping("/piliers")
@Api(value = "Pilier API", description = "Pilier API")
public class PilierController {

    @Autowired
    PilierService pilierService;
    @Autowired
    HttpServletRequest request;


    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a pilier", notes = "Create a pilier")
    public ResponseEntity<Pilier> create(@RequestBody @Valid Pilier pilier) throws SQLException {

        pilier = pilierService.create(pilier);
        return new ResponseEntity<Pilier>(pilier, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a pilier", notes = "Read a pilier")
    public ResponseEntity<Pilier> read(@PathVariable int id) throws SQLException {

        HttpStatus httpStatus = null;

        Pilier pilier = pilierService.read(id);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<Pilier>(pilier, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "liste des  pilars", notes = "liste des piliers")
    public ResponseEntity<List<Pilier>> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {

        HttpStatus httpStatus = null;

        List<Pilier> piliers = pilierService.getAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Pilier>>(piliers, httpStatus);
    }
}
