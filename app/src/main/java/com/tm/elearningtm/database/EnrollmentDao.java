package com.tm.elearningtm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.classes.User;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
@Dao
public interface EnrollmentDao {

    // ========== INSERT ==========
    @Insert
    long insert(CourseEnrollment enrollment);

    // ========== DELETE ==========
    @Delete
    void delete(CourseEnrollment enrollment);

    @Query("DELETE FROM course_enrollments WHERE student_id = :studentId AND course_id = :courseId")
    void deleteEnrollment(int studentId, int courseId);

    // ========== CHECK ENROLLMENT ==========
    @Query("SELECT EXISTS(SELECT 1 FROM course_enrollments WHERE student_id = :studentId AND course_id = :courseId AND is_active = 1)")
    boolean isStudentEnrolled(int studentId, int courseId);

    // ========== GET COURSES FOR STUDENT ==========
    @Query("SELECT courses.* FROM courses " +
            "INNER JOIN course_enrollments ON courses.id = course_enrollments.course_id " +
            "WHERE course_enrollments.student_id = :studentId AND course_enrollments.is_active = 1")
    LiveData<List<Curs>> getCoursesForStudentLive(int studentId);

    // ========== GET STUDENTS FOR COURSE ==========
    @Query("SELECT users.* FROM users " +
            "INNER JOIN course_enrollments ON users.id = course_enrollments.student_id " +
            "WHERE course_enrollments.course_id = :courseId AND course_enrollments.is_active = 1")
    List<User> getStudentsForCourse(int courseId);

    @Query("SELECT users.* FROM users " +
            "INNER JOIN course_enrollments ON users.id = course_enrollments.student_id " +
            "WHERE course_enrollments.course_id = :courseId AND course_enrollments.is_active = 1")
    LiveData<List<User>> getStudentsForCourseLive(int courseId);

    // ========== GET ALL ENROLLMENTS ==========
    @Query("SELECT * FROM course_enrollments WHERE is_active = 1")
    List<CourseEnrollment> getAllActiveEnrollments();

    // ========== COUNT ==========
    @Query("SELECT COUNT(DISTINCT student_id) FROM course_enrollments WHERE course_id = :courseId AND is_active = 1")
    int getStudentCountForCourse(int courseId);
}