package com.xcloudeye.stats.domain.generic;

public class MainTrendDetail {

    private Integer date;
    private Integer new_user;
    private Integer active_user;
    private Integer payer;
    private Integer payed_order;
    private double income;

    public MainTrendDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param date
     * @param new_user
     * @param active_user
     * @param payer
     * @param payed_order
     * @param income
     */
    public MainTrendDetail(Integer date, Integer new_user, Integer active_user,
                           Integer payer, Integer payed_order, double income) {
        this.date = date;
        this.new_user = new_user;
        this.active_user = active_user;
        this.payer = payer;
        this.payed_order = payed_order;
        this.income = income;
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

    public Integer getActive_user() {
        return active_user;
    }

    public void setActive_user(Integer active_user) {
        this.active_user = active_user;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public Integer getPayed_order() {
        return payed_order;
    }

    public void setPayed_order(Integer payed_order) {
        this.payed_order = payed_order;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
