package com.xcloudeye.stats.domain.app;

/**
 * Created by admin on 2015/12/18.
 */
public class FiveNewUserDetail {
    private Integer time;
    private Integer new_user;

    public FiveNewUserDetail() {

    }

    public FiveNewUserDetail(Integer time, Integer new_user) {
        this.time = time;
        this.new_user = new_user;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
