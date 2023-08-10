package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 16/02/2023.
 */
public class DocumentRecently extends RealmObject {
    @PrimaryKey
    private int ID;
    private long Modified;

    public DocumentRecently() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getModified() {
        return Modified;
    }

    public void setModified(long modified) {
        Modified = modified;
    }

    public DocumentRecently(int ID, long modified) {
        this.ID = ID;
        Modified = modified;
    }
}
