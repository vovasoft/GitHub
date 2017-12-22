package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainTrend {
    private String set;
    private String api;
    private List<MainTrendApp> apps = new ArrayList<MainTrendApp>();

    public MainTrend() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param set
     * @param api
     * @param apps
     */
    public MainTrend(String set, String api, List<MainTrendApp> apps) {
        this.set = set;
        this.api = api;
        this.apps = apps;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<MainTrendApp> getApps() {
        return apps;
    }

    public void setApps(List<MainTrendApp> apps) {
        this.apps = apps;
    }

}
