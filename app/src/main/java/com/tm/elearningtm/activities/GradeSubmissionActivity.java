package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.NotificationsManager;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.database.AppData;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class GradeSubmissionActivity extends AppCompatActivity {

    private SubmisieStudent submission;
    private TextInputEditText gradeEditText;
    private TextInputEditText feedbackEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_submission);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Grade Submission");

        int submissionId = getIntent().getIntExtra("SUBMISSION_ID", -1);
        submission = AppData.getDatabase().submisieDao().getSubmisieById(submissionId);

        if (submission == null) {
            Toast.makeText(this, "Submission not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        gradeEditText = findViewById(R.id.edit_text_grade);
        feedbackEditText = findViewById(R.id.edit_text_feedback);
        Button saveGradeButton = findViewById(R.id.button_save_grade);

        if (submission.getNota() != null) {
            gradeEditText.setText(String.valueOf(submission.getNota()));
        }
        if (submission.getFeedback() != null) {
            feedbackEditText.setText(submission.getFeedback());
        }

        saveGradeButton.setOnClickListener(v -> saveGrade());
    }

    private void saveGrade() {
        String gradeStr = Objects.requireNonNull(gradeEditText.getText()).toString().trim();
        String feedback = Objects.requireNonNull(feedbackEditText.getText()).toString().trim();

        if (gradeStr.isEmpty()) {
            Toast.makeText(this, "Grade cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double grade = Double.parseDouble(gradeStr);
            submission.setNota(grade);
            submission.setFeedback(feedback);
            AppData.getDatabase().submisieDao().update(submission);

            NotificationsManager.sendNotification(this,
                    "Submission Graded!",
                    "You have received a grade for '" + AppData.getDatabase().temaDao().getTemaById(submission.getTemaId()).getTitlu() + "'.",
                    submission.getId());

            Toast.makeText(this, "Grade saved!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid grade format", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
