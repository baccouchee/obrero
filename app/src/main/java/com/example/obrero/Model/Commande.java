package com.example.obrero.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Commande {

    int idC;
    int idUs;
    int idP;
    Date date;
    String heure;
    String NomP;
    String adresse;
    String photo;
    String description;
    int idConnecter;
    float tarif;



    @SerializedName("body")


    public int getIdConnecter() {
        return idConnecter;
    }

    public void setIdConnecter(int idConnecter) {
        this.idConnecter = idConnecter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdUs() {
        return idUs;
    }

    public void setIdUs(int idUs) {
        this.idUs = idUs;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getNomP() {
        return NomP;
    }

    public void setNomP(String nomP) {
        NomP = nomP;
    }
}
