package com.bootcamp.servcice;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.PilierCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.bootcamp.pivots.PilierWS;
import com.bootcamp.services.PilierService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

//import org.junit.Test;
//import org.testng.annotations.Test;

/**
 * Created by darextossa on 12/9/17.
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = PilierService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(PilierCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class PilierServiceTest {


    @InjectMocks
    private PilierService pilierService;

    @Test
    public void getAllPilier() throws Exception {
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        PowerMockito.mockStatic(PilierCRUD.class);
        Mockito.
                when(PilierCRUD.read()).thenReturn(piliers);
        List<PilierWS> resultPiliers = pilierService.getAll();
        Assert.assertEquals(piliers.size(), resultPiliers.size());

    }


    @Test
    public void create() throws Exception{
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        Pilier pilier = piliers.get(1);

        PowerMockito.mockStatic(PilierCRUD.class);
        Mockito.
                when(PilierCRUD.create(pilier)).thenReturn(true);
    }

    @Test
    public void delete() throws Exception{
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        Pilier pilier = piliers.get(1);

        PowerMockito.mockStatic(PilierCRUD.class);
        Mockito.
                when(PilierCRUD.delete(pilier)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Pilier> piliers = loadDataPilierFromJsonFile();
        Pilier pilier = piliers.get(1);

        PowerMockito.mockStatic(PilierCRUD.class);
        Mockito.
                when(PilierCRUD.update(pilier)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Projet> getProjectsFromJson() throws Exception {
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
        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();

        return secteur;
    }

    public Axe getAxeById(int id) throws Exception {
        List<Axe> axes = loadDataAxeFromJsonFile();
        Axe axe = axes.stream().filter(item -> item.getId() == id).findFirst().get();

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