package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Junction table representing the many-to-many relationship
 * between Students and Courses.
 */
@Entity(
        tableName = "course_enrollments",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "student_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Curs.class,
                        parentColumns = "id",
                        childColumns = "course_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "student_id"),
                @Index(value = "course_id"),
                @Index(value = {"student_id", "course_id"}, unique = true) // Prevent duplicate enrollments
        }
)
public class CourseEnrollment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "student_id")
    private int studentId;

    @ColumnInfo(name = "course_id")
    private int courseId;

    @ColumnInfo(name = "enrollment_date")
    private long enrollmentDate; // Unix timestamp (easier than LocalDateTime)

    @ColumnInfo(name = "is_active")
    private boolean isActive; // false if student dropped the course

    // Empty constructor for Room
    public CourseEnrollment() {
    }

    // Constructor
    public CourseEnrollment(int studentId, int courseId, long enrollmentDate, boolean isActive) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.isActive = isActive;
    }

    // Convenience constructor (sets current time and active=true)
    public CourseEnrollment(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = System.currentTimeMillis();
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public long getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(long enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}