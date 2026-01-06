package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.CoursePagerAdapter;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

public class CourseDetail extends AppCompatActivity {

    private static final String TAG = "CourseDetailActivity";

    private int courseId;
    private Curs course;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private CoursePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Get course ID from intent
        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Error: Invalid course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load course from database
        loadCourse();

        // Setup toolbar
        setupToolbar();

        // Setup tabs and viewpager
        setupTabs();
    }

    private void loadCourse() {
        course = AppData.getDatabase().cursDao().getCursById(courseId);

        if (course == null) {
            Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Store current course in AppData for easy access
        AppData.setCursCurent(course);
    }

    /**
     * Setup toolbar with course title
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(course.getTitlu());
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupTabs() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        pagerAdapter = new CoursePagerAdapter(this, courseId);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(pagerAdapter.getTabTitle(position))
        ).attach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourse();
    }
}