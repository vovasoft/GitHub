package com.xcloud.schedule.appdomain;

/**
 *
 * 五分钟不去重
 * Created by admin on 2016/1/6.
 */
public class repetitiveFiveOut extends Out {
    private Integer time;

    public repetitiveFiveOut() {

    }

    public repetitiveFiveOut(Integer time) {
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