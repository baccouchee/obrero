package com.example.obrero.Model;

import com.google.gson.annotations.SerializedName;

public class Categories {

    private int idCat;
    private String nom;
    private String icon;

    @SerializedName("body")

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
