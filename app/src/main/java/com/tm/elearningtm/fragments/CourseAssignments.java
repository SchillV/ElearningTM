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
import com.tm.elearningtm.adapters.AssignmentAdapter;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseAssignments extends Fragment {

    public static CourseAssignments newInstance(int courseId) {
        // Unused, but kept for consistency
        return new CourseAssignments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_assignments, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_assignments);
        TextView emptyView = view.findViewById(R.id.text_empty_assignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Tema> assignments = AppData.getDatabase().temaDao().getTemeForCurs(AppData.getCursCurent().getId());

        if (assignments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            AssignmentAdapter adapter = new AssignmentAdapter(assignments);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
