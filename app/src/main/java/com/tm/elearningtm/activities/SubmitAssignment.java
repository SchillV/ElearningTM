package com.tm.elearningtm.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.SubmissionAdapter;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class SubmitAssignment extends AppCompatActivity {

    private Tema assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int assignmentId = getIntent().getIntExtra("ASSIGNMENT_ID", -1);
        assignment = AppData.getDatabase().temaDao().getTemaById(assignmentId);

        if (assignment == null) {
            Toast.makeText(this, "Assignment not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateAssignmentDetails();

        if (AppData.isProfesor()) {
            setupTeacherView();
        } else {
            setupStudentView();
        }
    }

    private void populateAssignmentDetails() {
        TextView titleText = findViewById(R.id.text_assignment_title);
        TextView deadlineText = findViewById(R.id.text_assignment_deadline);
        TextView descriptionText = findViewById(R.id.text_assignment_description);

        titleText.setText(assignment.getTitlu());
        descriptionText.setText(assignment.getDescriere());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            deadlineText.setText("Deadline: " + assignment.getDeadline().format(formatter));
        }
    }

    private void setupStudentView() {
        findViewById(R.id.teacher_submissions_view).setVisibility(View.GONE);
        Button submitButton = findViewById(R.id.button_submit_assignment);
        TextInputEditText submissionText = findViewById(R.id.edit_text_submission);

        submitButton.setOnClickListener(v -> {
            String submissionContent = Objects.requireNonNull(submissionText.getText()).toString().trim();
            if (submissionContent.isEmpty()) {
                Toast.makeText(this, "Please enter your submission.", Toast.LENGTH_SHORT).show();
                return;
            }

            SubmisieStudent submission = new SubmisieStudent(AppData.getUtilizatorCurent(), submissionContent);
            AppData.getDatabase().submisieDao().insert(submission);

            Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setupTeacherView() {
        findViewById(R.id.student_submission_card).setVisibility(View.GONE);
        findViewById(R.id.button_submit_assignment).setVisibility(View.GONE);
        findViewById(R.id.teacher_submissions_view).setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_submissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SubmisieStudent> submissions = AppData.getDatabase().submisieDao().getSubmisiiForTema(assignment.getId());
        SubmissionAdapter adapter = new SubmissionAdapter(submissions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AppData.isProfesor()) {
            getMenuInflater().inflate(R.menu.menu_assignment_teacher, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit_assignment) {
            // TODO: Implement edit assignment functionality
            Toast.makeText(this, "Edit Assignment - Coming Soon!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_delete_assignment) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Assignment")
                    .setMessage("Are you sure you want to delete this assignment?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        AppData.getDatabase().temaDao().delete(assignment);
                        Toast.makeText(this, "Assignment deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
