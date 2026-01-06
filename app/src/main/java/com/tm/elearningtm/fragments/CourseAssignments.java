package com.tm.elearningtm.fragments;

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

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.AssignmentAdapter;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.viewmodel.CourseViewModel;

import java.util.ArrayList;

public class CourseAssignments extends Fragment {

    private AssignmentAdapter adapter;

    public static CourseAssignments newInstance(int courseId) {
        return new CourseAssignments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_assignments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_assignments);
        TextView emptyView = view.findViewById(R.id.text_empty_assignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AssignmentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        CourseViewModel viewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        viewModel.getAssignmentsForCourse(AppData.getCursCurent().getId()).observe(getViewLifecycleOwner(), assignments -> {
            if (assignments.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                // The adapter needs a method to update its data
                // For now, we create a new one each time.
                adapter = new AssignmentAdapter(assignments);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
