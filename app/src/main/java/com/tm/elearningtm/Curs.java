package com.tm.elearningtm;

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
    private final List<SubmisieStudent> submisii;
    private final List<Student> studentiInscrisi;

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
        materiale = new ArrayList<>();
        submisii = new ArrayList<>();
        teme = new ArrayList<>();
        studentiInscrisi = new ArrayList<>();
    }

    public void adaugaMaterial(MaterialCurs materialCurs) {
        materiale.add(materialCurs);
        System.out.println("Material \"" + materialCurs.getTitlu() + "\" adăugat la curs.");
    }

    public void adaugaTema(Tema tema) {
        teme.add(tema);
        System.out.println("Tema \"" + tema.getTitlu() + "\" adăugată la curs.");
    }

    public void inscrieStudent(Student student) {
        studentiInscrisi.add(student);
        System.out.println(student.getNume() + " a fost înscris la cursul \"" + titlu + "\".");
    }

    public int getId() { return id; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public String getCategorie() { return categorie; }
    public Profesor getProfesor() { return profesor; }
    public List<MaterialCurs> getMateriale() { return materiale; }
    public List<SubmisieStudent> getSubmisii() { return submisii; }
    public List<Student> getStudentiInscrisi() { return studentiInscrisi; }

    public void setTitlu(String titlu) { this.titlu = titlu; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
}
