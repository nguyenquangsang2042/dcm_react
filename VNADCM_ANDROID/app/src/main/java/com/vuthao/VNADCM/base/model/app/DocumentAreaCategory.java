package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class DocumentAreaCategory extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Title;
    private String TitleEN;
    private String Url;
    private int ParentId;
    private int Rank;
    private String Description;
    private String Image;
    private String Created;
    private String Modified;

    public DocumentAreaCategory() {
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
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

    public String getTitleEN() {
        return TitleEN;
    }

    public void setTitleEN(String titleEN) {
        TitleEN = titleEN;
    }
}
