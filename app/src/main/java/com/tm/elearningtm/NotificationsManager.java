package com.tm.elearningtm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationsManager {

    private static final String CHANNEL_ID = "ELEARNING_CHANNEL";
    private static final String CHANNEL_NAME = "Elearning App Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notifications for deadlines, grades, and announcements.";

    /**
     * Creates the notification channel. This should be called once when the app starts.
     */
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Sends a simple notification.
     * @param context The context from where the notification is sent.
     * @param title The title of the notification.
     * @param message The main content of the notification.
     * @param notificationId A unique ID for the notification to prevent them from stacking.
     */
    public static void sendNotification(Context context, String title, String message, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // You should create a proper notification icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // NOTE: In a real app, you would add a PendingIntent here to open a specific screen when tapped.

        notificationManager.notify(notificationId, builder.build());
    }
}