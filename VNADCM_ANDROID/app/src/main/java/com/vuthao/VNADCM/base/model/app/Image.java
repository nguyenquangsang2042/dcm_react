package com.vuthao.VNADCM.base.model.app;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class Image {
    private int ID;
    private String Title;
    private String UrlPath;
    private String Path;
    private String Created;
    private String Modified;

    public Image() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrlPath() {
        return UrlPath;
    }

    public void setUrlPath(String urlPath) {
        UrlPath = urlPath;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }
}
