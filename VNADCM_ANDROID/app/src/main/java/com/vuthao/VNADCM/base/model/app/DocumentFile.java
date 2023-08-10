package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 01/03/2023.
 */
public class DocumentFile extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Path;
    private String Extension;

    public DocumentFile() {
    }

    public DocumentFile(int ID, String path, String extension) {
        this.ID = ID;
        Path = path;
        Extension = extension;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
}
