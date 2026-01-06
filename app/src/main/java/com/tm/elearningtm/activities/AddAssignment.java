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
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddAssignment extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private TextView deadlineText;

    private LocalDateTime selectedDeadline;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Bind views
        titleEditText = findViewById(R.id.edit_text_assignment_title);
        descriptionEditText = findViewById(R.id.edit_text_assignment_description);
        deadlineText = findViewById(R.id.text_deadline_value);

        Button selectDateButton = findViewById(R.id.button_select_date);
        Button publishButton = findViewById(R.id.button_publish_assignment);

        selectDateButton.setOnClickListener(v -> showDatePicker());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            publishButton.setOnClickListener(v -> publishAssignment());
        }
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

        assert dateDialog != null;
        dateDialog.show();
    }

    private void showTimePicker() {
        @SuppressLint("SetTextI18n") TimePickerDialog timeDialog = new TimePickerDialog(
                this,
                (view, hour, minute) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        selectedDeadline = selectedDate.atTime(hour, minute);
                    }
                    deadlineText.setText("Deadline: " + selectedDeadline);
                },
                23,
                59,
                true
        );

        timeDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void publishAssignment() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String description = Objects.requireNonNull(descriptionEditText.getText()).toString().trim();

        if (title.isEmpty() || selectedDeadline == null) {
            Toast.makeText(this, "Titlul și deadline-ul sunt obligatorii", Toast.LENGTH_SHORT).show();
            return;
        }

        int courseId = getIntent().getIntExtra("COURSE_ID", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Curs invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        Curs curs = AppData.getDatabase().cursDao().getCursById(courseId);

        if (curs == null) {
            Toast.makeText(this, "Cursul nu a fost găsit", Toast.LENGTH_SHORT).show();
            return;
        }

        Tema tema = new Tema(title, description, selectedDeadline);
        curs.adaugaTema(tema);

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
