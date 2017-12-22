package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class RetQuery {

    private String api;
    private Integer start;
    private Integer end;
    private List<RetQueryChannel> channels = new ArrayList<RetQueryChannel>();

    public RetQuery() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param api
     * @param start
     * @param end
     * @param channels
     */
    public RetQuery(String api, Integer start, Integer end,
                    List<RetQueryChannel> channels) {
        this.api = api;
        this.start = start;
        this.end = end;
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

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<RetQueryChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<RetQueryChannel> channels) {
        this.channels = channels;
    }

}
