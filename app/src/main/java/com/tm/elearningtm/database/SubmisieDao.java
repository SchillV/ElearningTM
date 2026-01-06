package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tm.elearningtm.classes.SubmisieStudent;

import java.util.List;

@Dao
public interface SubmisieDao {

    // ========== INSERT ==========
    @Insert
    long insert(SubmisieStudent submisie);

    // ========== UPDATE ==========
    @Update
    void update(SubmisieStudent submisie);

    // ========== DELETE ==========
    @Delete
    void delete(SubmisieStudent submisie);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM submisii")
    List<SubmisieStudent> getAllSubmissions();

    @Query("SELECT * FROM submisii WHERE id = :submisieId")
    SubmisieStudent getSubmisieById(int submisieId);

    // ========== SELECT - By Student ==========
    @Query("SELECT * FROM submisii WHERE student_id = :studentId")
    List<SubmisieStudent> getSubmisiiByStudent(int studentId);

    @Query("SELECT * FROM submisii WHERE student_id = :studentId")
    LiveData<List<SubmisieStudent>> getSubmisiiByStudentLive(int studentId);

    // ========== SELECT - By Tema ==========
    @Query("SELECT * FROM submisii WHERE tema_id = :temaId")
    List<SubmisieStudent> getSubmisiiForTema(int temaId);

    @Query("SELECT * FROM submisii WHERE tema_id = :temaId")
    LiveData<List<SubmisieStudent>> getSubmisiiForTemaLive(int temaId);

    // ========== CHECK if student submitted ==========
    @Query("SELECT * FROM submisii WHERE student_id = :studentId AND tema_id = :temaId LIMIT 1")
    SubmisieStudent getSubmisieByStudentAndTema(int studentId, int temaId);

    // ========== SELECT - Graded/Ungraded ==========
    @Query("SELECT * FROM submisii WHERE tema_id = :temaId AND nota IS NULL")
    List<SubmisieStudent> getUngradedSubmisii(int temaId);

    @Query("SELECT * FROM submisii WHERE tema_id = :temaId AND nota IS NOT NULL")
    List<SubmisieStudent> getGradedSubmisii(int temaId);

    @Query("SELECT * FROM submisii WHERE tema_id = :temaId AND nota IS NULL")
    LiveData<List<SubmisieStudent>> getUngradedSubmisiiLive(int temaId);

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM submisii WHERE tema_id = :temaId")
    int getSubmissionCount(int temaId);

    @Query("SELECT COUNT(*) FROM submisii WHERE tema_id = :temaId AND nota IS NULL")
    int getUngradedCount(int temaId);

    // ========== SELECT - By Student and Course ==========
    @Query("SELECT s.* FROM submisii s JOIN teme t ON s.tema_id = t.id WHERE s.student_id = :studentId AND t.curs_id = :cursId")
    List<SubmisieStudent> getSubmissionsForStudentInCourse(int studentId, int cursId);
}