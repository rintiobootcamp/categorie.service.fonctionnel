package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.AxeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Ibrahim@abladon
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = AxeController.class, secure = false)
@ContextConfiguration(classes={Application.class})
public class AxeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AxeService axeService;


    @Test
    public void getAxes() throws Exception{
        List<Axe> axes =  loadDataAxeFromJsonFile();
        System.out.println(axes.size());
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(axeService.readAll(Mockito.any(HttpServletRequest.class))).thenReturn(axes);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/axes")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getAxeByIdForController() throws Exception{
        Axe axe = getAxeById(1);

        Mockito.
                when(axeService.read(1)).thenReturn(axe);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/axes/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for get a axe by id in axe controller done *******************");

    }

    @Test
    public void testCreateAxe() throws Exception{
        Axe axe = new Axe();
        axe.setId(10);
        axe.setNom("axe teste");
        Mockito.
        when(!axeService.exist(axe)).thenReturn(false);
        when(axeService.create(axe)).thenReturn(axe);

        RequestBuilder requestBuilder =
                post("/axes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(axe));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for create axe in axe controller done *******************");

    }

    @Test
    public void testUpdateaxe() throws Exception{
        int id = 7;
        Axe axe = new Axe();
        axe.setNom("axe update");
        Mockito.
                when(axeService.exist(id)).thenReturn(true);
        when(axeService.update(axe)).thenReturn(true);

        RequestBuilder requestBuilder =
                put("/axes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(axe));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for update axe in axe controller done *******************");


    }

    @Test
    public void testDeleteAxe() throws Exception{
        int id = 7;
        Axe axe = getAxeById(id);
        Mockito.
                when(axeService.exist(id)).thenReturn(true);
        when(axeService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder =
                delete("/axes/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete axe in axe controller done *******************");


    }

    public static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if(!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public  List<Projet> getProjectsFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

    public List<Secteur> loadDataSecteurFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "secteurs.json");

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