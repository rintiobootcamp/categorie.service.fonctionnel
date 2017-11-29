package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.commons.ws.models.Error;
import com.bootcamp.entities.Axe;
import com.bootcamp.services.AxeService;
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
import java.util.HashMap;
import java.util.List;


@RestController("AxeController")
@RequestMapping("/axe")
@Api(value = "Axe API", description = "Axe API")
public class AxeController {

    @Autowired
    AxeService axeService;
    @Autowired
    HttpServletRequest request;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a axe", notes = "Read a axe")
    public ResponseEntity<List<Axe>> read() throws InvocationTargetException, SQLException, DatabaseException, IllegalAccessException {
        List<Axe> axes = axeService.read(request);
        return new ResponseEntity<List<Axe>>(axes, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Count axes", notes = "Count axes")
    public ResponseEntity<HashMap<String,Integer>> countAxes() throws SQLException {
        
        int count = axeService.getCountAxes();
        HashMap<String,Integer> map = new HashMap<>();
        map.put(CommonsWsConstants.MAP_COUNT_KEY, count);
        

        return new ResponseEntity<HashMap<String,Integer>>(map, HttpStatus.OK);
    }

}
