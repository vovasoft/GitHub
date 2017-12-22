package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserPayChannel extends ChannelBaseInfo {

    private List<UserPayDetail> detail = new ArrayList<UserPayDetail>();

    public UserPayChannel() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param channel
     * @param parent
     * @param child
     * @param seq
     * @param detail
     */
    public UserPayChannel(String channel, String parent, String child,
                          String seq, List<UserPayDetail> detail) {
        this.setChannel(channel);
        this.setParent(parent);
        this.setChild(child);
        this.setSeq(seq);
        this.detail = detail;
    }


    public List<UserPayDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<UserPayDetail> detail) {
        this.detail = detail;
    }


}
