package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.model.app.Document;
import com.vuthao.VNADCM.base.model.app.DocumentFile;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Nhum Lê Sơn Thạch on 01/03/2023.
 */
public class RealmDocumentFileController extends RealmController {

    public void addNewItem(DocumentFile documentFile) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentFile));
    }

    public void addNewItems(ArrayList<DocumentFile> documentFiles) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(documentFiles));
    }

    public DocumentFile getItemById(int id) {
        return realm.where(DocumentFile.class).equalTo("ID", id).findFirst();
    }

    public void updatePath(int id, String path) {
        DocumentFile documentFile = getItemById(id);

        if (documentFile != null) {

        } else {
            String extension = Functions.share.getFileExtension(path);
            documentFile = new DocumentFile(id, path, extension);
            addNewItem(documentFile);
        }
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(DocumentFile.class));
    }
}
