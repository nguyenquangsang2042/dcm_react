package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.ConfigureNotification;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Nhum Lê Sơn Thạch on 17/02/2023.
 */
public class RealmConfigureNotificationController extends RealmController {

    public void addNewItems(ArrayList<ConfigureNotification> configureNotifications) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(configureNotifications));
    }

    public ArrayList<ConfigureNotification> getAllItems() {
        return new ArrayList<>(realm.where(ConfigureNotification.class).findAll());
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(ConfigureNotification.class));
    }
}
