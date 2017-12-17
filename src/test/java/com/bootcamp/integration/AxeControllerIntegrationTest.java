package com.bootcamp.integration;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.AxeService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.jayway.restassured.response.Response;


import org.junit.Before;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;


/**
 * <h2> The integration test for Axe controller</h2>
 * <p>
 * In this test class,
 * the methods :
 * <ul>
 * <li>create a axe </li>
 * <li>get one axe by it's id</li>
 * <li>get all axe</li>
 * <li>And update a axe have been implemented</li>
 * </ul>
 * before  getting started , make sure , the categorie fonctionnel service is deploy and running as well.
 * you can also test it will the online ruuning service
 * As this test interact directly with the local database, make sure that the specific database has been created
 * and all it's tables.
 * If you have data in the table,make sure that before creating a data with it's id, do not use
 * an existing id.
 * </p>
 */
public class AxeControllerIntegrationTest {
    private static Logger logger = LogManager.getLogger(AxeControllerIntegrationTest.class);

    /**
     *The Base URI of categorie fonctionnal service,
     * it can be change with the online URIof this service.
     */
    private String BASE_URI = "http://localhost:8082/categorie";

    /**
     * The path of the Axe controller, according to this controller implementation
     */
    private String AXE_PATH ="/axes";

    /**
     * This ID is initialize for create , getById, and update method,
     * you have to change it if you have a save data on this ID otherwise
     * a error or conflit will be note by your test.
     */
    private int axeId = 0;


   /* @BeforeTest
    public void count() throws Exception{
       int totalData = new AxeService().getCountAxes();
       axeId=totalData;
       logger.info( axeId );
   }*/


    /**
     * This method create a new axe with the given id
     * @see Axe#id
     * <b>you have to chenge the name of
     * the axe if this name already exists in the database
     * @see Axe#getNom()
     * else, the axe  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 0, groups = {"AxeTest"})
    public void createAxe() throws Exception{
        String createURI = BASE_URI+AXE_PATH;
        Axe axe = getAxeById( 1 );
        axe.setId( axeId );
        axe.setNom( "axe test after the doc" );
        axe.setSecteurs( null );
        Gson gson = new Gson();
        String axeData = gson.toJson( axe );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(axeData)
                .expect()
                .when()
                .post(createURI);

        axeId = gson.fromJson( response.getBody().print(),Axe.class ).getId();

        logger.debug( axeId );
                logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;



    }

    /**
     * This method get a axe with the given id
     * @see Axe#id
     * <b>
     *     If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"AxeTest"})
    public void getAxeById() throws Exception{

        String getAxeById = BASE_URI+AXE_PATH+"/"+axeId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAxeById);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;


    }

  
    /**
     * Update a axe with the given ID
     * <b>
     *     the axe must exist in the database
     *     </b>
     * Note that this method will be the third to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 2, groups = {"AxeTest"})
    public void updateAxe() throws Exception{
        String updateURI = BASE_URI+AXE_PATH;
        Axe axe = getAxeById( 1 );
        axe.setId( axeId );
        axe.setNom( "update axe to final test integration v2" );
        axe.setSecteurs( null );
        Gson gson = new Gson();
        String axeData = gson.toJson( axe );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(axeData)
                .expect()
                .when()
                .put(updateURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;



    }

    /**
     * Get All the axes in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 3, groups = {"AxeTest"})
    public void getAllAxes()throws Exception{
        String getAllAxeURI = BASE_URI+AXE_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllAxeURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }


    /**
     * Delete a axe for the given ID
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */
    @Test(priority = 4, groups = {"AxeTest"})
    public void deleteAxe() throws Exception{

        String deleteAxeUI = BASE_URI+AXE_PATH+"/"+axeId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteAxeUI);

        Assert.assertEquals(response.statusCode(), 200) ;


    }



    /**
     * Convert a relative path file into a File Object type
     * @param relativePath
     * @return  File
     * @throws Exception
     */
    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    /**
     * Convert a projets json data to a projet objet list
     * this json file is in resources
     * @return a list of projet in this json file
     * @throws Exception
     */
    public List<Projet> getProjectsFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

    /**
     * Convert a secteurs json data to a secteur objet list
     * this json file is in resources
     * @return a list of secteur in this json file
     * @throws Exception
     */
    public List<Secteur> loadDataSecteurFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "secteurs.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Secteur>>() {
        }.getType();
        List<Secteur> secteurs = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return secteurs;
    }


    /**
     * Get on secteur by a given ID from the List of secteurs
     * @param id
     * @return
     * @throws Exception
     */
    private Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();

        return secteur;
    }

    /**
     * Get on axe by a given ID from the List of axes
     * @param id
     * @return
     * @throws Exception
     */
    public Axe getAxeById(int id) throws Exception {
        List<Axe> axes = loadDataAxeFromJsonFile();
        Axe axe = axes.stream().filter(item -> item.getId() == id).findFirst().get();

        return axe;
    }


    /**
     * Convert a axes json data to a axe objet list
     * this json file is in resources
     * @return a list of axe in this json file
     * @throws Exception
     */
    public List<Axe> loadDataAxeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "axes.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Axe>>() {
        }.getType();
        List<Axe> axes = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        for (int i = 0; i < axes.size(); i++) {
            Axe axe = axes.get(i);
            List<Secteur> secteurs = new LinkedList();
            switch (i) {
                case 0:
                    secteurs.add(getSecteurById(8));
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    secteurs.add(getSecteurById(1));
                    secteurs.add(getSecteurById(2));
                    secteurs.add(getSecteurById(5));
                    secteurs.add(getSecteurById(9));
                    break;
                case 4:
                    secteurs.add(getSecteurById(3));
                    break;
                case 5:
                    secteurs.add(getSecteurById(8));
                    break;
                case 6:
                    secteurs.add(getSecteurById(6));
                    break;
            }
            axe.setSecteurs(secteurs);
        }

        return axes;
    }


    /**
     * Convert a piliers json data to a pilier objet list
     * this json file is in resources
     * @return a list of pilier in this json file
     * @throws Exception
     */
    public List<Pilier> loadDataPilierFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "piliers.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Pilier>>() {
        }.getType();
        List<Pilier> piliers = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);
        //List<Axe> axes = axeRepository.findAll();
        for (int i = 0; i < piliers.size(); i++) {
            List<Axe> axes = new LinkedList();
            Pilier pilier = piliers.get(i);
            switch (i) {
                case 0:
                    axes.add(getAxeById(1));
                    axes.add(getAxeById(2));
                    break;
                case 1:
                    axes.add(getAxeById(3));
                    axes.add(getAxeById(4));
                    axes.add(getAxeById(5));
                    break;
                case 2:
                    axes.add(getAxeById(6));
                    axes.add(getAxeById(7));
                    break;
            }
            pilier.setAxes(axes);
        }

        return piliers;
    }

}