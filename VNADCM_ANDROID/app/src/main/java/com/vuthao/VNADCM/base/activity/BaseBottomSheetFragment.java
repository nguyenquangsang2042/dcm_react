package com.vuthao.VNADCM.base.activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.NetworkChangeReceiver;
import com.vuthao.VNADCM.base.custom.DialogNoConnection;

/**
 * Created by Nhum Lê Sơn Thạch on 23/02/2023.
 */
public class BaseBottomSheetFragment extends BottomSheetDialogFragment implements NetworkChangeReceiver.ReceiverCallback {
    public static BaseBottomSheetFragment sBaseBottomSheet;
    private NetworkChangeReceiver broadcastReceiver;
    private DialogNoConnection dialogNoConnection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sBaseBottomSheet = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new NetworkChangeReceiver(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(broadcastReceiver, filter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) getActivity().unregisterReceiver(broadcastReceiver);
        if (dialogNoConnection != null && dialogNoConnection.isShowing()) {
            dialogNoConnection.dismiss();
        }
    }

    @Override
    public void OnShowInfoNetWork(boolean b) {
        if (dialogNoConnection == null)
            dialogNoConnection = new DialogNoConnection(getActivity());
        if (b && !dialogNoConnection.isShowing()) dialogNoConnection.show();
        else dialogNoConnection.cancel();
    }
}
