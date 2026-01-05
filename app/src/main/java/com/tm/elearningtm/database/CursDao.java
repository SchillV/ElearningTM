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

    @Insert
    List<Long> insertAll(List<Curs> cursuri);

    // ========== UPDATE ==========
    @Update
    void update(Curs curs);

    // ========== DELETE ==========
    @Delete
    void delete(Curs curs);

    @Query("DELETE FROM courses WHERE id = :cursId")
    void deleteById(int cursId);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM courses WHERE id = :cursId")
    Curs getCursById(int cursId);

    @Query("SELECT * FROM courses")
    List<Curs> getAllCourses();

    @Query("SELECT * FROM courses")
    LiveData<List<Curs>> getAllCoursesLive();

    // ========== SELECT - By Profesor ==========
    @Query("SELECT * FROM courses WHERE profesor_id = :profesorId")
    List<Curs> getCoursesByProfesor(int profesorId);

    @Query("SELECT * FROM courses WHERE profesor_id = :profesorId")
    LiveData<List<Curs>> getCoursesByProfesorLive(int profesorId);

    // ========== SELECT - By Category ==========
    @Query("SELECT * FROM courses WHERE categorie = :categorie")
    List<Curs> getCoursesByCategory(String categorie);

    @Query("SELECT * FROM courses WHERE categorie = :categorie")
    LiveData<List<Curs>> getCoursesByCategoryLive(String categorie);

    // ========== SEARCH ==========
    @Query("SELECT * FROM courses WHERE titlu LIKE '%' || :searchQuery || '%'")
    List<Curs> searchCoursesByTitle(String searchQuery);

    @Query("SELECT * FROM courses WHERE titlu LIKE '%' || :query || '%' OR descriere LIKE '%' || :query || '%'")
    LiveData<List<Curs>> searchCoursesLive(String query);

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM courses")
    int getCourseCount();

    @Query("SELECT COUNT(*) FROM courses WHERE profesor_id = :profesorId")
    int getCourseCountByProfesor(int profesorId);
}