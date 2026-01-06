package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tm.elearningtm.database.Converters;

import java.time.LocalDateTime;

@Entity(
        tableName = "submisii",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Tema.class,
                        parentColumns = "id",
                        childColumns = "tema_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
@TypeConverters(Converters.class)
public class SubmisieStudent {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "student_id", index = true)
    private int studentId;

    @ColumnInfo(name = "tema_id", index = true)
    private int temaId;

    private String continut;

    @ColumnInfo(name = "data_submisie")
    private LocalDateTime dataSubmisie;

    private Double nota;

    private String feedback;

    // Full and Empty constructors for Room
    public SubmisieStudent() {
    }

    public SubmisieStudent(int id, int studentId, int temaId, String continut,
                           LocalDateTime dataSubmisie, Double nota, String feedback) {
        this.id = id;
        this.studentId = studentId;
        this.temaId = temaId;
        this.continut = continut;
        this.dataSubmisie = dataSubmisie;
        this.nota = nota;
        this.feedback = feedback;
    }

    @Ignore
    public SubmisieStudent(User student, String continut) {
        // loaded separately
        this.studentId = student.getId();
        this.continut = continut;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.dataSubmisie = LocalDateTime.now();
        }
        this.nota = null;
        this.feedback = null;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getTemaId() { return temaId; }
    public void setTemaId(int temaId) { this.temaId = temaId; }

    public String getContinut() { return continut; }
    public void setContinut(String continut) { this.continut = continut; }

    public LocalDateTime getDataSubmisie() { return dataSubmisie; }
    public void setDataSubmisie(LocalDateTime dataSubmisie) { this.dataSubmisie = dataSubmisie; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

}