package com.vuthao.VNADCM.account.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import com.vuthao.VNADCM.account.AccountFragment;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.ApiAuthController;
import com.vuthao.VNADCM.login.LoginActivity;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class AccountPresenter {
    private final ApiAuthController apiAuthController;

    public AccountPresenter() {
        this.apiAuthController = new ApiAuthController();
    }

    @SuppressLint("HardwareIds")
    public void signOut(AccountFragment bottomSheetAccountDialog) {
        Activity activity = bottomSheetAccountDialog.getActivity();
        assert activity != null;
        apiAuthController.signOut(Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
        Utility.share.resetData(activity);

        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
