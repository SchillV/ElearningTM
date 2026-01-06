package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CourseAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements CourseAdapter.OnCourseListener {

    private CourseAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView welcomeText = findViewById(R.id.text_welcome);
        TextView roleText = findViewById(R.id.text_user_role);
        Button adminButton = findViewById(R.id.button_admin_panel);
        Button createCourseButton = findViewById(R.id.button_create_course);
        Button catalogueButton = findViewById(R.id.button_catalogue);
        Button logoutButton = findViewById(R.id.button_logout);

        User currentUser = AppData.getUtilizatorCurent();
        if (currentUser == null) {
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome, " + currentUser.getNume() + "!");
        welcomeText.setText("Bine ai venit, " + currentUser.getNume() + "!");
        roleText.setText("Rol: " + currentUser.getRole());

        catalogueButton.setOnClickListener(v -> startActivity(new Intent(this, CatalogueActivity.class)));

        if (AppData.isProfesor() || AppData.isAdmin()) {
            createCourseButton.setVisibility(View.VISIBLE);
            createCourseButton.setOnClickListener(v -> startActivity(new Intent(this, AddCourseActivity.class)));
        } else {
            createCourseButton.setVisibility(View.GONE);
        }

        if (AppData.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(v -> startActivity(new Intent(this, AdminDashboard.class)));
        } else {
            adminButton.setVisibility(View.GONE);
        }

        RecyclerView coursesRecyclerView = findViewById(R.id.recycler_view_courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(new ArrayList<>(), this);
        coursesRecyclerView.setAdapter(adapter);

        setupViewModel(currentUser);

        logoutButton.setOnClickListener(v -> logout());
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

    @Override
    public void onCourseClick(Curs curs) {
        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("COURSE_ID", curs.getId());
        startActivity(intent);
    }

    private void logout() {
        AppData.logout();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
