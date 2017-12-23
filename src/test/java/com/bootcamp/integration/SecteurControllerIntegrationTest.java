package com.bootcamp.integration;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * <h2> The integration test for Secteur controller</h2>
 * <p>
 * In this test class,
 * the methods :
 * <ul>
 * <li>create a secteur </li>
 * <li>get one secteur by it's id</li>
 * <li>get all secteur</li>
 * <li>And update a secteur have been implemented</li>
 * </ul>
 * before  getting started , make sure , the categorie fonctionnel service is deploy and running as well.
 * you can also test it will the online ruuning service
 * As this test interact directly with the local database, make sure that the specific database has been created
 * and all it's tables.
 * If you have data in the table,make sure that before creating a data with it's id, do not use
 * an existing id.
 * </p>
 */
public class SecteurControllerIntegrationTest {
    private static Logger logger = LogManager.getLogger(SecteurControllerIntegrationTest.class);

    /**
     *The Base URI of categorie fonctionnal service,
     * it can be change with the online URIof this service.
     */
    private String BASE_URI = "http://localhost:8082/categorie";

    /**
     * The path of the Secteur controller, according to this controller implementation
     */
    private String SECTEUR_PATH ="/secteurs";

    /**
     * This ID is initialize for create , getById, and update method,
     * you have to change it if you have a save data on this ID otherwise
     * a error or conflit will be note by your test.
     */
    private int secteurId = 0;


    /**
     * This method create a new secteur with the given id
     * @see Secteur#id
     * <b>you have to chenge the name of
     * the secteur if this name already exists in the database
     * @see Secteur#getNom()
     * else, the secteur  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 0, groups = {"SecteurTest"})
    public void createSecteur() throws Exception{
        String createURI = BASE_URI+SECTEUR_PATH;
        Secteur secteur = getSecteurById( 1 );
        secteur.setId( secteurId );
        secteur.setNom( "secteur change in" );
        Gson gson = new Gson();
        String secteurData = gson.toJson( secteur );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(secteurData)
                .expect()
                .when()
                .post(createURI);

        secteurId = gson.fromJson( response.getBody().print(),Secteur.class ).getId();

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }

    /**
     * This method get a secteur with the given id
     * @see Secteur#id
     * <b>
     *     If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"SecteurTest"})
    public void getSecteurById() throws Exception{

        String getSecteurById = BASE_URI+SECTEUR_PATH+"/"+secteurId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getSecteurById);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;


    }

    /**
     * Update a secteur with the given ID
     * <b>
     *     the secteur must exist in the database
     *     </b>
     * Note that this method will be the third to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 2, groups = {"SecteurTest"})
    public void updateSecteur() throws Exception{
        String updateURI = BASE_URI+SECTEUR_PATH;
        Secteur secteur = getSecteurById( 1 );
        secteur.setId( secteurId );
        secteur.setNom( "update secteur during intallation" );
        secteur.setAxe( null );
        Gson gson = new Gson();
        String secteurData = gson.toJson( secteur );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(secteurData)
                .expect()
                .when()
                .put(updateURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;



    }

    /**
     * Get All the secteurs in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 3, groups = {"SecteurTest"})
    public void getAllSecteurs()throws Exception{
        String getAllSecteurURI = BASE_URI+SECTEUR_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllSecteurURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }


    /**
     * Delete a secteur for the given ID
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */
    @Test(priority = 4, groups = {"SecteurTest"})
    public void deleteSecteur() throws Exception{

        String deleteSecteurUI = BASE_URI+SECTEUR_PATH+"/"+secteurId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteSecteurUI);

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

    private Pilier getPilierById(int id) throws Exception {
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        Pilier pilier = piliers.stream().filter(item -> item.getId() == id).findFirst().get();

        return pilier;
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
     * Get on secteur by a given ID from the List of secteurs
     * @param id
     * @return
     * @throws Exception
     */
    public Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();

        return secteur;
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
