package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentCategory;
import com.vuthao.VNADCM.base.model.app.DocumentRecently;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Nhum Lê Sơn Thạch on 16/02/2023.
 */
public class RealmDocumentController extends RealmController {

    public void addNewItems(ArrayList<Document> documents) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documents));
    }

    public void addNewItem(Document document) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(document));
    }

    public ArrayList<Document> getDocumentRecently(Integer[] documentId) {
        RealmResults<Document> realmResults = realm.where(Document.class)
                .in("DocumentId", documentId)
                .sort("LastTimeView", Sort.DESCENDING)
                .findAll();
        return new ArrayList<>(realmResults);
    }

    public ArrayList<Document> getAllItems() {
        RealmResults<Document> realmResults = realm.where(Document.class).findAll();
        return new ArrayList<>(realmResults);
    }

    public void updateAllHomeResouces() {
        RealmResults<Document> realmResults = realm.where(Document.class).findAll();
        if (realmResults.size() > 0) {
            realm.executeTransaction(realm -> {
                for (Document d: realmResults) {
                    d.setNewestL(false);
                    d.setMostViewedL(false);
                    d.setFavoriteL(false);
                    realm.copyToRealmOrUpdate(d);
                }
            });
        }
    }

    public Document getItemByDocumentId(int documentId) {
        return realm.where(Document.class).equalTo("DocumentId", documentId).findFirst();
    }

    public Document getItemById(int id) {
        return realm.where(Document.class).equalTo("ID", id).findFirst();
    }

    public ArrayList<Document> getDocumentsMostView() {
        RealmResults<Document> realmResults = realm.where(Document.class).equalTo("IsMostViewedL", true).findAll();
        return new ArrayList<>(realmResults);
    }

    public ArrayList<Document> getDocumentsNewest() {
        RealmResults<Document> realmResults = realm.where(Document.class).equalTo("IsNewestL", true).findAll();
        return new ArrayList<>(realmResults);
    }

    public ArrayList<Document> getDocumentsFavorite() {
        RealmResults<Document> realmResults = realm.where(Document.class).equalTo("IsFavoriteL", true).findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteAllWithOutRecently(Integer[] ids) {
        RealmResults<Document> realmResults = realm.where(Document.class).not().in("ID", ids).findAll();
        if (realmResults.size() > 0) {
            realm.executeTransaction(realm -> realmResults.deleteAllFromRealm());
        }
    }
    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(Document.class));
    }
}
