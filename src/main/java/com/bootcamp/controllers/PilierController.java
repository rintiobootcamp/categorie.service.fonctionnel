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
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


@RestController("PilierController")
@RequestMapping("/pilier")
@Api(value = "Pilier API", description = "Pilier API")
public class PilierController {

    @Autowired
    PilierService pilierService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a pilier", notes = "Read a pilier")
    public ResponseEntity<Pilier> read(@PathVariable(name = "id") int id) throws SQLException {

        HttpStatus httpStatus = null;

        Pilier pilier = pilierService.read(id);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<Pilier>(pilier, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "All pilars", notes = "All pilars")
    public ResponseEntity<List<Pilier>> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {

        HttpStatus httpStatus = null;

        List<Pilier> piliers = pilierService.getAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Pilier>>(piliers, httpStatus);
    }
}
