package com.tm.elearningtm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseViewModel extends ViewModel {

    public LiveData<List<User>> getStudentsForCourse(int courseId) {
        return AppData.getDatabase().enrollmentDao().getStudentsForCourseLive(courseId);
    }

    public LiveData<List<Tema>> getAssignmentsForCourse(int courseId) {
        return AppData.getDatabase().temaDao().getTemeForCursLive(courseId);
    }

    public LiveData<List<MaterialCurs>> getMaterialsForCourse(int courseId) {
        return AppData.getDatabase().materialDao().getMaterialeForCursLive(courseId);
    }
}