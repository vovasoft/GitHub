package com.xcloudeye.stats.domain.manage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/14.
 */
public class ChannelStatus {
    private String api;
    private List<String> org_chs = new ArrayList<String>();
    private List<ChannelStatusChanged> changed_chs = new ArrayList<ChannelStatusChanged>();

    public ChannelStatus() {
    }

    public ChannelStatus(String api, List<String> org_chs, List<ChannelStatusChanged> changed_chs) {
        this.api = api;
        this.org_chs = org_chs;
        this.changed_chs = changed_chs;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<String> getOrg_chs() {
        return org_chs;
    }

    public void setOrg_chs(List<String> org_chs) {
        this.org_chs = org_chs;
    }

    public List<ChannelStatusChanged> getChanged_chs() {
        return changed_chs;
    }

    public void setChanged_chs(List<ChannelStatusChanged> changed_chs) {
        this.changed_chs = changed_chs;
    }
}
