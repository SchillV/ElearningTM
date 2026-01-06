package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.database.PasswordHelper;

public class ProfileActivity extends BaseActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupNavDrawer(R.id.nav_profile);

        nameEditText = findViewById(R.id.edit_text_name);
        emailEditText = findViewById(R.id.edit_text_email);
        newPasswordEditText = findViewById(R.id.edit_text_new_password);
        confirmPasswordEditText = findViewById(R.id.edit_text_confirm_password);
        Button saveButton = findViewById(R.id.button_save_profile);

        loadUserData();

        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void loadUserData() {
        User currentUser = AppData.getUtilizatorCurent();
        if (currentUser != null) {
            nameEditText.setText(currentUser.getNume());
            emailEditText.setText(currentUser.getEmail());
        }
    }

    private void saveProfile() {
        User currentUser = AppData.getUtilizatorCurent();
        String name = nameEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setNume(name);

        if (!newPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            currentUser.setPassHash(PasswordHelper.hashPassword(newPassword));
        }

        AppData.getDatabase().userDao().update(currentUser);
        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
