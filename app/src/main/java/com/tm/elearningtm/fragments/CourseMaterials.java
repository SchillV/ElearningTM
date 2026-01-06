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
import com.tm.elearningtm.adapters.CourseMaterialAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseMaterials extends Fragment {

    public static CourseMaterials newInstance() {
        return new CourseMaterials();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_materials, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_materials);
        TextView emptyView = view.findViewById(R.id.text_empty_materials);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Curs currentCourse = AppData.getCursCurent();
        if (currentCourse != null) {
            List<MaterialCurs> materials = AppData.getDatabase().materialDao().getMaterialeForCurs(currentCourse.getId());
            if (materials.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                CourseMaterialAdapter adapter = new CourseMaterialAdapter(materials);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
