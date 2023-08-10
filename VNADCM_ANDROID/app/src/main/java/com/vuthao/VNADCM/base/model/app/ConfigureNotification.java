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
public class ConfigureNotification extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Title;
    private String TitleEN;
    private String Description;
    private String DescriptionEN;
    private int ActionCategoryId;
    private int ResourceCategoryId;
    private int ResourceSubCategoryId;
    private int IsConfig;
    private int Rank;
    private String Created;
    private String Modified;
    @Ignore
    private ArrayList<MoreInfo> MoreInfo;
    @Ignore
    private ArrayList<ConfigureNotification> Data;
    @Ignore
    private boolean isNotifyChecked;
    @Ignore
    private boolean isEmailChecked;

    public ConfigureNotification() {
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionEN() {
        return DescriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        DescriptionEN = descriptionEN;
    }

    public int getActionCategoryId() {
        return ActionCategoryId;
    }

    public void setActionCategoryId(int actionCategoryId) {
        ActionCategoryId = actionCategoryId;
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

    public int getIsConfig() {
        return IsConfig;
    }

    public void setIsConfig(int isConfig) {
        IsConfig = isConfig;
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

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }

    public ArrayList<ConfigureNotification> getData() {
        return Data;
    }

    public void setData(ArrayList<ConfigureNotification> data) {
        Data = data;
    }

    public boolean isNotifyChecked() {
        return isNotifyChecked;
    }

    public void setNotifyChecked(boolean notifyChecked) {
        isNotifyChecked = notifyChecked;
    }

    public boolean isEmailChecked() {
        return isEmailChecked;
    }

    public void setEmailChecked(boolean emailChecked) {
        isEmailChecked = emailChecked;
    }
}
