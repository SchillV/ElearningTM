package com.tm.elearningtm;

import java.util.ArrayList;
import java.util.List;

public class Curs {
    private int id;
    private String titlu;
    private String descriere;
    private String categorie;
    private Profesor profesor;
    private List<MaterialCurs> materiale;
    private List<SubmisieStudent> submisii;
    private List<Student> studentiInscrisi;

    public Curs(
        int id,
        String titlu,
        String descriere,
        String categorie,
        Profesor profesor
    ) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.categorie = categorie;
        this.profesor = profesor;
        materiale = new ArrayList<MaterialCurs>();
        submisii = new ArrayList<SubmisieStudent>();
        studentiInscrisi = new ArrayList<Student>();
    }

    public void adaugaMaterial(MaterialCurs materialCurs) {

    }

    public void adaugaTema(Tema tema) {

    }

    public void inscrieStudent(Student student) {

    }
}
