package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.MoreInfo;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class DocumentFavorite extends RealmObject {
    @PrimaryKey
    private String ID;
    private String ResourceTitle;
    private String ResourceUrl;
    private String ResourceId;
    private String FolderId;
    private String CreatedBy;
    private String Modified;
    private String Created;
    private String FolderTitle;
    private String Thumbnail;
    private int DocumentId;
    @Ignore
    private ArrayList<DocumentFavorite> Data;
    @Ignore
    private ArrayList<MoreInfo> MoreInfo;

    public DocumentFavorite() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getResourceTitle() {
        return ResourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        ResourceTitle = resourceTitle;
    }

    public String getResourceUrl() {
        return ResourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        ResourceUrl = resourceUrl;
    }

    public String getResourceId() {
        return ResourceId;
    }

    public void setResourceId(String resourceId) {
        ResourceId = resourceId;
    }

    public String getFolderId() {
        return FolderId;
    }

    public void setFolderId(String folderId) {
        FolderId = folderId;
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

    public String getFolderTitle() {
        return FolderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        FolderTitle = folderTitle;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
    }

    public ArrayList<DocumentFavorite> getData() {
        return Data;
    }

    public void setData(ArrayList<DocumentFavorite> data) {
        Data = data;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
