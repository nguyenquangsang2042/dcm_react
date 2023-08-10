package com.vuthao.VNADCM.base.pushnotfication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.activity.TabActivity;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.SharedPreferencesController;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.base.model.app.User;
import com.vuthao.VNADCM.base.model.custom.ReceivedInfo;
import com.vuthao.VNADCM.document.DocumentDetailWebActivity;
import com.vuthao.VNADCM.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Nhum Lê Sơn Thạch
 * on 24/02/2023.
 */
public class NotificationService extends FirebaseMessagingService implements ApiAuthController.ApiAuthUserListener {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ReceivedInfo receivedInfo = new ReceivedInfo(remoteMessage.getData());
        if (receivedInfo.isValid()) {
            makeIconNotification(receivedInfo);
        }
    }

    private void makeIconNotification(ReceivedInfo receivedInfo) {
        String content = receivedInfo.getNotifyContent();
        String url = receivedInfo.getUrl();

        Intent intent;

        if (TabActivity.instance != null) {
            ArrayList<Integer> params = Functions.share.getParameterUrlDoc(url);
            intent = new Intent(this, DocumentDetailWebActivity.class);
            intent.putExtra("ResourceId", params.get(0));
            intent.putExtra("DocumentGroupId", params.get(1));
            intent.putExtra("CategoryId", params.get(2));
        } else {
            intent = new Intent(this, LoginActivity.class);
            intent.putExtra("Url", url);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this,
                    new Random().nextInt(99999)
                    , intent
                    , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this
                    , new Random().nextInt(99999)
                    , intent
                    , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String channelId = getString(R.string.app_name);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon_logo_app)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        NotificationChannel channel = new NotificationChannel(channelId, "Thông báo & Nhắc nhở", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(new Random().nextInt(99999), notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);
        sharedPreferencesController.getUserLogin(userId -> {
            if (!userId.isEmpty()) {
                Constants.mDeviceToken = token;
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("DeviceInfo", new Gson().toJson(Utility.share.genDeviceInfo()));
                String data = new Gson().toJson(hashMap);
                new ApiAuthController().getUserInfo(data, NotificationService.this);
            }
        });
    }

    @Override
    public void onAuthUserSuccess(User user) {

    }

    @Override
    public void onAuthUserError(String error) {

    }
}
