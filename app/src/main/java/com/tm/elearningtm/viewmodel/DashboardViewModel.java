package com.tm.elearningtm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    public LiveData<List<Curs>> getCoursesForStudent(int studentId) {
        return AppData.getDatabase().enrollmentDao().getCoursesForStudentLive(studentId);
    }

    public LiveData<List<Curs>> getCoursesForProfessor(int professorId) {
        return AppData.getDatabase().cursDao().getCoursesByProfesorLive(professorId);
    }

    public LiveData<List<Curs>> getAllCourses() {
        return AppData.getDatabase().cursDao().getAllCoursesLive();
    }
}
