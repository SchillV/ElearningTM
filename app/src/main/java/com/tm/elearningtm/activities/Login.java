package com.tm.elearningtm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.database.DatabaseSeeder;
import com.tm.elearningtm.database.PasswordHelper;

public class Login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.edit_text_username);
        passwordEditText = findViewById(R.id.edit_text_password);
        Button loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(v -> attemptLogin());
        AppData.initialize(this);
        //DatabaseSeeder.seedDatabase(this);
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String parola = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || parola.isEmpty()) {
            Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = AppData.getDatabase().userDao().getUserByEmail(email);

        if (user == null) {
            Toast.makeText(this, "Email-ul nu există", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PasswordHelper.checkPassword(parola, user.getPassHash())) {
            Toast.makeText(this, "Parolă incorectă", Toast.LENGTH_SHORT).show();
            return;
        }

        AppData.setUtilizatorCurent(user);

        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
