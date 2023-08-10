package com.vuthao.VNADCM.base.model.custom;

import java.util.Map;

/**
 * Created by Nhum Lê Sơn Thạch
 * 09/03/2023.
 */
public class ReceivedInfo {
    private Map<String, String> map;

    public ReceivedInfo(Map<String, String> map) {
        this.map = map;
    }

    public boolean isValid() {
        return null != map;
    }

    public String getNotifyContent() {
        return map.get("NotifyContent");
    }

    public String getUrl() {
        return map.get("URL");
    }
}
