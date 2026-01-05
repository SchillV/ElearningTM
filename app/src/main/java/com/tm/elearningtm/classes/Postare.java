package com.tm.elearningtm.classes;

import android.os.Build;

import java.time.LocalDateTime;

public abstract class Postare {
    private final int id;
    private String titlu;
    private String descriere;
    private final LocalDateTime dataCreare;

    public Postare(int id, String titlu, String descriere) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.dataCreare = LocalDateTime.now();
        } else {
            this.dataCreare = null;
        }
    }

    public int getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
