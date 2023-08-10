package com.vuthao.VNADCM.notification.config.presenter;

import com.google.gson.Gson;
import com.vuthao.VNADCM.base.api.ApiConfigureNotificationController;
import com.vuthao.VNADCM.base.api.ApiController;
import com.vuthao.VNADCM.base.model.ApiList;
import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.app.ConfigureNotification;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.realm.RealmConfigureNotificationController;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class ConfigNotificationPresenter {
    private final ConfigNotificationListener listener;
    private final ApiConfigureNotificationController apiConfigureNotificationController;
    private final RealmConfigureNotificationController realmConfigureNotificationController;

    public ConfigNotificationPresenter(ConfigNotificationListener listener) {
        this.listener = listener;
        this.apiConfigureNotificationController = new ApiConfigureNotificationController();
        this.realmConfigureNotificationController = new RealmConfigureNotificationController();
    }

    public void getConfigureNotification() {
        apiConfigureNotificationController.getConfigureNotification(new ApiConfigureNotificationController.ApiConfigureNotificationListener() {
            @Override
            public void onGetConfigureSuccess(ArrayList<ConfigureNotification> configureNotifications, MoreInfo moreInfo) {
                realmConfigureNotificationController.addNewItems(configureNotifications);
                listener.onGetConfigSuccess(configureNotifications, moreInfo);
            }

            @Override
            public void onGetConfigureError() {
                listener.onGetConfigError();
            }
        });
    }

    public void updateConfigureNotification(ArrayList<ConfigureNotification> configureNotificationList, MoreInfo moreInfo) {
        int notifyCategoryId = 0;
        int emailCategoryId = 0;

        for (ConfigureNotification configureNotification: configureNotificationList) {
            if (configureNotification.isNotifyChecked()) {
                notifyCategoryId += configureNotification.getID();
            }

            if (configureNotification.isEmailChecked()) {
                emailCategoryId += configureNotification.getID();
            }
        }

        moreInfo.setNotifyCategoryId(notifyCategoryId);
        moreInfo.setEmailCategoryId(emailCategoryId);

        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Integer> h = new HashMap<>();
        h.put("NotifyCategoryId", notifyCategoryId);
        h.put("EmailCategoryId", emailCategoryId);
        hashMap.put("Parameters", h);

        String data = new Gson().toJson(hashMap);
        apiConfigureNotificationController.updateConfigureNotification(data, new ApiConfigureNotificationController.ApiUpdateConfigureNotificationListener() {
            @Override
            public void onUpdateConfigureNotificationSuccess() {
                listener.onUpdateConfigureSuccess();
            }

            @Override
            public void onUpdateConfigureNotificationError() {
                listener.onGetConfigError();
            }
        });
    }

    public ArrayList<ConfigureNotification> getConfigureNotificationOff() {
        return realmConfigureNotificationController.getAllItems();
    }

    public interface ConfigNotificationListener {
        void onGetConfigSuccess(ArrayList<ConfigureNotification> configNotifies, MoreInfo moreInfo);

        void onGetConfigError();

        void onUpdateConfigureSuccess();

        void onUpdateConfigureError();
    }
}
