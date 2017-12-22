package com.xcloudeye.stats.domain.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetentionData {
    private Integer date;
    private String channel;
    private String parent;
    private String child;
    private String seq;
    private Integer new_user;
    private List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String, Integer>>();

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public RetentionData() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param date
     * @param channel
     * @param parent
     * @param child
     * @param seq
     * @param new_user
     * @param ret
     */
    public RetentionData(Integer date, String channel, String parent,
                         String child, String seq, Integer new_user,
                         List<HashMap<String, Integer>> ret) {
        this.date = date;
        this.channel = channel;
        this.parent = parent;
        this.child = child;
        this.seq = seq;
        this.new_user = new_user;
        this.ret = ret;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public List<HashMap<String, Integer>> getRet() {
        return ret;
    }

    public void setRet(List<HashMap<String, Integer>> ret) {
        this.ret = ret;
    }

}
