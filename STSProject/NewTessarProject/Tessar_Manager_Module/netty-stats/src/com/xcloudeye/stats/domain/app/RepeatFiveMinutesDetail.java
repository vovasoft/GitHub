package com.xcloudeye.stats.domain.app;

/**
 *
 */
public class RepeatFiveMinutesDetail {
    private Integer time;
    private Integer active_user;

    public RepeatFiveMinutesDetail() {
    }

    public RepeatFiveMinutesDetail(Integer time, Integer active_user) {
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
