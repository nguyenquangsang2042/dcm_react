package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.MoreInfo;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 14/02/2023.
 */
public class DocumentCategory extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Title;
    private String Url;
    private int DocumentId;
    private String StorageCode;
    private int AreaCategoryId;
    private int Version;
    private String IssueDate;
    private int Status;
    private String StatusName;
    private String Code;
    @Ignore
    private ArrayList<DocumentCategory> Data;
    @Ignore
    private ArrayList<MoreInfo> MoreInfo;
    private String Thumbnail;

    public DocumentCategory() {
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

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<DocumentCategory> getData() {
        return Data;
    }

    public void setData(ArrayList<DocumentCategory> data) {
        Data = data;
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

    public int getAreaCategoryId() {
        return AreaCategoryId;
    }

    public void setAreaCategoryId(int areaCategoryId) {
        AreaCategoryId = areaCategoryId;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
