package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CourseAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.viewmodel.DashboardViewModel;

import java.util.ArrayList;

public class Dashboard extends BaseActivity implements CourseAdapter.OnCourseListener {

    private CourseAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupNavDrawer(R.id.nav_dashboard);
        setupFab();

        User currentUser = AppData.getUtilizatorCurent();
        if (currentUser == null) {
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        RecyclerView coursesRecyclerView = findViewById(R.id.recycler_view_courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(new ArrayList<>(), this);
        coursesRecyclerView.setAdapter(adapter);

        setupViewModel(currentUser);
    }

    private void setupViewModel(User currentUser) {
        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        if (currentUser.isStudent()) {
            viewModel.getCoursesForStudent(currentUser.getId()).observe(this, courses -> adapter.updateCourses(courses));
        } else if (currentUser.isProfesor()) {
            viewModel.getCoursesForProfessor(currentUser.getId()).observe(this, courses -> adapter.updateCourses(courses));
        } else { // Admin
            viewModel.getAllCourses().observe(this, courses -> adapter.updateCourses(courses));
        }
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if (AppData.isProfesor()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> startActivity(new Intent(this, AddCourseActivity.class)));
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCourseClick(Curs curs) {
        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("COURSE_ID", curs.getId());
        startActivity(intent);
    }
}