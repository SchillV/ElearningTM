package com.tm.elearningtm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tm.elearningtm.R;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.database.DatabaseSeeder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set splash theme before super.onCreate()
        setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);

        Log.d(TAG, "Starting application...");
        initializeApp();

        // Show splash for 2 seconds, then route
        new Handler(Looper.getMainLooper()).postDelayed(this::routeUser, SPLASH_DURATION);
    }

    private void initializeApp() {
        // Initialize database
        AppData.initialize(this);

        // Seed test data if no data is present
        DatabaseSeeder.seedDatabase(this);
        Log.d(TAG, "App initialization complete");
    }

    private void routeUser() {
        Intent intent;

        if (AppData.getUtilizatorCurent() != null) {
            Log.d(TAG, "User already logged in: " + AppData.getUtilizatorCurent().getEmail());

            // Route based on role
            if (AppData.isAdmin()) {
                Log.d(TAG, "Routing to Admin Dashboard");
                intent = new Intent(this, AdminDashboard.class);
            } else {
                Log.d(TAG, "Routing to Dashboard");
                intent = new Intent(this, Dashboard.class);
            }
        } else {
            Log.d(TAG, "No user logged in, routing to Login");
            intent = new Intent(this, Login.class);
        }

        startActivity(intent);
        finish();
    }
}