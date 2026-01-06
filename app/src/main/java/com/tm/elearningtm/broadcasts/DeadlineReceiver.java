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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<Tema> upcomingAssignments = AppData.getDatabase().temaDao().getUpcomingTemeDirect(LocalDateTime.now().plusDays(1).toString());

            for (Tema assignment : upcomingAssignments) {
                // In a real app, you might check if the user is still enrolled
                // and wants this notification before sending it.
                NotificationsManager.sendNotification(context,
                        "Deadline approaching!",
                        "The deadline for '" + assignment.getTitlu() + "' is tomorrow.",
                        assignment.getId());
            }
        }
    }
}