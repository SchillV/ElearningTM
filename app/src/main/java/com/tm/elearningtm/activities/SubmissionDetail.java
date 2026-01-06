package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SubmissionDetail extends AppCompatActivity {

    private SubmisieStudent submission;
    private Tema assignment;
    private User student;
    private EditText submissionContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        int submissionId = getIntent().getIntExtra("SUBMISSION_ID", -1);
        submission = AppData.getDatabase().submisieDao().getSubmisieById(submissionId);

        if (submission == null) {
            Toast.makeText(this, "Submission not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        assignment = AppData.getDatabase().temaDao().getTemaById(submission.getTemaId());
        student = AppData.getDatabase().userDao().getUserById(submission.getStudentId());

        if (assignment == null || student == null) {
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getSupportActionBar().setTitle("Submission Details");
        populateDetails();

        if (AppData.isProfesor()) {
            setupTeacherView();
        } else {
            setupStudentView();
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateDetails() {
        TextView studentNameHeader = findViewById(R.id.text_student_name_header);
        TextView submissionDateHeader = findViewById(R.id.text_submission_date_header);
        submissionContentEditText = findViewById(R.id.edit_text_submission_content);

        studentNameHeader.setText("Submission by: " + student.getNume());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            submissionDateHeader.setText("Submitted on: " + submission.getDataSubmisie().format(formatter));
        }
        submissionContentEditText.setText(submission.getContinut());
    }

    @SuppressLint("SetTextI18n")
    private void setupStudentView() {
        findViewById(R.id.teacher_grading_card).setVisibility(View.GONE);
        findViewById(R.id.button_save_grade).setVisibility(View.GONE);

        boolean isGraded = submission.getNota() != null;
        boolean canEdit = !isGraded;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canEdit = !isGraded && LocalDateTime.now().isBefore(assignment.getDeadline());
        }

        if (isGraded) {
            findViewById(R.id.student_action_buttons).setVisibility(View.GONE);
            findViewById(R.id.grade_feedback_card).setVisibility(View.VISIBLE);
            submissionContentEditText.setEnabled(false);

            TextView gradeText = findViewById(R.id.text_grade);
            TextView feedbackText = findViewById(R.id.text_feedback);

            gradeText.setText(String.valueOf(submission.getNota()));
            if (submission.getFeedback() != null && !submission.getFeedback().isEmpty()) {
                feedbackText.setText(submission.getFeedback());
            } else {
                feedbackText.setText("No feedback provided.");
            }
        } else {
            findViewById(R.id.student_action_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.grade_feedback_card).setVisibility(View.GONE);
            submissionContentEditText.setEnabled(canEdit);

            Button saveButton = findViewById(R.id.button_save_submission);
            Button deleteButton = findViewById(R.id.button_delete_submission);

            saveButton.setEnabled(canEdit);
            deleteButton.setEnabled(canEdit);

            saveButton.setOnClickListener(v -> {
                String newContent = submissionContentEditText.getText().toString().trim();
                if (newContent.isEmpty()) {
                    Toast.makeText(this, "Submission content cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                submission.setContinut(newContent);
                AppData.getDatabase().submisieDao().update(submission);
                Toast.makeText(this, "Submission updated!", Toast.LENGTH_SHORT).show();
                finish();
            });

            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("Delete Submission")
                    .setMessage("Are you sure you want to delete your submission?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        AppData.getDatabase().submisieDao().delete(submission);
                        Toast.makeText(this, "Submission deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show());
        }
    }

    private void setupTeacherView() {
        findViewById(R.id.student_action_buttons).setVisibility(View.GONE);
        findViewById(R.id.grade_feedback_card).setVisibility(View.VISIBLE);
        submissionContentEditText.setEnabled(false);

        TextView gradeText = findViewById(R.id.text_grade);
        TextView feedbackText = findViewById(R.id.text_feedback);

        if (submission.getNota() != null) {
            gradeText.setText(String.valueOf(submission.getNota()));
        } else {
            gradeText.setText("Not Graded");
        }

        if (submission.getFeedback() != null && !submission.getFeedback().isEmpty()) {
            feedbackText.setText(submission.getFeedback());
        } else {
            feedbackText.setText("No feedback provided.");
        }
        
        findViewById(R.id.teacher_grading_card).setOnClickListener(v -> {
            Intent intent = new Intent(this, GradeSubmissionActivity.class);
            intent.putExtra("SUBMISSION_ID", submission.getId());
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}