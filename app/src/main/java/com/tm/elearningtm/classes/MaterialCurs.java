package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tm.elearningtm.database.Converters;

import java.time.LocalDateTime;

@Entity(
        tableName = "materiale",
        foreignKeys = @ForeignKey(
                entity = Curs.class,
                parentColumns = "id",
                childColumns = "curs_id",
                onDelete = ForeignKey.CASCADE
        )
)
@TypeConverters(Converters.class)
public class MaterialCurs {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titlu;
    private String descriere;

    @ColumnInfo(name = "curs_id", index = true)
    private int cursId;

    @ColumnInfo(name = "tip_material")
    private String tipMaterial;

    @ColumnInfo(name = "data_creare")
    private LocalDateTime dataCreare;

    // Full and Empty constructors for Room
    public MaterialCurs() {
    }

    public MaterialCurs(int id, String titlu, String descriere, int cursId,
                        String tipMaterial, LocalDateTime dataCreare) {
        this.id = id;
        this.titlu = titlu;
        this.descriere = descriere;
        this.cursId = cursId;
        this.tipMaterial = tipMaterial;
        this.dataCreare = dataCreare;
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

    public String getTipMaterial() { return tipMaterial; }
    public void setTipMaterial(String tipMaterial) { this.tipMaterial = tipMaterial; }

    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
}