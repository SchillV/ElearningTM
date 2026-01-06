package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tm.elearningtm.classes.User;

import java.util.List;

@Dao
public interface UserDao {

    // ========== INSERT ==========
    @Insert
    long insert(User user);

    @Insert
    List<Long> insertAll(List<User> users);

    // ========== UPDATE ==========
    @Update
    void update(User user);

    // ========== DELETE ==========
    @Delete
    void delete(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    void deleteById(int userId);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsersLive(); // Observable list for UI

    // ========== SELECT - By Role ==========
    @Query("SELECT * FROM users WHERE user_role = 'STUDENT'")
    List<User> getAllStudents();

    @Query("SELECT * FROM users WHERE user_role = 'STUDENT'")
    LiveData<List<User>> getAllStudentsLive();

    @Query("SELECT * FROM users WHERE user_role = 'PROFESOR'")
    List<User> getAllProfessors();

    @Query("SELECT * FROM users WHERE user_role = 'PROFESOR'")
    LiveData<List<User>> getAllProfessorsLive();

    // ========== AUTHENTICATION ==========
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User authenticate(String email); // password is checked in code

    // ========== SEARCH ==========
    @Query("SELECT * FROM users WHERE nume LIKE '%' || :searchQuery || '%'")
    List<User> searchUsersByName(String searchQuery);

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();

    @Query("SELECT COUNT(*) FROM users WHERE user_role = 'STUDENT'")
    int getStudentCount();
}