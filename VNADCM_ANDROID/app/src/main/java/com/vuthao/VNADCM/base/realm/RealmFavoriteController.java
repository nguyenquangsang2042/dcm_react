package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;
import com.vuthao.VNADCM.base.model.app.FavoriteFolder;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 14/02/2023.
 */
public class RealmFavoriteController extends RealmController {
    public void addItems(ArrayList<FavoriteFolder> favoriteFolders) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(favoriteFolders));
    }

    public void addItem(FavoriteFolder favoriteFolder) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(favoriteFolder));
    }


    public ArrayList<FavoriteFolder> getParentItems() {
        RealmResults<FavoriteFolder> realmResults = realm.where(FavoriteFolder.class).isNull("ParentId").findAll();
        return new ArrayList<>(realmResults);
    }

    public ArrayList<FavoriteFolder> getItemByParentId(String parentId) {
        RealmResults<FavoriteFolder> realmResults = realm.where(FavoriteFolder.class).equalTo("ParentId", parentId).findAll();
        return new ArrayList<>(realmResults);
    }

    public FavoriteFolder getItem(String favoriteId) {
        return realm.where(FavoriteFolder.class).equalTo("ID", favoriteId).findFirst();
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(FavoriteFolder.class));
    }
}
