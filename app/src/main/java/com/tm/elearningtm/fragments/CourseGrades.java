package com.tm.elearningtm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.GradeAdapter;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseGrades extends Fragment {

    public static CourseGrades newInstance() {
        return new CourseGrades();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_grades, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_grades);
        TextView emptyView = view.findViewById(R.id.text_empty_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SubmisieStudent> submissions = AppData.getDatabase().submisieDao().getSubmissionsForStudentInCourse(AppData.getUtilizatorCurent().getId(), AppData.getCursCurent().getId());

        if (submissions.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            GradeAdapter adapter = new GradeAdapter(submissions);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
