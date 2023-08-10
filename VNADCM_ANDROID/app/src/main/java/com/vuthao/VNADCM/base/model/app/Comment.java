package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.Status;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 13/02/2023.
 */
public class Comment extends RealmObject {
    @PrimaryKey
    private String ID;
    private String Title;
    private String StorageCode;
    private int Version;
    private String Content;
    private String Created;
    private int IsApproved;
    private String ResourceUrl;
    private int Status;
    @Ignore
    private ArrayList<Comment> Data;
    @Ignore
    private ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> MoreInfo;
    private String Thumbnail;
    private int DocumentId;

    public Comment() {
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

    public String getStorageCode() {
        return StorageCode;
    }

    public void setStorageCode(String storageCode) {
        StorageCode = storageCode;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public int getIsApproved() {
        return IsApproved;
    }

    public void setIsApproved(int isApproved) {
        IsApproved = isApproved;
    }

    public String getResourceUrl() {
        return ResourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        ResourceUrl = resourceUrl;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public ArrayList<Comment> getData() {
        return Data;
    }

    public void setData(ArrayList<Comment> data) {
        Data = data;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
