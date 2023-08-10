package com.vuthao.VNADCM.base.model.app;

import com.vuthao.VNADCM.base.model.Status;

/**
 * Created by Nhum Lê Sơn Thạch on 15/02/2023.
 */
public class SearchTrend extends Status {
    private String Title;

    public SearchTrend() {
    }

    public SearchTrend(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
