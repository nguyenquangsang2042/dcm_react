package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class Document extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Title;
    private String TitleEN;
    private int DocumentId;
    private String Url;
    private String StorageCode;
    private int Status;
    private String IssueDate;
    private String Thumbnail;
    private String LastTimeView;
    private boolean IsMostViewedL;
    private boolean IsNewestL;
    private boolean IsFavoriteL;
    private int AreaCategoryId;

    public Document() {
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

    public String getTitleEN() {
        return TitleEN;
    }

    public void setTitleEN(String titleEN) {
        TitleEN = titleEN;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getLastTimeView() {
        return LastTimeView;
    }

    public void setLastTimeView(String lastTimeView) {
        LastTimeView = lastTimeView;
    }

    public boolean isMostViewedL() {
        return IsMostViewedL;
    }

    public void setMostViewedL(boolean mostViewedL) {
        IsMostViewedL = mostViewedL;
    }

    public boolean isNewestL() {
        return IsNewestL;
    }

    public void setNewestL(boolean newestL) {
        IsNewestL = newestL;
    }

    public boolean isFavoriteL() {
        return IsFavoriteL;
    }

    public void setFavoriteL(boolean favoriteL) {
        IsFavoriteL = favoriteL;
    }

    public int getAreaCategoryId() {
        return AreaCategoryId;
    }

    public void setAreaCategoryId(int areaCategoryId) {
        AreaCategoryId = areaCategoryId;
    }
}
