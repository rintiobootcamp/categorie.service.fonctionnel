package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.models.PilierWs;
import com.bootcamp.commons.ws.models.PilierWss;
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


@RestController("PilierController")
@RequestMapping("/pilier")
@Api(value = "Pilier API", description = "Pilier API")
public class PilierController {

    @Autowired
    PilierService pilierService;
    @Autowired
    HttpServletRequest request;

//    @RequestMapping(method = RequestMethod.POST, value = "/")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Create a new pilier", notes = "Create a new pilier")
//    public ResponseEntity<PilierWs> create(@RequestBody @Valid Pilier pilier) throws SQLException {
//
//        PilierWs pilierWs = new PilierWs();
//        HttpStatus httpStatus = null;
//
//            pilierService.create(pilier);
//            pilierWs.setData(pilier);
//            httpStatus = HttpStatus.OK;
//
//        return new ResponseEntity<>(pilierWs, httpStatus);
//    }


    @RequestMapping(method = RequestMethod.PUT, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a new pilier", notes = "Update a new pilier")
    public ResponseEntity<PilierWs> update(@RequestBody @Valid Pilier pilier) throws SQLException {

        PilierWs pilierWs = new PilierWs();
        HttpStatus httpStatus = null;

            pilierService.update(pilier);
            pilierWs.setData(pilier);
            httpStatus = HttpStatus.OK;
       

        return new ResponseEntity<>(pilierWs, httpStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Delete a pilier", notes = "Delete a pilier")
    public ResponseEntity<PilierWs> delete(@PathVariable(name = "id") int id) throws SQLException {

        PilierWs pilierWs = new PilierWs();
        HttpStatus httpStatus = null;

            Pilier pilier = pilierService.delete(id);
            pilierWs.setData(pilier);
            httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(pilierWs, httpStatus);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a pilier", notes = "Read a pilier")
    public ResponseEntity<PilierWs> read(@PathVariable(name = "id") int id) throws SQLException {

        PilierWs pilierWs = new PilierWs();
        HttpStatus httpStatus = null;

            Pilier pilier = pilierService.read(id);
            pilierWs.setData(pilier);
            httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(pilierWs, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a pilier", notes = "Read a pilier")
    public ResponseEntity<PilierWss> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {

        PilierWss pilierWss = new PilierWss();
        HttpStatus httpStatus = null;

            List<Pilier> piliers = pilierService.read(request);
            pilierWss.setData(piliers);
            httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(pilierWss, httpStatus);
    }
}
