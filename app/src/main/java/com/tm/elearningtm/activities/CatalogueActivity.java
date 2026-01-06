package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CatalogueAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.models.CatalogueGrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class CatalogueActivity extends AppCompatActivity {

    private List<CatalogueGrade> allGrades;
    private RecyclerView recyclerView;
    private CatalogueAdapter adapter;
    private Spinner categorySpinner;
    private Spinner studentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Catalogue");

        recyclerView = findViewById(R.id.recycler_view_catalogue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categorySpinner = findViewById(R.id.spinner_category);
        studentSpinner = findViewById(R.id.spinner_student);

        loadAllGrades();
        setupSpinners();

        adapter = new CatalogueAdapter(allGrades);
        recyclerView.setAdapter(adapter);
    }

    private void loadAllGrades() {
        allGrades = new ArrayList<>();
        List<SubmisieStudent> submissions;

        if (AppData.isStudent()) {
            submissions = AppData.getDatabase().submisieDao().getSubmisiiByStudent(AppData.getUtilizatorCurent().getId());
        } else { // Teacher or Admin
            submissions = AppData.getDatabase().submisieDao().getAllSubmissions();
        }

        for (SubmisieStudent sub : submissions) {
            if (sub.getNota() != null) {
                User student = AppData.getDatabase().userDao().getUserById(sub.getStudentId());
                Tema assignment = AppData.getDatabase().temaDao().getTemaById(sub.getTemaId());
                if (assignment != null) {
                    Curs course = AppData.getDatabase().cursDao().getCursById(assignment.getCursId());
                    if (student != null && course != null) {
                        allGrades.add(new CatalogueGrade(student.getNume(), course.getTitlu(), course.getCategorie(), assignment.getTitlu(), sub.getNota()));
                    }
                }
            }
        }
    }

    private void setupSpinners() {
        // Category Spinner
        List<String> categories = AppData.getDatabase().cursDao().getAllCategories();
        categories.add(0, "All Categories");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Student Spinner
        if (AppData.isStudent()) {
            studentSpinner.setVisibility(View.GONE);
        } else {
            List<User> students = AppData.getDatabase().userDao().getAllStudents();
            List<String> studentNames = new ArrayList<>();
            studentNames.add("All Students");
            for (User s : students) {
                studentNames.add(s.getNume());
            }
            ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentNames);
            studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            studentSpinner.setAdapter(studentAdapter);
        }

        AdapterView.OnItemSelectedListener filterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterGrades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        categorySpinner.setOnItemSelectedListener(filterListener);
        studentSpinner.setOnItemSelectedListener(filterListener);
    }

    private void filterGrades() {
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String selectedStudent = AppData.isStudent() ? AppData.getUtilizatorCurent().getNume() : studentSpinner.getSelectedItem().toString();

        List<CatalogueGrade> filteredGrades = allGrades.stream()
                .filter(grade -> ("All Categories".equals(selectedCategory) || grade.getCourseCategory().equals(selectedCategory)))
                .filter(grade -> ("All Students".equals(selectedStudent) || grade.getStudentName().equals(selectedStudent)))
                .collect(Collectors.toList());

        adapter = new CatalogueAdapter(filteredGrades);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
