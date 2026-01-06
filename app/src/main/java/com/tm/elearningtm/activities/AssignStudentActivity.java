package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.SelectableStudentAdapter;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssignStudentActivity extends AppCompatActivity {

    private SelectableStudentAdapter adapter;
    private List<User> allStudents;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Assign Student");

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Invalid course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        allStudents = AppData.getDatabase().userDao().getAllStudents();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_all_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectableStudentAdapter(allStudents, courseId);
        recyclerView.setAdapter(adapter);

        setupSearch();
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.search_view_students);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<User> filteredList = new ArrayList<>();
        for (User student : allStudents) {
            if (student.getNume().toLowerCase().contains(text.toLowerCase()) ||
                    student.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(student);
            }
        }
        adapter = new SelectableStudentAdapter(filteredList, courseId);
        ((RecyclerView) findViewById(R.id.recycler_view_all_students)).setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
