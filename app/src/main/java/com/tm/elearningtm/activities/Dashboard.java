package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CourseAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {

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

        catalogueButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CatalogueActivity.class));
        });

        if (AppData.isProfesor() || AppData.isAdmin()) {
            createCourseButton.setVisibility(View.VISIBLE);
            createCourseButton.setOnClickListener(v -> {
                startActivity(new Intent(this, AddCourseActivity.class));
            });
        } else {
            createCourseButton.setVisibility(View.GONE);
        }

        if (AppData.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(v -> {
                startActivity(new Intent(this, AdminDashboard.class));
            });
        } else {
            adminButton.setVisibility(View.GONE);
        }

        RecyclerView coursesRecyclerView = findViewById(R.id.recycler_view_courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Curs> cursuri;
        if (currentUser.isStudent()) {
            cursuri = AppData.getDatabase().enrollmentDao().getCoursesForStudent(currentUser.getId());
        } else if (currentUser.isProfesor()) {
            cursuri = AppData.getDatabase().cursDao().getCoursesByProfesor(currentUser.getId());
        } else {
            cursuri = AppData.getDatabase().cursDao().getAllCourses();
        }

        CourseAdapter adapter = new CourseAdapter(cursuri, this::openCourse);
        coursesRecyclerView.setAdapter(adapter);

        logoutButton.setOnClickListener(v -> logout());
    }

    private void openCourse(Curs curs) {
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
