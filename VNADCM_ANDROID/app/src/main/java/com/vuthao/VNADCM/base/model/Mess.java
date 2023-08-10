package com.vuthao.VNADCM.base.model;

/**
 * Created by Nhum Lê Sơn Thạch on 28/02/2023.
 */
public class Mess {
    private String Key;
    private String Value;

    public String getKey() {
        if (Key == null) {
            Key = "";
        }
        return Key;
    }

    public String getValue() {
        if (Value == null) {
            Value = "";
        }
        return Value;
    }

    public void setKey(String key) {
        Key = key;
    }

    public void setValue(String value) {
        Value = value;
    }
}
