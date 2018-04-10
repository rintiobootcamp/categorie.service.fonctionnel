/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.entities.Axe;
import com.bootcamp.pivots.AxeWS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bello
 */
public class AxeHelper {

    public AxeWS convertAxeToAxeWS(Axe axe) {
        AxeWS axeWS = new AxeWS();
        SecteurHelper helper = new SecteurHelper();
        axeWS.setId(axe.getId());
        axeWS.setNom(axe.getNom());
        axeWS.setDescription(axe.getDescription());
        axeWS.setDateCreation(axe.getDateCreation());
        axeWS.setDateMiseAJour(axe.getDateMiseAJour());
        axeWS.setTitre(axe.getTitre());
        axeWS.setTitreFocus(axe.getTitreFocus());
        axeWS.setDescriptionFocus(axe.getDescriptionFocus());
        axeWS.setSecteurs(helper.getListSecteurWS(axe.getSecteurs()));

        return axeWS;
    }

    public Axe convertAxeWSToAxe(AxeWS axeWS) {
        Axe axe = new Axe();
        SecteurHelper helper = new SecteurHelper();
        axe.setId(axeWS.getId());
        axe.setNom(axeWS.getNom());
        axe.setDescription(axeWS.getDescription());
        axe.setDateCreation(axeWS.getDateCreation());
        axe.setDateMiseAJour(axeWS.getDateMiseAJour());
        axe.setTitre(axeWS.getTitre());
        axe.setDescriptionFocus(axeWS.getDescriptionFocus());
        axe.setTitreFocus(axeWS.getTitreFocus());
        axe.setSecteurs(helper.getListSecteur(axeWS.getSecteurs()));

        return axe;
    }

    public List<AxeWS> getListAxeWS(List<Axe> listAxe) {
        List<AxeWS> listAxeWs = new ArrayList<>();
        for (Axe axe : listAxe) {
            AxeWS axeWS = this.convertAxeToAxeWS(axe);
            listAxeWs.add(axeWS);
        }
        return listAxeWs;
    }

    public List<Axe> getListAxe(List<AxeWS> listAxeWS) {
        List<Axe> listAxe = new ArrayList<>();
        for (AxeWS axeWS : listAxeWS) {
            Axe axe = this.convertAxeWSToAxe(axeWS);
            listAxe.add(axe);
        }
        return listAxe;
    }

}
