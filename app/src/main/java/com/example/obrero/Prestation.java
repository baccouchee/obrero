package com.example.obrero;

import com.google.gson.annotations.SerializedName;

public class Prestation {

    private int idPres;
    private String Nom;
    private String description;
    private float tarif;
    private int idU;
    private int nbnote;
    private String photo;
    private String nomC;

    @SerializedName("body")
    public int getIdPres() {
        return idPres;
    }

    public void setIdPres(int idPres) {
        this.idPres = idPres;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = Nom;
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

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getNbnote() {
        return nbnote;
    }

    public void setNbnote(int nbnote) {
        this.nbnote = nbnote;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }
}
