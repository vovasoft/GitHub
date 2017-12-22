package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainTrendApp {
    private String app;
    private List<MainTrendDetail> details = new ArrayList<MainTrendDetail>();

    public MainTrendApp() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param app
     * @param details
     */
    public MainTrendApp(String app, List<MainTrendDetail> details) {
        this.app = app;
        this.details = details;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<MainTrendDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MainTrendDetail> details) {
        this.details = details;
    }


}
