package com.tm.elearningtm.classes;

import android.os.Build;

import com.tm.elearningtm.data.AppData;

import java.time.LocalDateTime;

public class SubmisieStudent {
    private final int id;
    private final Student student;
    private final String continut;
    private final LocalDateTime dataSubmisie;
    private Double nota;

    public SubmisieStudent(Student student, String continut) {
        this.id = AppData.generateSubID();
        this.student = student;
        this.continut = continut;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.dataSubmisie = LocalDateTime.now();
        } else {
            this.dataSubmisie = null;
        }
        this.nota = null;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public String getContinut() { return continut; }
    public LocalDateTime getDataSubmisie() { return dataSubmisie; }
    public Double getNota() { return nota; }
}
