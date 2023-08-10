package com.vuthao.VNADCM.base.model;

public class ApiObject<T> extends Status {

    private T data;

    public ApiObject() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
