package com.vuthao.VNADCM.notification.config;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.NetworkUtil;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.User;
import com.vuthao.VNADCM.databinding.ActivityConfigNotificationBinding;
import com.vuthao.VNADCM.notification.config.adapter.ConfigNotificationAdapter;
import com.vuthao.VNADCM.notification.config.presenter.ConfigNotificationPresenter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class ConfigNotificationActivity extends BaseActivity implements View.OnClickListener, ConfigNotificationPresenter.ConfigNotificationListener, ConfigNotificationAdapter.ConfigNotificationListener {
    private ActivityConfigNotificationBinding binding;
    private ConfigNotificationPresenter presenter;
    private ConfigNotificationAdapter adapter;
    private MoreInfo moreInfo;
    private ArrayList<ConfigureNotification> configureNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityConfigNotificationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
        presenter.getConfigureNotification();

        binding.imgBack.setOnClickListener(this);
    }

    private void init() {
        presenter = new ConfigNotificationPresenter(this);
        configureNotifications = new ArrayList<>();

        binding.lvItems.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.lvItems.setHasFixedSize(true);
        binding.lvItems.setItemViewCacheSize(20);
    }

    private void loadList() {
        if (configureNotifications.size() > 0) {
            adapter = new ConfigNotificationAdapter(this, configureNotifications, moreInfo, this);
            binding.lvItems.setAdapter(new AlphaInAnimationAdapter(adapter));

            binding.shimmer.stopShimmerAnimation();
            binding.shimmer.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.lvItems.setVisibility(View.VISIBLE);
        } else {
            binding.shimmer.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
            binding.lvItems.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.imgBack) {
            onBackPressed();
        }
    }

    @Override
    public void onGetConfigSuccess(ArrayList<ConfigureNotification> configNotifies, MoreInfo moreInfo) {
        this.configureNotifications = configNotifies;
        this.moreInfo = moreInfo;
        loadList();
    }

    @Override
    public void onGetConfigError() {
        configureNotifications = presenter.getConfigureNotificationOff();
        loadList();
    }

    @Override
    public void onUpdateConfigureSuccess() {
        CurrentUser.getInstance().getUser().setNotifyCategoryId(moreInfo.getNotifyCategoryId());
        CurrentUser.getInstance().getUser().setEmailCategoryId(moreInfo.getEmailCategoryId());
        getPreferencesController().saveUser(new Gson().toJson(CurrentUser.getInstance().getUser()));
    }

    @Override
    public void onUpdateConfigureError() {

    }

    @Override
    public void onCheckNotifyChange(int pos, boolean isCheck) {
        if (NetworkUtil.getConnectivityStatus(this) == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            new Handler().postDelayed(() -> adapter.notifyItemChanged(pos), 1000);
        } else {
            configureNotifications.get(pos).setNotifyChecked(isCheck);
            presenter.updateConfigureNotification(configureNotifications, moreInfo);
        }
    }

    @Override
    public void onCheckEmailChange(int pos, boolean isCheck) {
        if (NetworkUtil.getConnectivityStatus(this) == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            new Handler().postDelayed(() -> adapter.notifyItemChanged(pos), 1000);
        } else {
            configureNotifications.get(pos).setEmailChecked(isCheck);
            presenter.updateConfigureNotification(configureNotifications, moreInfo);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmer.startShimmerAnimation();
        DCMApplication.getInstance().trackScreenView("Configure Notification screen");
    }

    @Override
    protected void onPause() {
        binding.shimmer.stopShimmerAnimation();
        super.onPause();
    }
}