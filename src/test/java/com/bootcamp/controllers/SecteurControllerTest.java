package com.bootcamp.controllers;


import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.SecteurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SecteurController.class, secure = false)
@ContextConfiguration(classes={Application.class})
public class SecteurControllerTest {
    private final Logger LOG = LoggerFactory.getLogger(SecteurControllerTest.class);


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SecteurService secteurService;


    @Test
    public void getSecteurs() throws Exception{
        LOG.info("Testing get all secteurs test method ");
        List<Secteur> secteurs =  loadDataSecteurFromJsonFile();
        System.out.println(secteurs.size());
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(secteurService.read(Mockito.any(HttpServletRequest.class))).thenReturn(secteurs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/secteurs")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        LOG.info(response.getContentAsString());
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getSecteurById() throws Exception{

        LOG.info("Testing get secteur by id  method ");

        int id = 1;
        Secteur secteur = getSecteurById(id);

        Mockito.
                when(secteurService.read(id)).thenReturn(secteur);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/secteurs/{id}",id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        LOG.info(response.getContentAsString());
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());


    }
    
    @Test
    public void createSecteur() throws Exception{
        Secteur secteur = new Secteur();
        secteur.setId(10);
        secteur.setNom("secteur teste");
        Mockito.
                when(!secteurService.exist(secteur)).thenReturn(false);
        when(secteurService.create(secteur)).thenReturn(secteur);

        RequestBuilder requestBuilder =
                post("/secteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(secteur));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for create secteur in secteur controller done *******************");
    }

    @Test
    public void testUpdatesecteur() throws Exception{
        int id = 7;
        Secteur secteur = new Secteur();
        secteur.setNom("secteur update");
        Mockito.
                when(secteurService.exist(id)).thenReturn(true);
        when(secteurService.update(secteur)).thenReturn(true);

        RequestBuilder requestBuilder =
                put("/secteurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(secteur));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for update secteur in secteur controller done *******************");


    }

    @Test
    public void deleteSecteur() throws Exception{
        int id = 7;
        Mockito.
                when(secteurService.exist(id)).thenReturn(true);
        when(secteurService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder =
                delete("/secteurs/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete secteur in secteur controller done *******************");


    }

    private static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item->item.getId()==id).findFirst().get();

        return secteur;
    }

    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if(!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
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
}
