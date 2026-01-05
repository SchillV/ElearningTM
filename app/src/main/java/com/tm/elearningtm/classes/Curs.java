package com.tm.elearningtm.classes;

import com.tm.elearningtm.data.AppData;

import java.util.ArrayList;
import java.util.List;

public class Curs {
    private final int id;
    private String titlu;
    private String descriere;
    private String categorie;
    private final Profesor profesor;
    private final List<MaterialCurs> materiale;
    private final List<Tema> teme;
    private final List<Student> studentiInscrisi;

    public Curs(
            String titlu,
            String descriere,
            String categorie,
            Profesor profesor
    ) {
        this.id = AppData.generateCursID();
        this.titlu = titlu;
        this.descriere = descriere;
        this.categorie = categorie;
        this.profesor = profesor;
        materiale = new ArrayList<>();
        teme = new ArrayList<>();
        studentiInscrisi = new ArrayList<>();
    }

    public void adaugaMaterial(MaterialCurs materialCurs) {
        materiale.add(materialCurs);
    }

    public void adaugaTema(Tema tema) {
        teme.add(tema);
    }

    public void inscrieStudent(Student student) {
        studentiInscrisi.add(student);
    }

    public int getId() { return id; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public String getCategorie() { return categorie; }
    public Profesor getProfesor() { return profesor; }
    public Tema getTemaById(int id) {
        for(Tema t:this.teme){
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    public List<MaterialCurs> getMateriale() { return materiale; }
    public List<Student> getStudentiInscrisi() { return studentiInscrisi; }

    public void setTitlu(String titlu) { this.titlu = titlu; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
}
