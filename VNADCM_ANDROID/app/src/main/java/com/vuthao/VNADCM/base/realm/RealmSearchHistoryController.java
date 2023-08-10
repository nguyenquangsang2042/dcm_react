package com.vuthao.VNADCM.base.realm;

import com.vuthao.VNADCM.base.model.app.SearchHistory;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class RealmSearchHistoryController extends RealmController {

    public void addItems(ArrayList<SearchHistory> searchHistories) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(searchHistories));
    }

    public void addItem(SearchHistory searchHistory) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(searchHistory));
    }

    public ArrayList<SearchHistory> getAllItems() {
        RealmResults<SearchHistory> realmResults = realm.where(SearchHistory.class)
                .sort("Modified", Sort.DESCENDING)
                .findAll();
        return new ArrayList<>(realmResults);
    }

    public void deleteById(String id) {
        SearchHistory s = realm.where(SearchHistory.class).equalTo("Title", id).findFirst();
        if (s != null) {
            realm.executeTransaction(realm -> s.deleteFromRealm());
        }
    }

    public void deleteAll() {
        realm.executeTransaction(realm -> realm.delete(SearchHistory.class));
    }
}
