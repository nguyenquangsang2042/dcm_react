package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentCategory;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class RealmDocumentCategoryController extends RealmController {
    public void addItems(ArrayList<DocumentCategory> documentCategories) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentCategories));
    }

    public void addItem(DocumentCategory documentCategory) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentCategory));
    }

    public DocumentCategory getItemByDocumentId(int documentId) {
        return realm.where(DocumentCategory.class).equalTo("DocumentId", documentId).findFirst();
    }

    public ArrayList<DocumentCategory> getItemsAreaCategoryId(int id) {
        RealmResults<DocumentCategory> realmResults = realm.where(DocumentCategory.class).equalTo("AreaCategoryId", id).findAll();
        return new ArrayList<>(realmResults);
    }
    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentCategory.class));
    }
}
