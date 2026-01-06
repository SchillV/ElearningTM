package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
        this.profesorId = profesor != null ? profesor.getId() : 0;
    }

    // Getters and setters
    public int getId() { return id; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getProfesorId() { return profesorId; }
}