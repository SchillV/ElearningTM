package com.tm.elearningtm.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tm.elearningtm.NotificationsManager;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.time.LocalDateTime;
import java.util.List;

public class DeadlineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Ensure AppData is initialized before use in a background context.
        AppData.initialize(context.getApplicationContext());

        if (AppData.isAdmin()) {
            return; // Admins should not receive notifications.
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<Tema> upcomingAssignments = AppData.getDatabase().temaDao().getUpcomingTemeDirect(LocalDateTime.now().plusDays(1).toString());

            for (Tema assignment : upcomingAssignments) {
                // Only send notifications to students enrolled in the course
                if (!AppData.isProfesor()) {
                    NotificationsManager.sendNotification(context,
                            "Deadline approaching!",
                            "The deadline for '" + assignment.getTitlu() + "' is tomorrow.",
                            assignment.getId());
                }
            }
        }
    }
}
