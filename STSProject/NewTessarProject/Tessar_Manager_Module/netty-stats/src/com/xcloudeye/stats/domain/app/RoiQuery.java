package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/18.
 */
public class RoiQuery {
    private String api;
    private Integer start;
    private List<RoiQueryChannel> channels = new ArrayList<RoiQueryChannel>();

    public RoiQuery() {
    }

    public RoiQuery(String api, Integer start, List<RoiQueryChannel> channels) {
        this.api = api;
        this.start = start;
        this.channels = channels;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<RoiQueryChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<RoiQueryChannel> channels) {
        this.channels = channels;
    }
}
