package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tm.elearningtm.NotificationsManager;
import com.tm.elearningtm.R;
import com.tm.elearningtm.broadcasts.DeadlineReceiver;
import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.database.DatabaseSeeder;

import java.util.Calendar;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Log.d(TAG, "Starting application...");
        initializeApp();

        new Handler(Looper.getMainLooper()).postDelayed(this::routeUser, SPLASH_DURATION);
    }

    private void initializeApp() {
        AppData.initialize(this);
        NotificationsManager.createNotificationChannel(this);
        scheduleDailyDeadlineChecks();
        DatabaseSeeder.seedDatabase(this);
        Log.d(TAG, "App initialization complete");
    }

    private void scheduleDailyDeadlineChecks() {
        Intent intent = new Intent(this, DeadlineReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to start at approximately 8:00 a.m. every day.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void routeUser() {
        Intent intent;
        if (AppData.getUtilizatorCurent() != null) {
            Log.d(TAG, "User already logged in: " + AppData.getUtilizatorCurent().getEmail());
            intent = new Intent(this, Dashboard.class);
        } else {
            Log.d(TAG, "No user logged in, routing to Login");
            intent = new Intent(this, Login.class);
        }
        startActivity(intent);
        finish();
    }
}
