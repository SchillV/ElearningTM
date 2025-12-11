package com.tm.elearningtm;

import java.time.LocalDateTime;

public abstract class Postare {
    private int id;
    private String titlu;
    private LocalDateTime dataCreare;

    public Postare(int id, String titlu) {
        this.id = id;
        this.titlu = titlu;
        dataCreare = LocalDateTime.now();
    }
}
