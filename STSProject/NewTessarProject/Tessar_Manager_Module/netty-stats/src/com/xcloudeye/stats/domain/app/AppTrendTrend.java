package com.xcloudeye.stats.domain.app;

public class AppTrendTrend {

    private Integer date;
    private Integer new_user;
    private Integer dau;

    public AppTrendTrend() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param time
     * @param new_user
     * @param dau
     */
    public AppTrendTrend(Integer date, Integer new_user, Integer dau) {
        this.setDate(date);
        this.new_user = new_user;
        this.dau = dau;
    }


    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public Integer getDau() {
        return dau;
    }

    public void setDau(Integer dau) {
        this.dau = dau;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

}
