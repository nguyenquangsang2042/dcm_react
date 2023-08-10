package com.vuthao.VNADCM.base.model;

import java.util.ArrayList;

public class ApiList<T> extends Status {
    private ArrayList<T> data;

    public ApiList() {
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
