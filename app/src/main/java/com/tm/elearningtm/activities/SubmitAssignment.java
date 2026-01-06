package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.tm.elearningtm.NotificationsManager;
import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.SubmissionAdapter;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class SubmitAssignment extends AppCompatActivity {

    private Tema assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

    @SuppressLint("SetTextI18n")
    private void populateAssignmentDetails() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(assignment.getTitlu());
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

    @SuppressLint("SetTextI18n")
    private void setupStudentView() {
        SubmisieStudent submission = AppData.getDatabase().submisieDao()
                .getSubmisieByStudentAndTema(AppData.getUtilizatorCurent().getId(), assignment.getId());

        if (submission != null) {
            // Already submitted, show the submission in the list
            findViewById(R.id.student_submission_card).setVisibility(View.GONE);
            findViewById(R.id.button_submit_assignment).setVisibility(View.GONE);
            setupSubmissionsList(Collections.singletonList(submission), "Your Submission");
        } else {
            // Not submitted yet, show the submission form
            findViewById(R.id.submissions_list_container).setVisibility(View.GONE);
            Button submitButton = findViewById(R.id.button_submit_assignment);
            TextInputEditText submissionText = findViewById(R.id.edit_text_submission);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && LocalDateTime.now().isAfter(assignment.getDeadline())) {
                submitButton.setEnabled(false);
                submitButton.setText("Deadline Passed");
            }

            submitButton.setOnClickListener(v -> {
                String submissionContent = Objects.requireNonNull(submissionText.getText()).toString().trim();
                if (submissionContent.isEmpty()) {
                    Toast.makeText(this, "Please enter your submission.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SubmisieStudent newSubmission = new SubmisieStudent(AppData.getUtilizatorCurent(), submissionContent);
                newSubmission.setTemaId(assignment.getId()); // <-- THE FIX
                AppData.getDatabase().submisieDao().insert(newSubmission);

                NotificationsManager.sendNotification(this, 
                    "Assignment Submitted!", 
                    "Your work for '" + assignment.getTitlu() + "' has been received.", 
                    (int) System.currentTimeMillis());

                Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
                recreate(); // Recreate the activity to show the submission list
            });
        }
    }

    private void setupTeacherView() {
        findViewById(R.id.student_submission_card).setVisibility(View.GONE);
        findViewById(R.id.button_submit_assignment).setVisibility(View.GONE);
        List<SubmisieStudent> submissions = AppData.getDatabase().submisieDao().getSubmisiiForTema(assignment.getId());
        setupSubmissionsList(submissions, "Student Submissions");
    }

    @SuppressLint("SetTextI18n")
    private void setupSubmissionsList(List<SubmisieStudent> submissions, String header) {
        View container = findViewById(R.id.submissions_list_container);
        container.setVisibility(View.VISIBLE);

        TextView headerText = findViewById(R.id.submissions_list_header);
        headerText.setText(header);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_submissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit_assignment) {
            Intent intent = new Intent(this, AddAssignment.class);
            intent.putExtra("EDIT_ASSIGNMENT_ID", assignment.getId());
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_delete_assignment) {
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
