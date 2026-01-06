package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class AddAssignment extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private TextView deadlineText;
    private Button publishButton;

    private LocalDateTime selectedDeadline;
    private LocalDate selectedDate;

    private boolean isEditMode = false;
    private Tema existingAssignment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        titleEditText = findViewById(R.id.edit_text_assignment_title);
        descriptionEditText = findViewById(R.id.edit_text_assignment_description);
        deadlineText = findViewById(R.id.text_deadline_value);
        publishButton = findViewById(R.id.button_publish_assignment);

        Button selectDateButton = findViewById(R.id.button_select_date);
        selectDateButton.setOnClickListener(v -> showDatePicker());

        if (getIntent().hasExtra("EDIT_ASSIGNMENT_ID")) {
            isEditMode = true;
            int assignmentId = getIntent().getIntExtra("EDIT_ASSIGNMENT_ID", -1);
            existingAssignment = AppData.getDatabase().temaDao().getTemaById(assignmentId);
            setupEditMode();
        } else {
            getSupportActionBar().setTitle("Add Assignment");
            publishButton.setText("Publish Assignment");
        }

        publishButton.setOnClickListener(v -> saveData());
    }

    private void setupEditMode() {
        getSupportActionBar().setTitle("Edit Assignment");
        publishButton.setText("Save Changes");

        if (existingAssignment != null) {
            titleEditText.setText(existingAssignment.getTitlu());
            descriptionEditText.setText(existingAssignment.getDescriere());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDeadline = existingAssignment.getDeadline();
                updateDeadlineText();
            }
        }
    }

    private void saveData() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String description = Objects.requireNonNull(descriptionEditText.getText()).toString().trim();

        if (title.isEmpty() || selectedDeadline == null) {
            Toast.makeText(this, "Title and deadline are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode && existingAssignment != null) {
            existingAssignment.setTitlu(title);
            existingAssignment.setDescriere(description);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                existingAssignment.setDeadline(selectedDeadline);
            }
            AppData.getDatabase().temaDao().update(existingAssignment);
            Toast.makeText(this, "Assignment updated!", Toast.LENGTH_SHORT).show();
        } else {
            int courseId = getIntent().getIntExtra("COURSE_ID", -1);
            if (courseId == -1) {
                Toast.makeText(this, "Invalid course", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Tema newAssignment = new Tema(title, description, selectedDeadline);
                AppData.getDatabase().temaDao().insert(newAssignment, courseId);
                Toast.makeText(this, "Assignment published!", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void showDatePicker() {
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }
        DatePickerDialog dateDialog = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateDialog = new DatePickerDialog(
                    this,
                    (view, year, month, day) -> {
                        selectedDate = LocalDate.of(year, month + 1, day);
                        showTimePicker();
                    },
                    today.getYear(),
                    today.getMonthValue() - 1,
                    today.getDayOfMonth()
            );
        }
        if (dateDialog != null) {
            dateDialog.show();
        }
    }

    private void showTimePicker() {
        @SuppressLint("SetTextI18n") TimePickerDialog timeDialog = new TimePickerDialog(
                this,
                (view, hour, minute) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        selectedDeadline = selectedDate.atTime(hour, minute);
                        updateDeadlineText();
                    }
                },
                23,
                59,
                true
        );
        timeDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void updateDeadlineText() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
            deadlineText.setText("Deadline: " + selectedDeadline.format(formatter));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
