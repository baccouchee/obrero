package com.example.obrero;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prestation {

    private int idPres;
    private String NomP;
    private String description;
    private float tarif;
    private  int idConnecter;

    private int nbnote;
    private String photo;
    private String nomC;
    private int idU;
    private String adresse;

    public int getIdConnecter() {
        return idConnecter;
    }

    public void setIdConnecter(int idConnecter) {
        this.idConnecter = idConnecter;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    @SerializedName("body")
    public int getIdPres() {
        return idPres;
    }

    public void setIdPres(int idPres) {
        this.idPres = idPres;
    }

    public String getNomP() {
        return NomP;
    }

    public void setNomP(String NomP) {
        this.NomP = NomP;
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
