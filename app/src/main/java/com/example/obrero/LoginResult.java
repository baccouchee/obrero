package com.example.obrero;
import com.google.gson.annotations.SerializedName;
public class LoginResult {

    private int idUser;
private int id;



    private String nom;

    private String NomP;

    private String adresse;

    private String password;

    private String email;

    public String getNomP() {
        return NomP;
    }

    public void setNomP(String NomP) {
        NomP = NomP;
    }

    @SerializedName("body")


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
