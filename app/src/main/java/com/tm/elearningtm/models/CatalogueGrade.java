package com.tm.elearningtm.models;

public class CatalogueGrade {
    private final String studentName;
    private final String courseName;
    private final String courseCategory;
    private final String assignmentName;
    private final double grade;

    public CatalogueGrade(String studentName, String courseName, String courseCategory, String assignmentName, double grade) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.assignmentName = assignmentName;
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public double getGrade() {
        return grade;
    }
}
