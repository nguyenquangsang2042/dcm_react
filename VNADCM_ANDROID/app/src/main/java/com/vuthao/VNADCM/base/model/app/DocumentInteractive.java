package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.MoreInfo;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class DocumentInteractive extends RealmObject {
    @PrimaryKey
    private String ID;
    private String Title;
    private String ResourceId;
    private String ResourceUrl;
    private String Created;
    private String Type;
    private String StorageCode;
    private int VersionShow;
    private String DocumentType;
    private String Department;
    private int IsAutoFollow;
    private String Thumbnail;
    private int DocumentId;
    @Ignore
    private ArrayList<DocumentInteractive> Data;
    @Ignore
    private ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> MoreInfo;

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

    public String getResourceId() {
        return ResourceId;
    }

    public void setResourceId(String resourceId) {
        ResourceId = resourceId;
    }

    public String getResourceUrl() {
        return ResourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        ResourceUrl = resourceUrl;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStorageCode() {
        return StorageCode;
    }

    public void setStorageCode(String storageCode) {
        StorageCode = storageCode;
    }

    public int getVersionShow() {
        return VersionShow;
    }

    public void setVersionShow(int versionShow) {
        VersionShow = versionShow;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getIsAutoFollow() {
        return IsAutoFollow;
    }

    public void setIsAutoFollow(int isAutoFollow) {
        IsAutoFollow = isAutoFollow;
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

    public ArrayList<DocumentInteractive> getData() {
        return Data;
    }

    public void setData(ArrayList<DocumentInteractive> data) {
        Data = data;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
