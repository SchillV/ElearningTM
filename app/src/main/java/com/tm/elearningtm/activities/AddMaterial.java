package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.NotificationsManager;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class AddMaterial extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;
    private Spinner typeSpinner;
    private Button publishButton;

    private boolean isEditMode = false;
    private MaterialCurs existingMaterial = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        titleEditText = findViewById(R.id.edit_text_material_title);
        contentEditText = findViewById(R.id.edit_text_material_content);
        typeSpinner = findViewById(R.id.spinner_material_type);
        publishButton = findViewById(R.id.button_publish_material);

        setupSpinner();

        // Check if we are in edit mode
        if (getIntent().hasExtra("EDIT_MATERIAL_ID")) {
            isEditMode = true;
            int materialId = getIntent().getIntExtra("EDIT_MATERIAL_ID", -1);
            existingMaterial = AppData.getDatabase().materialDao().getMaterialById(materialId);
            setupEditMode();
        } else {
            getSupportActionBar().setTitle("Add Material");
            publishButton.setText("Publish Material");
        }

        publishButton.setOnClickListener(v -> saveData());
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.material_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setupEditMode() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Material");
        publishButton.setText("Save Changes");

        if (existingMaterial != null) {
            titleEditText.setText(existingMaterial.getTitlu());
            contentEditText.setText(existingMaterial.getDescriere());

            // Set spinner selection
            String[] materialTypes = getResources().getStringArray(R.array.material_types);
            int spinnerPosition = Arrays.asList(materialTypes).indexOf(existingMaterial.getTipMaterial());
            if (spinnerPosition >= 0) {
                typeSpinner.setSelection(spinnerPosition);
            }
        }
    }

    private void saveData() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String content = Objects.requireNonNull(contentEditText.getText()).toString().trim();
        String type = typeSpinner.getSelectedItem().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode && existingMaterial != null) {
            // Update existing material
            existingMaterial.setTitlu(title);
            existingMaterial.setDescriere(content);
            existingMaterial.setTipMaterial(type);
            AppData.getDatabase().materialDao().update(existingMaterial);
            Toast.makeText(this, "Material updated!", Toast.LENGTH_SHORT).show();
        } else {
            // Create new material
            int courseId = getIntent().getIntExtra("COURSE_ID", -1);
            if (courseId == -1) {
                Toast.makeText(this, "Invalid course", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MaterialCurs newMaterial = new MaterialCurs(0, title, content, courseId, type, LocalDateTime.now());
                long newMaterialId = AppData.getDatabase().materialDao().insert(newMaterial);

                if ("ANUNT".equals(type)) {
                    List<User> students = AppData.getDatabase().enrollmentDao().getStudentsForCourse(courseId);
                    for (User student : students) {
                        NotificationsManager.sendNotification(this,
                                "New Announcement in " + AppData.getDatabase().cursDao().getCursById(courseId).getTitlu(),
                                title,
                                (int) newMaterialId + student.getId()); // Unique ID per student
                    }
                }

                Toast.makeText(this, "Material published!", Toast.LENGTH_SHORT).show();
            }
        }
        finish(); // Go back to the detail screen
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
