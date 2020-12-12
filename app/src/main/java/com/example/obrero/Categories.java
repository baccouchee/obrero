package com.example.obrero;

import com.google.gson.annotations.SerializedName;

public class Categories {

    private int idCat;
    private String nom;


    @SerializedName("body")


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
