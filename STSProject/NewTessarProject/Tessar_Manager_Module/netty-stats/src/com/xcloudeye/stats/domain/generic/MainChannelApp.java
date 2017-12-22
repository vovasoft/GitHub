package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainChannelApp {

    private String app;
    private List<MainChannelChannel> channels = new ArrayList<MainChannelChannel>();

    public MainChannelApp() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param app
     * @param channels
     */
    public MainChannelApp(String app, List<MainChannelChannel> channels) {
        this.app = app;
        this.channels = channels;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<MainChannelChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<MainChannelChannel> channels) {
        this.channels = channels;
    }

}
