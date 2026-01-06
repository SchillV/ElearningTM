package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tm.elearningtm.classes.MaterialCurs;

import java.util.List;

@Dao
public interface MaterialDao {

    // ========== INSERT ========== 
    @Insert
    long insert(MaterialCurs material);

    // ========== UPDATE ========== 
    @Update
    void update(MaterialCurs material);

    // ========== DELETE ========== 
    @Delete
    void delete(MaterialCurs material);

    // ========== SELECT - Basic ========== 
    @Query("SELECT * FROM materiale WHERE id = :materialId")
    MaterialCurs getMaterialById(int materialId);

    // ========== SELECT - By Course ==========
    @Query("SELECT * FROM materiale WHERE curs_id = :cursId")
    LiveData<List<MaterialCurs>> getMaterialeForCursLive(int cursId);

    // ========== COUNT ========== 
    @Query("SELECT COUNT(*) FROM materiale WHERE curs_id = :cursId")
    int getMaterialCountForCurs(int cursId);
}