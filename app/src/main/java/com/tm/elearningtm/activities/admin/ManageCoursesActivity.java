package com.tm.elearningtm.activities.admin;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CourseManagementAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class ManageCoursesActivity extends AppCompatActivity {

    private CourseManagementAdapter adapter;
    private List<Curs> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Courses");

        allCourses = AppData.getDatabase().cursDao().getAllCourses();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseManagementAdapter(allCourses);
        recyclerView.setAdapter(adapter);

        setupSearch();
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.search_view_courses);
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
        List<Curs> filteredList = new ArrayList<>();
        for (Curs course : allCourses) {
            if (course.getTitlu().toLowerCase().contains(text.toLowerCase()) ||
                    course.getCategorie().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(course);
            }
        }
        adapter = new CourseManagementAdapter(filteredList);
        ((RecyclerView) findViewById(R.id.recycler_view_courses)).setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
