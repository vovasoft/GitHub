package com.xcloudeye.stats.domain.db;

/**
 * Created by admin on 2015/12/18.
 */
public class FiveActData extends DataBase {
    private Integer time;

    public FiveActData() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param time
     * @param channel
     * @param parent
     * @param child
     * @param seq
     * @param active_user
     */
    public FiveActData(Integer time, String channel, String parent,
                       String child, String seq, Integer active_user) {
        this.time = time;
        this.setChannel(channel);
        this.setParent(parent);
        this.setChild(child);
        this.setSeq(seq);
        this.setActive_user(active_user);
    }


    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
