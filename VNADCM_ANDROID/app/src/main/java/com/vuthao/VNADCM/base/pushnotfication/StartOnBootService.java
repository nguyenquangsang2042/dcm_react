package com.vuthao.VNADCM.base.pushnotfication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Nhum Lê Sơn Thạch on 24/02/2023.
 */
public class StartOnBootService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            context.startService(new Intent(NotificationService.class.getName()));
        }
    }
}