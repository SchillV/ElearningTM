package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CourseAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.data.AppData;

import java.util.List;

public class Dashboard extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView welcomeText = findViewById(R.id.text_welcome);
        TextView roleText = findViewById(R.id.text_user_role);
        Button logoutButton = findViewById(R.id.button_logout);

        User currentUser = AppData.getUtilizatorCurent();
        if (currentUser == null) {
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        welcomeText.setText("Bine ai venit, " + currentUser.getNume() + "!");
        roleText.setText("Rol: " + currentUser.getRole());

        RecyclerView coursesRecyclerView = findViewById(R.id.recycler_view_courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Curs> cursuri =
                AppData.getCatalog().getCursuri();

        CourseAdapter adapter =
                new CourseAdapter(cursuri, this::openCourse);

        coursesRecyclerView.setAdapter(adapter);

        logoutButton.setOnClickListener(v -> logout());
    }

    private void openCourse(Curs curs) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("COURSE_ID", curs.getId());
        startActivity(intent);
    }

    private void logout() {
        AppData.logout();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
