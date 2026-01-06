package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tm.elearningtm.classes.Tema;

import java.util.List;

@Dao
public interface TemaDao {

    // ========== INSERT ==========
    @Insert
    void insert(Tema tema);

    // ========== UPDATE ==========
    @Update
    void update(Tema tema);

    // ========== DELETE ==========
    @Delete
    void delete(Tema tema);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM teme WHERE id = :temaId")
    Tema getTemaById(int temaId);

    @Query("SELECT * FROM teme")
    List<Tema> getAllTeme();

    // ========== SELECT - By Course ==========
    @Query("SELECT * FROM teme WHERE curs_id = :cursId")
    LiveData<List<Tema>> getTemeForCursLive(int cursId);

    // ========== SELECT - By Deadline ==========
    @Query("SELECT * FROM teme WHERE deadline > :currentDate ORDER BY deadline ASC")
    LiveData<List<Tema>> getUpcomingTeme(String currentDate);

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM teme WHERE curs_id = :cursId")
    int getTemeCountForCurs(int cursId);
}