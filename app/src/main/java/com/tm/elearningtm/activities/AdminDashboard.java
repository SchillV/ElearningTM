package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.tm.elearningtm.R;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.database.DatabaseSeeder;
import com.tm.elearningtm.database.AppDatabase;


public class AdminDashboard extends AppCompatActivity {

    private static final String TAG = "AdminDashboard";

    private TextView statsUsersText;
    private TextView statsCoursesText;
    private TextView statsEnrollmentsText;
    private TextView statsSubmissionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check if user is admin
        if (!AppData.isAdmin()) {
            Toast.makeText(this, "Access denied - Admins only", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupViews();
        loadStatistics();
        setupClickListeners();
    }

    @SuppressLint("SetTextI18n")
    private void setupViews() {
        statsUsersText = findViewById(R.id.text_stat_users);
        statsCoursesText = findViewById(R.id.text_stat_courses);
        statsEnrollmentsText = findViewById(R.id.text_stat_enrollments);
        statsSubmissionsText = findViewById(R.id.text_stat_submissions);
    }

    @SuppressLint("SetTextI18n")
    private void loadStatistics() {
        try {
            AppDatabase db = AppData.getDatabase();

            // Load stats from database
            int totalUsers = db.userDao().getUserCount();
            int totalStudents = db.userDao().getStudentCount();
            int totalCourses = db.cursDao().getCourseCount();
            int totalEnrollments = db.enrollmentDao().getAllActiveEnrollments().size();

            // Display stats
            statsUsersText.setText(totalUsers + " (" + totalStudents + " students)");
            statsCoursesText.setText(String.valueOf(totalCourses));
            statsEnrollmentsText.setText(String.valueOf(totalEnrollments));
            statsSubmissionsText.setText("N/A"); // To be implemented
        } catch (Exception e) {
            Log.e(TAG, "Error loading statistics", e);
            Toast.makeText(this, "Error loading statistics.", Toast.LENGTH_LONG).show();
            statsUsersText.setText("Error");
            statsCoursesText.setText("Error");
            statsEnrollmentsText.setText("Error");
            statsSubmissionsText.setText("Error");
        }
    }

    private void setupClickListeners() {
        // User Management Card
        CardView manageUsersCard = findViewById(R.id.card_manage_users);
        manageUsersCard.setOnClickListener(v -> {
            Toast.makeText(this, "User Management - Coming soon!", Toast.LENGTH_SHORT).show();
            // TODO: Open ManageUsersActivity
        });

        // Course Management Card
        CardView manageCoursesCard = findViewById(R.id.card_manage_courses);
        manageCoursesCard.setOnClickListener(v -> {
            Toast.makeText(this, "Course Management - Coming soon!", Toast.LENGTH_SHORT).show();
            // TODO: Open ManageCoursesActivity
        });

        // Enrollment Management Card
        CardView manageEnrollmentsCard = findViewById(R.id.card_manage_enrollments);
        manageEnrollmentsCard.setOnClickListener(v -> {
            Toast.makeText(this, "Enrollment Management - Coming soon!", Toast.LENGTH_SHORT).show();
            // TODO: Open ManageEnrollmentsActivity
        });

        // Database Management Card
        CardView databaseCard = findViewById(R.id.card_database);
        databaseCard.setOnClickListener(v -> showDatabaseOptions());

        // View All Courses Button
        Button viewCoursesButton = findViewById(R.id.button_view_all_courses);
        viewCoursesButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        });

        // Logout Button
        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(v -> logout());
    }

    private void showDatabaseOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Database Management");
        builder.setMessage("Choose an action:");

        builder.setPositiveButton("Clear Database", (dialog, which) -> confirmClearDatabase());
        builder.setNeutralButton("Regenerate Data", (dialog, which) -> confirmRegenerateData());
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void confirmClearDatabase() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⚠️ Warning");
        builder.setMessage("This will DELETE ALL DATA from the database. This action cannot be undone!\n\nAre you sure?");

        builder.setPositiveButton("Yes, Delete Everything", (dialog, which) -> clearDatabase());
        builder.setNegativeButton("Cancel", null);

        // Make it red to emphasize danger
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.error));
    }

    private void clearDatabase() {
        AppData.getDatabase().clearAllTables();
        Toast.makeText(this, "Database cleared successfully", Toast.LENGTH_SHORT).show();

        // Logout and restart
        AppData.logout();
        Intent intent = new Intent(this, Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void confirmRegenerateData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Regenerate Test Data");
        builder.setMessage("This will clear all existing data and create fresh test data.\n\nContinue?");

        builder.setPositiveButton("Yes, Regenerate", (dialog, which) -> regenerateData());
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void regenerateData() {
        // Clear existing data
        AppData.getDatabase().clearAllTables();

        // Regenerate test data
        DatabaseSeeder.seedDatabase(this);

        Toast.makeText(this, "Test data regenerated! Please wait...", Toast.LENGTH_SHORT).show();

        // Wait a moment for seeding to complete, then reload
        new android.os.Handler().postDelayed(() -> {
            loadStatistics();
            Toast.makeText(this, "✓ Data regenerated successfully", Toast.LENGTH_SHORT).show();
        }, 2000);
    }

    private void logout() {
        AppData.logout();
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload statistics when returning to this screen
        loadStatistics();
    }
}