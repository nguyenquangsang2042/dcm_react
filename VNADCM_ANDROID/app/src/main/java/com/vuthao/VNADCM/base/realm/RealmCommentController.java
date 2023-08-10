package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.Comment;
import com.vuthao.VNADCM.base.model.app.Notify;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class RealmCommentController extends RealmController {

    public void addItems(ArrayList<Comment> comments) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(comments));
    }

    public void addItem(Comment comment) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(comment));
    }

    public Comment getItemByDocumentId(int documentId) {
        return realm.where(Comment.class).equalTo("DocumentId", documentId).findFirst();
    }

    public ArrayList<Comment> getAllItems() {
        RealmResults<Comment> realmResults = realm.where(Comment.class).findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(Comment.class));
    }
}
