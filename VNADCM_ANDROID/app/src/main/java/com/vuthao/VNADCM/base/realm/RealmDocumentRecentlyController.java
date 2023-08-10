package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentRecently;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Nhum Lê Sơn Thạch on 16/02/2023.
 */
public class RealmDocumentRecentlyController extends RealmController {

    public void addItems(ArrayList<DocumentRecently> documentRecentlies) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentRecentlies));
    }

    public void addItem(DocumentRecently documentRecently) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentRecently));
    }

    public DocumentRecently getItemById(int id) {
        return realm.where(DocumentRecently.class).equalTo("ID", id).findFirst();
    }

    public ArrayList<DocumentRecently> getAllItems() {
        return new ArrayList<>(realm.where(DocumentRecently.class).sort("Modified", Sort.DESCENDING).findAll());
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentRecently.class));
    }
}
