package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.entities.Axe;
import com.bootcamp.services.AxeService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;

/**
 *
 * @author Bello
 */
@RestController("AxeController")
@RequestMapping("/axes")
@Api(value = "Axe API", description = "Axe API")
@CrossOrigin(origins = "*")
public class AxeController {

    private final Logger LOG = LoggerFactory.getLogger(AxeController.class);
    @Autowired
    AxeService axeService;
    @Autowired
    HttpServletRequest request;

    /**
     * Insert the given axe in the database
     *
     * @param axe
     * @return axe
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a axe", notes = "Create a axe")
    public ResponseEntity<Axe> create(@RequestBody @Valid Axe axe) throws Exception {
        LOG.info("Creating a new Axe : {} ", axe);
             axe = axeService.create(axe);
        return new ResponseEntity<>(axe, HttpStatus.OK);
    }

    /**
     * Update the given axe in the database
     *
     * @param axe
     * @return axe
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a axe", notes = "Update a axe")
    public ResponseEntity<Axe> update(@RequestBody Axe axe) throws Exception {
        axe = axeService.create(axe);
        return new ResponseEntity<>(axe, HttpStatus.OK);
    }

    /**
     * Get all the axes in the database
     *
     * @return axes list
     * @throws InvocationTargetException
     * @throws SQLException
     * @throws DatabaseException
     * @throws IllegalAccessException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a axe", notes = "Read a axe")
    public ResponseEntity<List<Axe>> read() throws InvocationTargetException, SQLException, DatabaseException, IllegalAccessException {
        List<Axe> axes = axeService.readAll(request);
        return new ResponseEntity<List<Axe>>(axes, HttpStatus.OK);
    }

    /**
     * Get a axe by its id
     *
     * @param id
     * @return axe
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a specific axe", notes = "Read a specific axe")
    public ResponseEntity<Axe> read(@PathVariable(name = "id") int id) throws SQLException {

        Axe axe = axeService.read(id);
        return new ResponseEntity<Axe>(axe, HttpStatus.OK);
    }

    /**
     * Delete a axe by its id
     *
     * @param id
     * @return
     * @throws InvocationTargetException
     * @throws SQLException
     * @throws DatabaseException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Delete a axe", notes = "Delete a axe")
    public ResponseEntity<Boolean> delete(@PathVariable int id) throws InvocationTargetException, SQLException, DatabaseException, IllegalAccessException {
        axeService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * COunt all the axes in the database
     *
     * @return count
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Count axes", notes = "Count axes")
    public ResponseEntity<HashMap<String, Integer>> countAxes() throws SQLException {

        int count = axeService.getCountAxes();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(CommonsWsConstants.MAP_COUNT_KEY, count);

        return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
    }

}
