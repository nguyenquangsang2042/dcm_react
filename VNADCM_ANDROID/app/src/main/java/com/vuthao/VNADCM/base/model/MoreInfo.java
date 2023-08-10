package com.vuthao.VNADCM.base.model;

import java.util.ArrayList;

/**
 * Created by Nhum Lê Sơn Thạch on 17/02/2023.
 */
public class MoreInfo {
    private int NotifyCategoryId;
    private int EmailCategoryId;
    private int totalRecord;

    public MoreInfo() {
    }

    public int getNotifyCategoryId() {
        return NotifyCategoryId;
    }

    public void setNotifyCategoryId(int notifyCategoryId) {
        NotifyCategoryId = notifyCategoryId;
    }

    public int getEmailCategoryId() {
        return EmailCategoryId;
    }

    public void setEmailCategoryId(int emailCategoryId) {
        EmailCategoryId = emailCategoryId;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
}
