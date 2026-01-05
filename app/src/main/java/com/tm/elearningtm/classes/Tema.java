package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tm.elearningtm.database.Converters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(
        tableName = "teme",
        foreignKeys = @ForeignKey(
                entity = Curs.class,
                parentColumns = "id",
                childColumns = "curs_id",
                onDelete = ForeignKey.CASCADE
        )
)
@TypeConverters(Converters.class)
public class Tema {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titlu;
    private String descriere;

    @ColumnInfo(name = "curs_id", index = true)
    private int cursId;

    @ColumnInfo(name = "data_creare")
    private LocalDateTime dataCreare;

    private LocalDateTime deadline;

    @Ignore
    private List<SubmisieStudent> submisii;

    // Full and Empty constructors for Room
    public Tema() {
    }

    public Tema(int id, String titlu, String descriere, int cursId,
                LocalDateTime dataCreare, LocalDateTime deadline) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.cursId = cursId;
        this.dataCreare = dataCreare;
        this.deadline = deadline;
        this.submisii = new ArrayList<>();
    }

    @Ignore
    public Tema(String titlu, String descriere, LocalDateTime deadline) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.deadline = deadline;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.dataCreare = LocalDateTime.now();
        }
        this.submisii = new ArrayList<>();
    }

    public void adaugaSubmisie(SubmisieStudent submisie) {
        if (submisii == null) submisii = new ArrayList<>();
        submisii.add(submisie);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public int getCursId() { return cursId; }
    public void setCursId(int cursId) { this.cursId = cursId; }

    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public List<SubmisieStudent> getSubmisiiStudenti() {
        if (submisii == null) submisii = new ArrayList<>();
        return submisii;
    }
    public void setSubmisii(List<SubmisieStudent> submisii) { this.submisii = submisii; }
}