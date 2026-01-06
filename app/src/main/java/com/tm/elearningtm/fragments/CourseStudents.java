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
import com.tm.elearningtm.adapters.StudentAdapter;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseStudents extends Fragment {

    public static CourseStudents newInstance() {
        return new CourseStudents();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_students, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_students);
        TextView emptyView = view.findViewById(R.id.text_empty_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<User> students = AppData.getDatabase().enrollmentDao().getStudentsForCourse(AppData.getCursCurent().getId());

        if (students.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            StudentAdapter adapter = new StudentAdapter(students);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
