package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
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

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SubmissionDetail extends AppCompatActivity {

    private SubmisieStudent submission;
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

        student = AppData.getDatabase().userDao().getUserById(submission.getStudentId());

        if (student == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
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

    private void setupStudentView() {
        findViewById(R.id.teacher_grading_card).setVisibility(View.GONE);
        findViewById(R.id.button_save_grade).setVisibility(View.GONE);

        Button saveButton = findViewById(R.id.button_save_submission);
        Button deleteButton = findViewById(R.id.button_delete_submission);

        // Allow editing only if the submission has not been graded
        boolean isGraded = submission.getNota() != null;
        submissionContentEditText.setEnabled(!isGraded);
        saveButton.setEnabled(!isGraded);
        deleteButton.setEnabled(!isGraded);

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

    private void setupTeacherView() {
        findViewById(R.id.student_action_buttons).setVisibility(View.GONE);
        findViewById(R.id.teacher_grading_card).setVisibility(View.VISIBLE);
        findViewById(R.id.button_save_grade).setVisibility(View.VISIBLE);

        TextInputEditText gradeEditText = findViewById(R.id.edit_text_grade);
        TextInputEditText feedbackEditText = findViewById(R.id.edit_text_feedback);
        Button saveGradeButton = findViewById(R.id.button_save_grade);

        if (submission.getNota() != null) {
            gradeEditText.setText(String.valueOf(submission.getNota()));
        }
        // feedbackEditText.setText(submission.getFeedback()); // Assuming a feedback field exists

        saveGradeButton.setOnClickListener(v -> {
            String gradeStr = Objects.requireNonNull(gradeEditText.getText()).toString().trim();
            String feedback = Objects.requireNonNull(feedbackEditText.getText()).toString().trim();

            if (gradeStr.isEmpty()) {
                Toast.makeText(this, "Grade cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double grade = Double.parseDouble(gradeStr);
                submission.setNota(grade);
                // submission.setFeedback(feedback); // Assuming a feedback field exists
                AppData.getDatabase().submisieDao().update(submission);
                Toast.makeText(this, "Grade saved!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid grade format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
