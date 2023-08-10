package com.vuthao.VNADCM.base.model;

import java.util.ArrayList;

public class ApiData extends Status {
    private String data;
    private ArrayList<MoreInfo> MoreInfo;

    public ApiData(String data) {
        this.data = data;
    }

    public ApiData() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(ArrayList<com.vuthao.VNADCM.base.model.MoreInfo> moreInfo) {
        MoreInfo = moreInfo;
    }
}
