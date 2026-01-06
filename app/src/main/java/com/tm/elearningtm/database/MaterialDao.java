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

    @Insert
    List<Long> insertAll(List<MaterialCurs> materiale);

    // ========== UPDATE ========== 
    @Update
    void update(MaterialCurs material);

    // ========== DELETE ========== 
    @Delete
    void delete(MaterialCurs material);

    @Query("DELETE FROM materiale WHERE id = :materialId")
    void deleteById(int materialId);

    // ========== SELECT - Basic ========== 
    @Query("SELECT * FROM materiale WHERE id = :materialId")
    MaterialCurs getMaterialById(int materialId);

    // ========== SELECT - By Course ========== 
    @Query("SELECT * FROM materiale WHERE curs_id = :cursId")
    List<MaterialCurs> getMaterialeForCurs(int cursId);

    @Query("SELECT * FROM materiale WHERE curs_id = :cursId")
    LiveData<List<MaterialCurs>> getMaterialeForCursLive(int cursId);

    // ========== SELECT - By Type ========== 
    @Query("SELECT * FROM materiale WHERE curs_id = :cursId AND tip_material = :tipMaterial")
    List<MaterialCurs> getMaterialeByType(int cursId, String tipMaterial);

    @Query("SELECT * FROM materiale WHERE curs_id = :cursId AND tip_material = :tipMaterial")
    LiveData<List<MaterialCurs>> getMaterialeByTypeLive(int cursId, String tipMaterial);

    // ========== SELECT - Ordered by date ========== 
    @Query("SELECT * FROM materiale WHERE curs_id = :cursId ORDER BY data_creare DESC")
    LiveData<List<MaterialCurs>> getMaterialeOrderedByDate(int cursId);

    // ========== COUNT ========== 
    @Query("SELECT COUNT(*) FROM materiale WHERE curs_id = :cursId")
    int getMaterialCountForCurs(int cursId);
}