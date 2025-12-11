package com.tm.elearningtm;

import java.time.LocalDateTime;

public class SubmisieStudent {
    private int id;
    private Student student;
    private Tema tema;
    private String continut;
    private LocalDateTime dataSubmisie;
    private Double nota;

    public SubmisieStudent(int id, Student student, Tema tema, String continut) {
        this.id = id;
        this.student = student;
        this.tema = tema;
        this.continut = continut;
        this.dataSubmisie = LocalDateTime.now();
        this.nota = null;
    }

    public void seteazaNota(double nota) {
        this.nota = nota;
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Tema getTema() { return tema; }
    public String getContinut() { return continut; }
    public LocalDateTime getDataSubmisie() { return dataSubmisie; }
    public Double getNota() { return nota; }
}
