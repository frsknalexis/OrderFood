package com.saitama.orderfood.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.saitama.orderfood.R;

public class NotificationUtil {
    private Context context;
    private static final int NOTIFICATION_ID = 234;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    public void showNotification(String from, String notification, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification mNotification = builder
                .setSmallIcon(R.drawable.ic_logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo))
                .setContentTitle(from)
                .setContentText(notification)
                .build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification);
    }

}
