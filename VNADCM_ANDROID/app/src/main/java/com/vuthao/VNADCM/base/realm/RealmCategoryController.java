package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentAreaCategory;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class RealmCategoryController extends RealmController {

    public void addItems(ArrayList<DocumentAreaCategory> categories) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(categories));
    }

    public void addItem(DocumentAreaCategory category) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(category));
    }

    public ArrayList<DocumentAreaCategory> getParentItems() {
        RealmResults<DocumentAreaCategory> realmResults = realm.where(DocumentAreaCategory.class)
                .equalTo("ParentId", 0)
                .findAll();
        return new ArrayList<>(realmResults);
    }

    public ArrayList<DocumentAreaCategory> getChildByParentId(int categoryId) {
        RealmResults<DocumentAreaCategory> realmResults = realm.where(DocumentAreaCategory.class).equalTo("ParentId", categoryId).findAll();
        return new ArrayList<>(realmResults);
    }

    public DocumentAreaCategory getItem(int categoryId) {
        return realm.where(DocumentAreaCategory.class).equalTo("ID", categoryId).findFirst();
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentAreaCategory.class));
    }
}
