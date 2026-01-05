package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "profesor_id",
                onDelete = ForeignKey.CASCADE // delete the courses associated to a profesor
        )
)
public class Curs {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titlu;
    private String descriere;
    private String categorie;

    @ColumnInfo(name = "profesor_id", index = true)
    private int profesorId;

    // These are loaded separately from the database
    @Ignore
    private User profesor;

    @Ignore
    private List<MaterialCurs> materiale;

    @Ignore
    private List<Tema> teme;

    @Ignore
    private List<User> studentiInscrisi;

    // Constructor for Room
    public Curs(int id, String titlu, String descriere, String categorie, int profesorId) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.categorie = categorie;
        this.profesorId = profesorId;
    }

    // Convenience constructor with Profesor object
    @Ignore
    public Curs(int id, String titlu, String descriere, String categorie, User profesor) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.categorie = categorie;
        this.profesor = profesor;
        this.profesorId = profesor != null ? profesor.getId() : 0;
        this.materiale = new ArrayList<>();
        this.teme = new ArrayList<>();
        this.studentiInscrisi = new ArrayList<>();
    }

    // TODO: call this after loading from database
    public void initializeLists() {
        if (materiale == null) materiale = new ArrayList<>();
        if (teme == null) teme = new ArrayList<>();
        if (studentiInscrisi == null) studentiInscrisi = new ArrayList<>();
    }
    public void adaugaMaterial(MaterialCurs materialCurs) {
        if (materiale == null) materiale = new ArrayList<>();
        materiale.add(materialCurs);
    }

    public void adaugaTema(Tema tema) {
        if (teme == null) teme = new ArrayList<>();
        teme.add(tema);
    }

    public void inscrieStudent(User student) {
        if (studentiInscrisi == null) studentiInscrisi = new ArrayList<>();
        studentiInscrisi.add(student);
    }

    public Tema getTemaById(int id) {
        if (teme == null) return null;
        for(Tema t : teme) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getProfesorId() { return profesorId; }
    public void setProfesorId(int profesorId) { this.profesorId = profesorId; }

    public User getProfesor() { return profesor; }
    public void setProfesor(User profesor) {
        this.profesor = profesor;
        if (profesor != null) {
            this.profesorId = profesor.getId();
        }
    }

    public List<MaterialCurs> getMateriale() {
        if (materiale == null) materiale = new ArrayList<>();
        return materiale;
    }
    public void setMateriale(List<MaterialCurs> materiale) { this.materiale = materiale; }

    public List<Tema> getTeme() {
        if (teme == null) teme = new ArrayList<>();
        return teme;
    }
    public void setTeme(List<Tema> teme) { this.teme = teme; }

    public List<User> getStudentiInscrisi() {
        if (studentiInscrisi == null) studentiInscrisi = new ArrayList<>();
        return studentiInscrisi;
    }
    public void setStudentiInscrisi(List<User> studentiInscrisi) {
        this.studentiInscrisi = studentiInscrisi;
    }
}