package com.xcloudeye.stats.domain.db;

/**
 * 五分钟不去重
 * Created by admin on 2016/1/14.
 */
public class RepeatFiveActData extends DataBase {
    private Integer time;

    public RepeatFiveActData() {
    }

    public RepeatFiveActData(Integer time,Integer active_user) {
        this.time = time;
        this.setActive_user(active_user);
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
