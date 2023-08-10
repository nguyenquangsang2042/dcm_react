package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.Status;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class FavoriteFolder extends RealmObject {
    @PrimaryKey
    private String ID;
    private String Title;
    private String ParentId;
    private int Rank;
    private String ResourceUrl;
    private String CreatedBy;
    private String Modified;
    private String Created;

    public FavoriteFolder() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getResourceUrl() {
        return ResourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        ResourceUrl = resourceUrl;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}
