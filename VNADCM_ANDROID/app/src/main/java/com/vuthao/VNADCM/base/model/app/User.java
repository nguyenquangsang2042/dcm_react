package com.vuthao.VNADCM.base.model.app;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nhum Lê Sơn Thạch on 09/02/2023.
 */
public class User {
    private String ID;
    private String AccountName;
    private String FullName;
    private boolean Gender;
    private String Birthday;
    private String ImagePath;
    private String StaffID;
    private int PositionId;
    private int DepartmentId;
    private String LineManagermentId;
    private String Tel;
    private String Fax;
    private String Mobile;
    private String Email;
    private int DefaultLanguageid;
    private int EmailCategoryId;
    private int NotifyCategoryId;
    private int NotifyMethod;
    private String LastAccessNotify;
    private String LastScanNotify;
    private int Status;
    private double LocaltionLat;
    private double LocaltionLong;
    private String LocaltionIP;
    private boolean IsAdmin;
    private boolean IsCMS;
    private String Modified;
    private String Created;
    private String TopUsedKey;
    private boolean IsTcbDoc;
    private boolean IsDepDoc;
    private boolean IsTcbDocSearch;
    private boolean IsDepDocSearch;
    private boolean IdentificationNum;
    private String PositionTitle;
    private String PositionTitleEN;
    private String DepartmentTitle;
    private String DepartmentTitleEN;
    private String LoginToken;

    public User() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public int getPositionId() {
        return PositionId;
    }

    public void setPositionId(int positionId) {
        PositionId = positionId;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public String getLineManagermentId() {
        return LineManagermentId;
    }

    public void setLineManagermentId(String lineManagermentId) {
        LineManagermentId = lineManagermentId;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getDefaultLanguageid() {
        return DefaultLanguageid;
    }

    public void setDefaultLanguageid(int defaultLanguageid) {
        DefaultLanguageid = defaultLanguageid;
    }

    public int getEmailCategoryId() {
        return EmailCategoryId;
    }

    public void setEmailCategoryId(int emailCategoryId) {
        EmailCategoryId = emailCategoryId;
    }

    public int getNotifyCategoryId() {
        return NotifyCategoryId;
    }

    public void setNotifyCategoryId(int notifyCategoryId) {
        NotifyCategoryId = notifyCategoryId;
    }

    public int getNotifyMethod() {
        return NotifyMethod;
    }

    public void setNotifyMethod(int notifyMethod) {
        NotifyMethod = notifyMethod;
    }

    public String getLastAccessNotify() {
        return LastAccessNotify;
    }

    public void setLastAccessNotify(String lastAccessNotify) {
        LastAccessNotify = lastAccessNotify;
    }

    public String getLastScanNotify() {
        return LastScanNotify;
    }

    public void setLastScanNotify(String lastScanNotify) {
        LastScanNotify = lastScanNotify;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public double getLocaltionLat() {
        return LocaltionLat;
    }

    public void setLocaltionLat(double localtionLat) {
        LocaltionLat = localtionLat;
    }

    public double getLocaltionLong() {
        return LocaltionLong;
    }

    public void setLocaltionLong(double localtionLong) {
        LocaltionLong = localtionLong;
    }

    public String getLocaltionIP() {
        return LocaltionIP;
    }

    public void setLocaltionIP(String localtionIP) {
        LocaltionIP = localtionIP;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
    }

    public boolean isCMS() {
        return IsCMS;
    }

    public void setCMS(boolean CMS) {
        IsCMS = CMS;
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

    public String getTopUsedKey() {
        return TopUsedKey;
    }

    public void setTopUsedKey(String topUsedKey) {
        TopUsedKey = topUsedKey;
    }

    public boolean isTcbDoc() {
        return IsTcbDoc;
    }

    public void setTcbDoc(boolean tcbDoc) {
        IsTcbDoc = tcbDoc;
    }

    public boolean isDepDoc() {
        return IsDepDoc;
    }

    public void setDepDoc(boolean depDoc) {
        IsDepDoc = depDoc;
    }

    public boolean isTcbDocSearch() {
        return IsTcbDocSearch;
    }

    public void setTcbDocSearch(boolean tcbDocSearch) {
        IsTcbDocSearch = tcbDocSearch;
    }

    public boolean isDepDocSearch() {
        return IsDepDocSearch;
    }

    public void setDepDocSearch(boolean depDocSearch) {
        IsDepDocSearch = depDocSearch;
    }

    public boolean isIdentificationNum() {
        return IdentificationNum;
    }

    public void setIdentificationNum(boolean identificationNum) {
        IdentificationNum = identificationNum;
    }

    public String getPositionTitle() {
        return PositionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        PositionTitle = positionTitle;
    }

    public String getPositionTitleEN() {
        return PositionTitleEN;
    }

    public void setPositionTitleEN(String positionTitleEN) {
        PositionTitleEN = positionTitleEN;
    }

    public String getDepartmentTitle() {
        return DepartmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        DepartmentTitle = departmentTitle;
    }

    public String getDepartmentTitleEN() {
        return DepartmentTitleEN;
    }

    public void setDepartmentTitleEN(String departmentTitleEN) {
        DepartmentTitleEN = departmentTitleEN;
    }

    public String getLoginToken() {
        return LoginToken;
    }

    public void setLoginToken(String loginToken) {
        LoginToken = loginToken;
    }
}
