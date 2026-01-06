package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tm.elearningtm.classes.Curs;

import java.util.List;

@Dao
public interface CursDao {

    // ========== INSERT ==========
    @Insert
    long insert(Curs curs);

    // ========== UPDATE ==========
    @Update
    void update(Curs curs);

    // ========== DELETE ==========
    @Delete
    void delete(Curs curs);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM courses WHERE id = :cursId")
    Curs getCursById(int cursId);

    @Query("SELECT * FROM courses")
    List<Curs> getAllCourses();

    @Query("SELECT * FROM courses")
    LiveData<List<Curs>> getAllCoursesLive();

    // ========== SELECT - By Profesor ==========
    @Query("SELECT * FROM courses WHERE profesor_id = :profesorId")
    LiveData<List<Curs>> getCoursesByProfesorLive(int profesorId);

    // ========== SELECT - By Category ==========
    @Query("SELECT DISTINCT categorie FROM courses")
    List<String> getAllCategories();

    // ========== SEARCH ==========
    @Query("SELECT * FROM courses WHERE titlu LIKE '%' || :searchQuery || '%'")
    List<Curs> searchCoursesByTitle(String searchQuery);

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM courses")
    int getCourseCount();
}