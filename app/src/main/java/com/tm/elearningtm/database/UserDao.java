package com.tm.elearningtm.database;

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

    // ========== UPDATE ==========
    @Update
    void update(User user);

    // ========== DELETE ==========
    @Delete
    void delete(User user);

    // ========== SELECT - Basic ==========
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    // ========== SELECT - By Role ==========
    @Query("SELECT * FROM users WHERE user_role = 'STUDENT'")
    List<User> getAllStudents();

    // ========== COUNT ==========
    @Query("SELECT COUNT(*) FROM users")
    int getUserCount();

    @Query("SELECT COUNT(*) FROM users WHERE user_role = 'STUDENT'")
    int getStudentCount();
}