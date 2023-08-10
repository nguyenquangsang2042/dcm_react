package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.api.ApiController;
import com.vuthao.VNADCM.base.model.app.DocumentType;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Nhum Lê Sơn Thạch on 24/02/2023.
 */
public class RealmDocumentTypeController extends RealmController {

    public void addNewItems(ArrayList<DocumentType> documentTypes) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentTypes));
    }

    public DocumentType getItem(int id) {
        return realm.where(DocumentType.class).equalTo("ID", id).findFirst();
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentType.class));
    }
}
