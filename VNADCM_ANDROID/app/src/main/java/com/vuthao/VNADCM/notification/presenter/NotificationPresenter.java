package com.vuthao.VNADCM.notification.presenter;

import com.vuthao.VNADCM.base.api.ApiNotificationController;
import com.vuthao.VNADCM.base.model.app.Notify;
import com.vuthao.VNADCM.base.realm.RealmNotifyController;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class NotificationPresenter {
    private final NotificationListener listener;
    private final ApiNotificationController apiNotificationController;
    private final RealmNotifyController realmNotifyController;

    public interface NotificationListener {
        void onGetNotificationSuccess(ArrayList<Notify> notifies, boolean isLoadMore, int totalRecord);

        void onGetNotificationError();
    }

    public NotificationPresenter(NotificationListener listener) {
        this.listener = listener;
        this.apiNotificationController = new ApiNotificationController();
        this.realmNotifyController = new RealmNotifyController();
    }

    public void getNotifies(int index, int limit, boolean isLoadMore) {
        apiNotificationController.getNotifies(index, limit, new ApiNotificationController.ApiNotificationListener() {
            @Override
            public void onGetNotificationSuccess(ArrayList<Notify> notifies, int totalRecord) {
                realmNotifyController.addItems(notifies);
                listener.onGetNotificationSuccess(notifies, isLoadMore, totalRecord);
            }

            @Override
            public void onGetNotificationError() {
                listener.onGetNotificationError();
            }
        });
    }

    public void getUnReadNotifies(int index, int limit, boolean isLoadMore) {
        apiNotificationController.getUnReadNotifies(index, limit, new ApiNotificationController.ApiNotificationListener() {
            @Override
            public void onGetNotificationSuccess(ArrayList<Notify> notifies, int totalRecord) {
                realmNotifyController.addItems(notifies);
                listener.onGetNotificationSuccess(notifies, isLoadMore, totalRecord);
            }

            @Override
            public void onGetNotificationError() {
                listener.onGetNotificationError();
            }
        });
    }

    public void markAsRead(String rid) {
        apiNotificationController.markAsRead(rid);
    }

    public ArrayList<Notify> getNotifiesOffline() {
        return realmNotifyController.getAllItems();
    }

    public ArrayList<Notify> getUnReadNotifiesOffline() {
        return realmNotifyController.getUnReadItems();
    }
}
