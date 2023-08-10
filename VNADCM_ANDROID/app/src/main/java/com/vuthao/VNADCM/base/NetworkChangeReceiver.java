package com.vuthao.VNADCM.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private ReceiverCallback callback;
    private boolean isShow;

    public NetworkChangeReceiver(ReceiverCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatus(context);
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED && !isShow) {
                isShow = true;
                callback.OnShowInfoNetWork(isShow);
            } else {
                isShow = false;
                callback.OnShowInfoNetWork(isShow);
            }
        }
    }

    public interface ReceiverCallback {
        void OnShowInfoNetWork(boolean b);
    }
}
