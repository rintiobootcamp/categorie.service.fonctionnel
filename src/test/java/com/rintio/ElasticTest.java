package com.rintio;


import com.bootcamp.crud.*;
import com.bootcamp.entities.*;
import com.bootcamp.services.AxeService;
import com.rintio.elastic.client.ElasticClient;
import com.rintio.elastic.elastic.ElasticSearchOutput;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ElasticTest {
    private final Logger LOG = LoggerFactory.getLogger(ElasticTest.class);
    //@Test
    public void createAxeIndex() throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Axe>  axes = AxeCRUD.read();
        for(Axe axe:axes){
            System.out.println("creation de "+axe.getId());
            elasticClient.creerIndexObjectNative("axes","axe",axe,axe.getId());
            System.out.println(axe.getId()+" cree avec succes");
        }
    }


    @Test
    public void createIndexAxe()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Axe> axes = AxeCRUD.read();
        for (Axe axe : axes){
            elasticClient.creerIndexObjectNative("axes","axe",axe,axe.getId());
            LOG.info("axe "+axe.getNom()+" created");
        }
    }

     @Test
    public void createIndexPilier()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Pilier> piliers = PilierCRUD.read();
        for (Pilier pilier : piliers){
            elasticClient.creerIndexObjectNative("piliers","pilier",pilier,pilier.getId());
            LOG.info("pilier "+pilier.getNom()+" created");
        }
    }

    @Test
    public void createIndexSecteur()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Secteur> secteurs = SecteurCRUD.read();
        for (Secteur secteur : secteurs){
            elasticClient.creerIndexObjectNative("secteurs","secteur",secteur,secteur.getId());
            LOG.info("secteur "+secteur.getNom()+" created");
        }
    }

   // @Test
    public void createIndexProjet()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Projet> projets = ProjetCRUD.read();
        for (Projet projet : projets){
            elasticClient.creerIndexObjectNative("projets","projet",projet,projet.getId());
            LOG.info("projet "+projet.getNom()+" created");
        }
    }

   // @Test
    public void createIndexCensure()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Censure> censures = CensureCRUD.read();
        for (Censure obj : censures){
            elasticClient.creerIndexObject("censures","censure",obj,obj.getId());
            LOG.info("censure "+obj.getMessage()+" created");
        }
    }


  //  @Test
    public void getAllAxe()throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
//        List<Object> objects = elasticClient.getAllObject("axes");
//        ModelMapper modelMapper = new ModelMapper();
//        List<Axe> axes = new ArrayList<>();
//        for(Object obj:objects){
//            Axe axe = modelMapper.map(obj,Axe.class);
//            LOG.info("axe  "+axe.getNom()+" created");
//
//        }
        AxeService axeService = new AxeService();
        List<Axe> axes = axeService.getAllAxes();
        LOG.info("axe  "+axes.get(0).getNom()+" created");
    }
}
