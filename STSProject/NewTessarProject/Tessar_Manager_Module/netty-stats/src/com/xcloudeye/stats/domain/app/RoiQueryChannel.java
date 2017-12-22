package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RoiQueryChannel {

    private String channel;
    private Integer new_user;
    private List<HashMap<String, String>> roi = new ArrayList<HashMap<String, String>>();

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public RoiQueryChannel() {
    }

    public RoiQueryChannel(String channel, Integer new_user, List<HashMap<String, String>> roi) {
        this.channel = channel;
        this.new_user = new_user;
        this.roi = roi;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public List<HashMap<String, String>> getRoi() {
        return roi;
    }

    public void setRoi(List<HashMap<String, String>> roi) {
        this.roi = roi;
    }
}
