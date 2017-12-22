package com.xcloud.schedule.appdomain;

/**
 * Created by 瑞刚 on 2015/12/18.
 */
public class FiveMinuteOut extends Out{
    private Integer time;

    public FiveMinuteOut() {

    }

    public FiveMinuteOut(Integer time) {
        super();
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
