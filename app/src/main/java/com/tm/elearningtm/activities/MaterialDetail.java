package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MaterialDetail extends AppCompatActivity {

    private MaterialCurs material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        int materialId = getIntent().getIntExtra("MATERIAL_ID", -1);
        material = AppData.getDatabase().materialDao().getMaterialById(materialId);

        if (material == null) {
            Toast.makeText(this, "Material not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getSupportActionBar().setTitle(material.getTipMaterial());
        populateMaterialDetails();
    }

    @SuppressLint("SetTextI18n")
    private void populateMaterialDetails() {
        TextView titleText = findViewById(R.id.text_material_title);
        TextView dateText = findViewById(R.id.text_material_date);
        TextView contentText = findViewById(R.id.text_material_content);

        titleText.setText(material.getTitlu());
        contentText.setText(material.getDescriere());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateText.setText("Posted on: " + material.getDataCreare().format(formatter));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AppData.isProfesor()) {
            getMenuInflater().inflate(R.menu.menu_material_teacher, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit_material) {
            Intent intent = new Intent(this, AddMaterial.class);
            intent.putExtra("EDIT_MATERIAL_ID", material.getId());
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_delete_material) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Material")
                    .setMessage("Are you sure you want to delete this material?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        AppData.getDatabase().materialDao().delete(material);
                        Toast.makeText(this, "Material deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}