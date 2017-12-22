package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class AppDau {
    private String app;
    private List<AppDauChannel> channels = new ArrayList<AppDauChannel>();

    public AppDau() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param app
     * @param channels
     */
    public AppDau(String app, List<AppDauChannel> channels) {
        this.app = app;
        this.channels = channels;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<AppDauChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<AppDauChannel> channels) {
        this.channels = channels;
    }

}
