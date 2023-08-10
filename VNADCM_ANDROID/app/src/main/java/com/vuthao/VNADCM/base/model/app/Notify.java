package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.MoreInfo;
import com.vuthao.VNADCM.base.model.Status;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 10/02/2023.
 */
public class Notify extends RealmObject {
    @PrimaryKey
    private String ID;
    private String UserId;
    private String Content;
    private String ContentEN;
    private String ItemImage;
    private String Link;
    private String Icon;
    private boolean FlgRead;
    private boolean flgConfirm;
    private boolean flgConfirmed;
    private boolean ShowPopup;
    private String ResourceId;
    private int ResourceCategoryId;
    private int ResourceSubCategoryId;
    private String ActionTime;
    private String PopupTitle;
    private String PopupTitleEN;
    private String Modified;
    private String Created;
    @Ignore
    private ArrayList<Notify> Data;
    @Ignore
    private ArrayList<MoreInfo> MoreInfo;

    public Notify() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContentEN() {
        return ContentEN;
    }

    public void setContentEN(String contentEN) {
        ContentEN = contentEN;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public boolean isFlgRead() {
        return FlgRead;
    }

    public void setFlgRead(boolean flgRead) {
        FlgRead = flgRead;
    }

    public boolean isFlgConfirm() {
        return flgConfirm;
    }

    public void setFlgConfirm(boolean flgConfirm) {
        this.flgConfirm = flgConfirm;
    }

    public boolean isFlgConfirmed() {
        return flgConfirmed;
    }

    public void setFlgConfirmed(boolean flgConfirmed) {
        this.flgConfirmed = flgConfirmed;
    }

    public boolean isShowPopup() {
        return ShowPopup;
    }

    public void setShowPopup(boolean showPopup) {
        ShowPopup = showPopup;
    }

    public String getResourceId() {
        return ResourceId;
    }

    public void setResourceId(String resourceId) {
        ResourceId = resourceId;
    }

    public int getResourceCategoryId() {
        return ResourceCategoryId;
    }

    public void setResourceCategoryId(int resourceCategoryId) {
        ResourceCategoryId = resourceCategoryId;
    }

    public int getResourceSubCategoryId() {
        return ResourceSubCategoryId;
    }

    public void setResourceSubCategoryId(int resourceSubCategoryId) {
        ResourceSubCategoryId = resourceSubCategoryId;
    }

    public String getActionTime() {
        return ActionTime;
    }

    public void setActionTime(String actionTime) {
        ActionTime = actionTime;
    }

    public String getPopupTitle() {
        return PopupTitle;
    }

    public void setPopupTitle(String popupTitle) {
        PopupTitle = popupTitle;
    }

    public String getPopupTitleEN() {
        return PopupTitleEN;
    }

    public void setPopupTitleEN(String popupTitleEN) {
        PopupTitleEN = popupTitleEN;
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

    public ArrayList<Notify> getData() {
        return Data;
    }

    public void setData(ArrayList<Notify> data) {
        Data = data;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
