package com.xcloudeye.stats.domain.db;

/**
 * 五分钟新注册用户
 * Created by admin on 2016/1/25.
 */
public class FiveNewUserDate extends DataBase {

    private Integer time;

    public FiveNewUserDate() {
    }

    public FiveNewUserDate(Integer time, String channel, String parent,
                           String child, String seq, Integer new_user) {
        this.time = time;
        this.setChannel(channel);
        this.setParent(parent);
        this.setChild(child);
        this.setSeq(seq);
        this.setNew_user(new_user);
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
