package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class ChannelListChannel extends ChannelBaseInfo {

    private List<ChannelListDetail> details = new ArrayList<ChannelListDetail>();

    public ChannelListChannel() {
    }

    public ChannelListChannel(String channel, String parent, String child,
                              String seq, List<ChannelListDetail> details) {
        this.setChannel(channel);
        this.setParent(parent);
        this.setChild(child);
        this.setSeq(seq);
        this.setDetails(details);
    }

    public List<ChannelListDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ChannelListDetail> details) {
        this.details = details;
    }


}
