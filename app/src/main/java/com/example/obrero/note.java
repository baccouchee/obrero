package com.example.obrero;

import com.google.gson.annotations.SerializedName;

public class note {
    private int idC;
    private float note;
    private int idU;
    private int idP;


    @SerializedName("body")


    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }
}
