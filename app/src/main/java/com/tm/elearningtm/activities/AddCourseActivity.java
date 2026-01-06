package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

import java.util.Objects;

public class AddCourseActivity extends BaseActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText categoryEditText;
    private TextInputEditText descriptionEditText;
    private Button saveButton;

    private boolean isEditMode = false;
    private Curs existingCourse = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setupToolbarWithBackButton();

        titleEditText = findViewById(R.id.edit_text_course_title);
        categoryEditText = findViewById(R.id.edit_text_course_category);
        descriptionEditText = findViewById(R.id.edit_text_course_description);
        saveButton = findViewById(R.id.button_save_course);

        if (getIntent().hasExtra("EDIT_COURSE_ID")) {
            isEditMode = true;
            int courseId = getIntent().getIntExtra("EDIT_COURSE_ID", -1);
            existingCourse = AppData.getDatabase().cursDao().getCursById(courseId);
            setupEditMode();
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Create Course");
            saveButton.setText("Create Course");
        }

        saveButton.setOnClickListener(v -> saveData());
    }

    @SuppressLint("SetTextI18n")
    private void setupEditMode() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Course");
        saveButton.setText("Save Changes");

        if (existingCourse != null) {
            titleEditText.setText(existingCourse.getTitlu());
            categoryEditText.setText(existingCourse.getCategorie());
            descriptionEditText.setText(existingCourse.getDescriere());
        }
    }

    private void saveData() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String category = Objects.requireNonNull(categoryEditText.getText()).toString().trim();
        String description = Objects.requireNonNull(descriptionEditText.getText()).toString().trim();

        if (title.isEmpty() || category.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode && existingCourse != null) {
            existingCourse.setTitlu(title);
            existingCourse.setCategorie(category);
            existingCourse.setDescriere(description);
            AppData.getDatabase().cursDao().update(existingCourse);
            Toast.makeText(this, "Course updated!", Toast.LENGTH_SHORT).show();
        } else {
            Curs newCourse = new Curs(0, title, description, category, AppData.getUtilizatorCurent().getId());
            AppData.getDatabase().cursDao().insert(newCourse);
            Toast.makeText(this, "Course created!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
