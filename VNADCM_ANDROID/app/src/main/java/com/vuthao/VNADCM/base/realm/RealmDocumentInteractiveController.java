package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.DocumentFavorite;
import com.vuthao.VNADCM.base.model.app.DocumentInteractive;
import com.vuthao.VNADCM.base.model.app.Notify;

import java.util.ArrayList;

import io.realm.RealmResults;

public class RealmDocumentInteractiveController extends RealmController {
    public void addItems(ArrayList<DocumentInteractive> documents) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documents));
    }

    public void addItem(DocumentInteractive document) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(document));
    }

    public DocumentInteractive getItemByDocumentId(int documentId) {
        return realm.where(DocumentInteractive.class).equalTo("DocumentId", documentId).findFirst();
    }

    public ArrayList<DocumentInteractive> getAllItems() {
        RealmResults<DocumentInteractive> realmResults = realm.where(DocumentInteractive.class).findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentInteractive.class));
    }
}
