package com.tm.elearningtm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CoursePagerAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class CourseDetail extends AppCompatActivity {
    private int courseId;
    private Curs course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Error: Invalid course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCourse();
        setupToolbar();
        setupTabs();
        setupFab();
    }

    private void loadCourse() {
        course = AppData.getDatabase().cursDao().getCursById(courseId);
        if (course == null) {
            Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        AppData.setCursCurent(course);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(course.getTitlu());
        }
    }

    private void setupTabs() {
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        CoursePagerAdapter pagerAdapter = new CoursePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(pagerAdapter.getTabTitle(position))).attach();
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if (AppData.isProfesor()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> showAddOptionsDialog());
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    private void showAddOptionsDialog() {
        final CharSequence[] options = {"Add Material", "Add Assignment", "Assign Student", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add to Course");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Add Material")) {
                Intent intent = new Intent(this, AddMaterial.class);
                intent.putExtra("COURSE_ID", courseId);
                startActivity(intent);
            } else if (options[item].equals("Add Assignment")) {
                Intent intent = new Intent(this, AddAssignment.class);
                intent.putExtra("COURSE_ID", courseId);
                startActivity(intent);
            } else if (options[item].equals("Assign Student")) {
                Intent intent = new Intent(this, AssignStudentActivity.class);
                intent.putExtra("COURSE_ID", courseId);
                startActivity(intent);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AppData.isProfesor()) {
            getMenuInflater().inflate(R.menu.menu_course_teacher, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit_course) {
            Intent intent = new Intent(this, AddCourseActivity.class);
            intent.putExtra("EDIT_COURSE_ID", course.getId());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCourse();
        // To refresh the title if it was edited
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(course.getTitlu());
        }
    }
}
