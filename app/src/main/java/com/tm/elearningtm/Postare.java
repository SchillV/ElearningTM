package com.tm.elearningtm;

import java.time.LocalDateTime;

public abstract class Postare {
    private final int id;
    private String titlu;
    private final LocalDateTime dataCreare;

    public Postare(int id, String titlu) {
        this.id = id;
        this.titlu = titlu;
        this.dataCreare = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }
}
