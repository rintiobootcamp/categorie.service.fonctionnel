/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.entities.Secteur;
import com.bootcamp.pivots.SecteurWS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bello
 */
public class SecteurHelper {

    public SecteurWS convertSecteurToSecteurWS(Secteur secteur) {
        SecteurWS secteurWS = new SecteurWS();
        secteurWS.setId(secteur.getId());
        secteurWS.setNom(secteur.getNom());
        secteurWS.setDescription(secteur.getDescription());
        secteurWS.setDateCreation(secteur.getDateCreation());
        secteurWS.setDateMiseAJour(secteur.getDateMiseAJour());
        secteurWS.setIcone(secteur.getIcone());

        return secteurWS;
    }

    public Secteur convertSecteurWSToSecteur(SecteurWS secteurWS) {
        Secteur secteur = new Secteur();
        secteur.setId(secteurWS.getId());
        secteur.setNom(secteurWS.getNom());
        secteur.setDescription(secteurWS.getDescription());
        secteur.setDateCreation(secteurWS.getDateCreation());
        secteur.setDateMiseAJour(secteurWS.getDateMiseAJour());
        secteur.setIcone(secteurWS.getIcone());

        return secteur;
    }

    public List<SecteurWS> getListSecteurWS(List<Secteur> listSecteur) {
        List<SecteurWS> listSecteurWs = new ArrayList<>();
        for (Secteur secteur : listSecteur) {
            SecteurWS secteurWS = this.convertSecteurToSecteurWS(secteur);
            listSecteurWs.add(secteurWS);
        }
        return listSecteurWs;
    }

    public List<Secteur> getListSecteur(List<SecteurWS> listSecteurWS) {
        List<Secteur> listSecteur = new ArrayList<>();
        for (SecteurWS secteurWS : listSecteurWS) {
            Secteur secteur = this.convertSecteurWSToSecteur(secteurWS);
            listSecteur.add(secteur);
        }
        return listSecteur;
    }

}
