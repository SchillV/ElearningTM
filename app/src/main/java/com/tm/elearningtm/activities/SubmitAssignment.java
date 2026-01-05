package com.tm.elearningtm.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.Student;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.data.AppData;

import java.util.Objects;

public class SubmitAssignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        int temaId = getIntent().getIntExtra("ASSIGNMENT_ID", -1);
        Tema tema = null;
        User student = AppData.getUtilizatorCurent();

        try {
            tema = AppData.getCursCurent().getTemaById(temaId);
            if (tema == null) {
                Toast.makeText(this, "Tema nu există", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Eroare: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

        if (tema == null || student == null) {
            finish();
            return;
        }

        TextView titleText = findViewById(R.id.text_assignment_title);
        TextInputEditText submissionText = findViewById(R.id.edit_text_submission);
        Button submitButton = findViewById(R.id.button_submit_assignment);

        titleText.setText(tema.getTitlu());

        Tema finalTema = tema;
        submitButton.setOnClickListener(v -> {
            String continut = Objects.requireNonNull(submissionText.getText()).toString().trim();

            if (continut.isEmpty()) {
                Toast.makeText(this, "Completează răspunsul!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(AppData.isStudent()) {
                SubmisieStudent submisie =
                        new SubmisieStudent(
                                (Student) student,
                                continut
                        );

                AppData.getCursCurent().getTemaById(finalTema.getId()).adaugaSubmisie(submisie);

                Toast.makeText(this, "Tema a fost trimisă!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
