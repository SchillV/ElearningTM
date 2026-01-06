package com.tm.elearningtm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tm.elearningtm.R;
import com.tm.elearningtm.activities.AssignStudentActivity;
import com.tm.elearningtm.adapters.StudentAdapter;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.viewmodel.CourseViewModel;

import java.util.ArrayList;

public class CourseStudents extends Fragment {

    private StudentAdapter adapter;

    public static CourseStudents newInstance() {
        return new CourseStudents();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_students);
        TextView emptyView = view.findViewById(R.id.text_empty_students);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_student);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StudentAdapter(new ArrayList<>(), AppData.isProfesor());
        recyclerView.setAdapter(adapter);

        CourseViewModel viewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        viewModel.getStudentsForCourse(AppData.getCursCurent().getId()).observe(getViewLifecycleOwner(), students -> {
            if (students.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                adapter.updateStudents(students);
            }
        });

        if (AppData.isProfesor()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AssignStudentActivity.class);
                intent.putExtra("COURSE_ID", AppData.getCursCurent().getId());
                startActivity(intent);
            });
        } else {
            fab.setVisibility(View.GONE);
        }
    }
}