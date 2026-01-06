package com.tm.elearningtm.activities.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.Arrays;
import java.util.Objects;

public class EditUserActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private Spinner roleSpinner;

    private User existingUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit User");

        nameEditText = findViewById(R.id.edit_text_user_name);
        emailEditText = findViewById(R.id.edit_text_user_email);
        roleSpinner = findViewById(R.id.spinner_user_role);
        Button saveButton = findViewById(R.id.button_save_user);

        setupRoleSpinner();

        int userId = getIntent().getIntExtra("USER_ID", -1);
        existingUser = AppData.getDatabase().userDao().getUserById(userId);

        if (existingUser != null) {
            nameEditText.setText(existingUser.getNume());
            emailEditText.setText(existingUser.getEmail());

            String[] roles = {"STUDENT", "PROFESOR", "ADMIN"};
            int rolePosition = Arrays.asList(roles).indexOf(existingUser.getRole());
            if (rolePosition >= 0) {
                roleSpinner.setSelection(rolePosition);
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        saveButton.setOnClickListener(v -> saveData());
    }

    private void setupRoleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
    }

    private void saveData() {
        String name = Objects.requireNonNull(nameEditText.getText()).toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        existingUser.setNume(name);
        existingUser.setRole(role);

        AppData.getDatabase().userDao().update(existingUser);
        Toast.makeText(this, "User updated!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}