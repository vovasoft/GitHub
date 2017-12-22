package com.xcloudeye.stats.domain.app;

/**
 * Created by admin on 2015/12/18.
 */
public class ActInFiveMinutesDetail{
    private Integer time;
    private Integer active_user;

    public ActInFiveMinutesDetail() {

    }

    public ActInFiveMinutesDetail(Integer time, Integer active_user) {
        this.time = time;
        this.active_user = active_user;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getActive_user() {
        return active_user;
    }

    public void setActive_user(Integer active_user) {
        this.active_user = active_user;
    }
}
