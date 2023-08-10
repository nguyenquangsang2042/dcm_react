package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.app.DocumentFavorite;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class RealmDocumentFavoriteController extends RealmController {

    public void addItems(ArrayList<DocumentFavorite> documentFavorites) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentFavorites));
    }

    public void addItem(DocumentFavorite documentFavorite) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentFavorite));
    }

    public DocumentFavorite getItemByDocumentId(int documentId) {
        return realm.where(DocumentFavorite.class).equalTo("DocumentId", documentId).findFirst();
    }

    public ArrayList<DocumentFavorite> getItemsByFolderId(String folderId) {
        RealmResults<DocumentFavorite> realmResults = realm.where(DocumentFavorite.class).equalTo("FolderId", folderId).findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentCategory.class));
    }
}
