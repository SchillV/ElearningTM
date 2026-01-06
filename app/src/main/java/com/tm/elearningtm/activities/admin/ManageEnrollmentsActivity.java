package com.tm.elearningtm.activities.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.EnrollmentManagementAdapter;
import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.database.AppData;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class ManageEnrollmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_enrollments);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Enrollments");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_enrollments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CourseEnrollment> allEnrollments = AppData.getDatabase().enrollmentDao().getAllActiveEnrollments();
        EnrollmentManagementAdapter adapter = new EnrollmentManagementAdapter(allEnrollments);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
