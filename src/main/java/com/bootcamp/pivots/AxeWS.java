/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.pivots;

import java.util.List;

/**
 *
 * @author Bello
 */
public class AxeWS {

    private int id;
    private String nom;
    private String description;
    private String titre;
    private String titreFocus;
    private String descriptionFocus;
    private long dateCreation;
    private long dateMiseAJour;
    private List<SecteurWS> secteurs;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the titreFocus
     */
    public String getTitreFocus() {
        return titreFocus;
    }

    /**
     * @param titreFocus the titreFocus to set
     */
    public void setTitreFocus(String titreFocus) {
        this.titreFocus = titreFocus;
    }

    /**
     * @return the descriptionFocus
     */
    public String getDescriptionFocus() {
        return descriptionFocus;
    }

    /**
     * @param descriptionFocus the descriptionFocus to set
     */
    public void setDescriptionFocus(String descriptionFocus) {
        this.descriptionFocus = descriptionFocus;
    }

    /**
     * @return the dateCreation
     */
    public long getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the dateMiseAJour
     */
    public long getDateMiseAJour() {
        return dateMiseAJour;
    }

    /**
     * @param dateMiseAJour the dateMiseAJour to set
     */
    public void setDateMiseAJour(long dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    /**
     * @return the secteurs
     */
    public List<SecteurWS> getSecteurs() {
        return secteurs;
    }

    /**
     * @param secteurs the secteurs to set
     */
    public void setSecteurs(List<SecteurWS> secteurs) {
        this.secteurs = secteurs;
    }

}
