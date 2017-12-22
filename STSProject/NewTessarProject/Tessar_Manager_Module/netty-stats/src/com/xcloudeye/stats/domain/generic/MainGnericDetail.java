package com.xcloudeye.stats.domain.generic;

public class MainGnericDetail {
    private String app;
    private Integer new_tdy;
    private Integer new_ytd;
    private Integer active_tdy;
    private Integer active_ytd;
    private double income;

    public MainGnericDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param app
     * @param new_tdy
     * @param new_ytd
     * @param active_tdy
     * @param active_ytd
     * @param income
     */
    public MainGnericDetail(String app, Integer new_tdy, Integer new_ytd,
                            Integer active_tdy, Integer active_ytd, double income) {
        this.app = app;
        this.new_tdy = new_tdy;
        this.new_ytd = new_ytd;
        this.active_tdy = active_tdy;
        this.active_ytd = active_ytd;
        this.income = income;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Integer getNew_tdy() {
        return new_tdy;
    }

    public void setNew_tdy(Integer new_tdy) {
        this.new_tdy = new_tdy;
    }

    public Integer getNew_ytd() {
        return new_ytd;
    }

    public void setNew_ytd(Integer new_ytd) {
        this.new_ytd = new_ytd;
    }

    public Integer getActive_tdy() {
        return active_tdy;
    }

    public void setActive_tdy(Integer active_tdy) {
        this.active_tdy = active_tdy;
    }

    public Integer getActive_ytd() {
        return active_ytd;
    }

    public void setActive_ytd(Integer active_ytd) {
        this.active_ytd = active_ytd;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
