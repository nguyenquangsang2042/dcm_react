package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.base.model.app.Notify;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class RealmNotifyController extends RealmController {

    public void addItems(ArrayList<Notify> notifies) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(notifies));
    }

    public void addItem(Notify notify) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(notify));
    }

    public ArrayList<Notify> getAllItems() {
        RealmResults<Notify> realmResults = realm.where(Notify.class).findAll();
        return new ArrayList<>(realmResults);
    }

    public Notify getItemByDocumentId(int documentId) {
        return realm.where(Notify.class).equalTo("ResourceId", documentId + "").findFirst();
    }

    public ArrayList<Notify> getUnReadItems() {
        RealmResults<Notify> realmResults = realm.where(Notify.class).equalTo("FlgRead", false).findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(Notify.class));
    }
}
