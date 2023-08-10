package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class SearchHistory extends RealmObject {
    @PrimaryKey
    private String Title;
    private long Modified;

    public SearchHistory() {
    }

    public SearchHistory(String title, long modified) {
        Title = title;
        Modified = modified;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getModified() {
        return Modified;
    }

    public void setModified(long modified) {
        Modified = modified;
    }
}
