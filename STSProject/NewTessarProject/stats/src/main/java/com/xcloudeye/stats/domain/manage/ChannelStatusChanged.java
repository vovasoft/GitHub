package com.xcloudeye.stats.domain.manage;

/**
 * Created by joker on 2015/9/14.
 */
public class ChannelStatusChanged {
    private String oldname;
    private String newname;

    public ChannelStatusChanged() {
    }

    public ChannelStatusChanged(String oldname, String newname) {
        this.oldname = oldname;
        this.newname = newname;
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }
}
