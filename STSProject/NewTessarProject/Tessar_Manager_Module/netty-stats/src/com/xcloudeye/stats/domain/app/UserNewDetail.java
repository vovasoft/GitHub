package com.xcloudeye.stats.domain.app;

public class UserNewDetail {
    private Integer date;
    private Integer new_user;

    public UserNewDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param date
     * @param new_user
     */
    public UserNewDetail(Integer date, Integer new_user) {
        this.date = date;
        this.new_user = new_user;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }


}
