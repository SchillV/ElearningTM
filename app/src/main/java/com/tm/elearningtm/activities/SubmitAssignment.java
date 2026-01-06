package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SubmitAssignment extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        int assignmentId = getIntent().getIntExtra("ASSIGNMENT_ID", -1);
        Tema assignment = AppData.getDatabase().temaDao().getTemaById(assignmentId);
        User currentUser = AppData.getUtilizatorCurent();

        if (assignment == null) {
            Toast.makeText(this, "Assignment not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView titleText = findViewById(R.id.text_assignment_title);
        TextView deadlineText = findViewById(R.id.text_assignment_deadline);
        TextView descriptionText = findViewById(R.id.text_assignment_description);
        TextInputEditText submissionText = findViewById(R.id.edit_text_submission);
        Button submitButton = findViewById(R.id.button_submit_assignment);

        titleText.setText(assignment.getTitlu());
        descriptionText.setText(assignment.getDescriere());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            deadlineText.setText("Deadline: " + assignment.getDeadline().format(formatter));
        }

        submitButton.setOnClickListener(v -> {
            String submissionContent = Objects.requireNonNull(submissionText.getText()).toString().trim();
            if (submissionContent.isEmpty()) {
                Toast.makeText(this, "Please enter your submission.", Toast.LENGTH_SHORT).show();
                return;
            }

            SubmisieStudent submission = new SubmisieStudent(currentUser, submissionContent);
            AppData.getDatabase().submisieDao().insert(submission);

            Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }
}
