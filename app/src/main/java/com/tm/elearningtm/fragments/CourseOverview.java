package com.tm.elearningtm.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseOverview extends Fragment {

    private Curs course;

    public static CourseOverview newInstance() {
        return new CourseOverview();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = AppData.getCursCurent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_overview, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (course != null) {
            // Set static info
            TextView descriptionText = view.findViewById(R.id.text_course_description);
            descriptionText.setText(course.getDescriere());

            User professor = AppData.getDatabase().userDao().getUserById(course.getProfesorId());
            if (professor != null) {
                TextView professorText = view.findViewById(R.id.text_professor_info);
                professorText.setText("Professor: " + professor.getNume());
            }

            // Set dynamic stats
            if (AppData.isStudent()) {
                setupStudentStats(view);
            } else {
                setupTeacherStats(view);
            }
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setupStudentStats(View view) {
        int assignmentsCount = AppData.getDatabase().temaDao().getTemeCountForCurs(course.getId());
        List<SubmisieStudent> submissions = AppData.getDatabase().submisieDao().getSubmissionsForStudentInCourse(AppData.getUtilizatorCurent().getId(), course.getId());
        int materialsCount = AppData.getDatabase().materialDao().getMaterialCountForCurs(course.getId());

        double totalGrade = 0;
        int gradedCount = 0;
        for (SubmisieStudent s : submissions) {
            if (s.getNota() != null) {
                totalGrade += s.getNota();
                gradedCount++;
            }
        }
        double averageGrade = (gradedCount > 0) ? totalGrade / gradedCount : 0;

        ((TextView) view.findViewById(R.id.text_stat1_value)).setText(String.valueOf(assignmentsCount));
        ((TextView) view.findViewById(R.id.text_stat1_label)).setText("Assignments");

        ((TextView) view.findViewById(R.id.text_stat2_value)).setText(String.format("%.1f", averageGrade));
        ((TextView) view.findViewById(R.id.text_stat2_label)).setText("Avg. Grade");

        ((TextView) view.findViewById(R.id.text_stat3_value)).setText(String.valueOf(materialsCount));
        ((TextView) view.findViewById(R.id.text_stat3_label)).setText("Materials");
    }

    @SuppressLint("SetTextI18n")
    private void setupTeacherStats(View view) {
        int studentCount = AppData.getDatabase().enrollmentDao().getStudentCountForCourse(course.getId());
        int assignmentsCount = AppData.getDatabase().temaDao().getTemeCountForCurs(course.getId());
        int materialsCount = AppData.getDatabase().materialDao().getMaterialCountForCurs(course.getId());

        ((TextView) view.findViewById(R.id.text_stat1_value)).setText(String.valueOf(studentCount));
        ((TextView) view.findViewById(R.id.text_stat1_label)).setText("Students");

        ((TextView) view.findViewById(R.id.text_stat2_value)).setText(String.valueOf(assignmentsCount));
        ((TextView) view.findViewById(R.id.text_stat2_label)).setText("Assignments");

        ((TextView) view.findViewById(R.id.text_stat3_value)).setText(String.valueOf(materialsCount));
        ((TextView) view.findViewById(R.id.text_stat3_label)).setText("Materials");
    }
}
