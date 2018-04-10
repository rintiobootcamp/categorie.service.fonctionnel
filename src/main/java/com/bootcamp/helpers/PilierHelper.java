/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Pilier;
import com.bootcamp.pivots.PilierWS;
import com.bootcamp.pivots.PilierWS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bello
 */
public class PilierHelper {
    public PilierWS convertPilierToPilierWS (Pilier pilier){
        PilierWS pilierWS = new PilierWS();
        AxeHelper helper = new AxeHelper();
        pilierWS.setId(pilier.getId());
        pilierWS.setNom(pilier.getNom());
        pilierWS.setDescription(pilier.getDescription());
        pilierWS.setDateCreation(pilier.getDateCreation());
        pilierWS.setDateMiseAJour(pilier.getDateMiseAJour());
        pilierWS.setAxes(helper.getListAxeWS(pilier.getAxes()));
        
        return pilierWS;
    }
    
    public Pilier convertPilierWSToPilier (PilierWS pilierWS){
        Pilier pilier = new Pilier();
        AxeHelper helper = new AxeHelper();
        pilier.setId(pilierWS.getId());
        pilier.setNom(pilierWS.getNom());
        pilier.setDescription(pilierWS.getDescription());
        pilier.setDateCreation(pilierWS.getDateCreation());
        pilier.setDateMiseAJour(pilierWS.getDateMiseAJour());
        pilier.setAxes(helper.getListAxe(pilierWS.getAxes()));
        
        return pilier;
    }
    
    public List<PilierWS> getListPilierWS(List<Pilier> listPilier) {
        List<PilierWS> listPilierWs = new ArrayList<>();
        for (Pilier pilier : listPilier) {
            PilierWS pilierWS = this.convertPilierToPilierWS(pilier);
            listPilierWs.add(pilierWS);
        }
        return listPilierWs;
    }

    public List<Pilier> getListPilier(List<PilierWS> listPilierWS) {
        List<Pilier> listPilier = new ArrayList<>();
        for (PilierWS pilierWS : listPilierWS) {
            Pilier pilier = this.convertPilierWSToPilier(pilierWS);
            listPilier.add(pilier);
        }
        return listPilier;
    }
}
