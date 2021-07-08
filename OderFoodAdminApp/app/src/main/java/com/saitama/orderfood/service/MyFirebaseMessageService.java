package com.saitama.orderfood.service;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.saitama.orderfood.activity.ActivityMain;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.NotificationUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

public class MyFirebaseMessageService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        SharedPrefsUtil.getInstance().put(ContainsUtil.TOKEN_FCM, token); //Lưu token FCM
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        this.notify(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void notify(String from, String body) {
        NotificationUtil notificationUtil = new NotificationUtil(getApplicationContext());
        notificationUtil.showNotification(from, body, new Intent(getApplicationContext(), ActivityMain.class));
    }
}
