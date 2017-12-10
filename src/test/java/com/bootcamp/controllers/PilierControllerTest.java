package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.controllers.PilierController;

import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.PilierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
/*
import org.junit.runner.RunWith;
*/
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
 *
 * Created by darextossa on 12/5/17.
 */

//@TestExecutionListeners(MockitoTestExecutionListener.class)
//@ActiveProfiles({"controller-unit-test"})
//@SpringBootApplication(scanBasePackages={"com.bootcamp"})
//@ContextConfiguration(classes = ControllerUnitTestConfig.class)
//@WebMvcTest(value = PilierController.class, secure = false, excludeAutoConfiguration = {HealthIndicatorAutoConfiguration.class, HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class, DataSourceAutoConfiguration.class})

@RunWith(SpringRunner.class)
@WebMvcTest(value = PilierController.class, secure = false)
@ContextConfiguration(classes={Application.class})
public class PilierControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PilierService pilierService;


    @Test
    public void getAllPilier() throws Exception{
        List<Pilier> piliers =  loadDataPilierFromJsonFile();
        //Mockito.mock(PilierCRUD.class);
        Mockito.
                when(pilierService.getAll()).thenReturn(piliers);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/piliers")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());
        System.out.println("*********************************Test for get all pilar  in secteur controller done *******************");


    }

    @Test
    public void getPilierById() throws Exception{
        int id = 1;
        Pilier pilier = getPilierDataById(id);
        Mockito.
                when(pilierService.read(id)).thenReturn(pilier);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/piliers/{id}",id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for get pilar by id in pilar controller done *******************");

    }


    @Test
    public void createPilier() throws Exception{
        List<Pilier> piliers =  loadDataPilierFromJsonFile();
        Pilier pilier = piliers.get(0);

        Mockito.when(pilierService.exist(pilier)).thenReturn(false);
                when(pilierService.create(pilier)).thenReturn(pilier);

        RequestBuilder requestBuilder =
                post("/piliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(pilier));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        System.out.println(response.getContentAsString());

        System.out.println("*********************************Test for create pilier in pilier controller done *******************");
    }


    @Test
    public void updatePilier() throws Exception{
        List<Pilier> piliers =  loadDataPilierFromJsonFile();
        Pilier pilier = piliers.get(0);
        pilier.setNom("pilier update");
        Mockito.
                when(pilierService.exist(pilier.getId())).thenReturn(true);
        when(pilierService.update(pilier)).thenReturn(true);

        RequestBuilder requestBuilder =
                put("/piliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(pilier));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for update pilier in pilier controller done *******************");


    }

    @Test
    public void deletePilier() throws Exception{
        int id = 7;
        Mockito.
                when(pilierService.exist(id)).thenReturn(true);
              when(pilierService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder =
                delete("/piliers/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete pilier in pilier controller done *******************");


    }



    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if(!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    private static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Projet> getProjectsFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

    public List<Secteur> loadDataSecteurFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "secteurs.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Secteur>>() {
        }.getType();
        List<Secteur> secteurs = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return secteurs;
    }

    private Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item->item.getId()==id).findFirst().get();

        return secteur;
    }

    public Axe getAxeById(int id) throws Exception {
        List<Axe> axes = loadDataAxeFromJsonFile();
        Axe axe = axes.stream().filter(item->item.getId()==id).findFirst().get();

        return axe;
    }

    public Pilier getPilierDataById(int id) throws Exception {
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        Pilier pilier = piliers.stream().filter(item->item.getId()==id).findFirst().get();

        return pilier;
    }


    public List<Axe> loadDataAxeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "axes.json");

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

    public List<Pilier> loadDataPilierFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "piliers.json");

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
