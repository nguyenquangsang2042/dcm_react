package com.vuthao.VNADCM.base.pushnotfication;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.Constants;

/**
 * Created by Nhum Lê Sơn Thạch on 24/02/2023.
 */
public class RegistrationService extends IntentService {
    private static final int ID_SERVICE = 101;

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String channelId  = createNotificationChannel("My Service", "My Background Service");

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId);

        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.icon_logo_app)
                .setOngoing(true)
                .setPriority(PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(ID_SERVICE, notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) return;
            Constants.mDeviceToken = task.getResult();
            Log.i("FCM Token", Constants.mDeviceToken);
        });
    }

    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel notificationChannel  = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(notificationChannel);
        return channelId;
    }
}
