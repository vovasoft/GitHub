package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserActiveChannel extends ChannelBaseInfo {

    private List<UserActiveDetail> channels = new ArrayList<UserActiveDetail>();

    public UserActiveChannel() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param channel
     * @param parent
     * @param child
     * @param seq
     * @param channels
     */
    public UserActiveChannel(String channel, String parent, String child,
                             String seq, List<UserActiveDetail> channels) {
        this.setChannel(channel);
        this.setParent(parent);
        this.setChild(child);
        this.setSeq(seq);
        this.channels = channels;
    }

    public List<UserActiveDetail> getChannels() {
        return channels;
    }

    public void setChannels(List<UserActiveDetail> channels) {
        this.channels = channels;
    }

}
